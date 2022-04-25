package com.bphc.cryptopoll.fragments;

import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.EMAIL;
import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.JWTS_TOKEN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bphc.cryptopoll.CreatePollActivity;
import com.bphc.cryptopoll.R;
import com.bphc.cryptopoll.adapters.PollAdapter;
import com.bphc.cryptopoll.helper.APIClient;
import com.bphc.cryptopoll.helper.OnItemClickListener;
import com.bphc.cryptopoll.helper.Webservices;
import com.bphc.cryptopoll.models.Poll;
import com.bphc.cryptopoll.models.PollResponse;
import com.bphc.cryptopoll.models.Vote;
import com.bphc.cryptopoll.prefs.SharedPrefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    PollAdapter pollAdapter;
    FloatingActionButton fabCreatePoll;
    ArrayList<Poll> polls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        polls = new ArrayList<>();
        pollAdapter = new PollAdapter(polls, requireContext(), 0);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_polls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(pollAdapter);

        fabCreatePoll = view.findViewById(R.id.fab_create_poll);
        fabCreatePoll.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), CreatePollActivity.class);
            startActivity(intent);
        });

        pollAdapter.setOnOptionClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int choiceId, int pollId) {
                Retrofit retrofit = APIClient.getRetrofitInstance();
                Webservices webservices = retrofit.create(Webservices.class);

                HashMap<String, Integer> map = new HashMap<>();
                map.put("choice_id", choiceId);

                Call<Vote> call = webservices.castVote("Bearer " + SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""), map);
                call.enqueue(new Callback<Vote>() {
                    @Override
                    public void onResponse(Call<Vote> call, Response<Vote> response) {
                        Vote vote = response.body();
                        SharedPrefs.setIntParams(
                                requireContext(),
                                SharedPrefs.getStringParams(requireContext(), EMAIL, "") + pollId + " vote_id",
                                vote.getVote_id());
                    }

                    @Override
                    public void onFailure(Call<Vote> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onItemClick(int id) {

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        polls.clear();
        pollAdapter.notifyDataSetChanged();
        getPolls();
    }

    private void getPolls() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        Call<PollResponse> call = webservices
                .getPolls("Bearer " + SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""));

        Log.d("BEARER", SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""));
        call.enqueue(new Callback<PollResponse>() {
            @Override
            public void onResponse(Call<PollResponse> call, Response<PollResponse> response) {
                PollResponse pollResponse = response.body();
                polls.addAll(pollResponse.getPolls());
                pollAdapter.notifyItemRangeInserted(0, polls.size());
            }

            @Override
            public void onFailure(Call<PollResponse> call, Throwable t) {

            }
        });
    }
}
