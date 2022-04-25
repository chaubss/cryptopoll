package com.bphc.cryptopoll.fragments;

import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.EMAIL;
import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.JWTS_TOKEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bphc.cryptopoll.R;
import com.bphc.cryptopoll.adapters.PollAdapter;
import com.bphc.cryptopoll.adapters.VoteAdapter;
import com.bphc.cryptopoll.helper.APIClient;
import com.bphc.cryptopoll.helper.OnItemClickListener;
import com.bphc.cryptopoll.helper.Webservices;
import com.bphc.cryptopoll.models.Poll;
import com.bphc.cryptopoll.models.PollResponse;
import com.bphc.cryptopoll.models.ZKP;
import com.bphc.cryptopoll.prefs.SharedPrefs;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyVotesFragment extends Fragment {

    PollAdapter pollAdapter;
    ArrayList<Poll> polls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_votes, container, false);

        polls = new ArrayList<>();

        pollAdapter = new PollAdapter(polls, requireContext(), 1);
        RecyclerView recyclerViewVotes = view.findViewById(R.id.recycler_my_votes);
        recyclerViewVotes.setHasFixedSize(true);
        recyclerViewVotes.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewVotes.setAdapter(pollAdapter);

        pollAdapter.setOnOptionClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int choiceId, int pollId) {

            }

            @Override
            public void onItemClick(int id) {
                Poll poll = polls.get(id);
                int pollId = poll.getId();
                int voteId = SharedPrefs.getIntParams(
                        requireContext(),
                        SharedPrefs.getStringParams(requireContext(), EMAIL, "") + pollId + " vote_id");
                Toast.makeText(requireContext(), "" + voteId, Toast.LENGTH_SHORT).show();
                zkpFirst(voteId);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        polls.clear();
        pollAdapter.notifyDataSetChanged();
        viewMyVotes();
    }

    private void viewMyVotes() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        String email = SharedPrefs.getStringParams(requireContext(), EMAIL, "");

        Call<PollResponse> call = webservices
                .getPolls("Bearer " + SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""));

        Log.d("BEARER", SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""));
        call.enqueue(new Callback<PollResponse>() {
            @Override
            public void onResponse(Call<PollResponse> call, Response<PollResponse> response) {
                PollResponse pollResponse = response.body();
                for(Poll poll: pollResponse.getPolls()) {
                    if (SharedPrefs.getBooleanParams(requireContext(), email + " marked " + poll.getId()))
                        polls.add(poll);
                }
                pollAdapter.notifyItemRangeInserted(0, polls.size());
            }

            @Override
            public void onFailure(Call<PollResponse> call, Throwable t) {

            }
        });
    }

    private void zkpFirst(int voteId) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        int p = 11, g = 2;
        int y = (int)Math.pow(g, voteId)  % p;
        int r = 5;
        int h = (int)Math.pow(g, r) % p;

        HashMap<String, Integer> map = new HashMap<>();
        map.put("y", y);
        map.put("h", h);

        Call<ZKP> call = webservices
                .zkpFirst("Bearer " + SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""),
                map);

        call.enqueue(new Callback<ZKP>() {
            @Override
            public void onResponse(Call<ZKP> call, Response<ZKP> response) {
                ZKP zkp = response.body();
                int b = zkp.getB();
                int s = (r + b*voteId) % (p-1);
                int zkp_id = zkp.getZkp_id();

                zkpSecond(s, zkp_id);
            }

            @Override
            public void onFailure(Call<ZKP> call, Throwable t) {

            }
        });
    }

    private void zkpSecond(int s, int zkp_id) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        HashMap<String, Integer> map = new HashMap<>();
        map.put("s", s);
        map.put("zkp_id", zkp_id);

        Call<ZKP> call = webservices
                .zkpSecond("Bearer " + SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""),
                        map);

        call.enqueue(new Callback<ZKP>() {
            @Override
            public void onResponse(Call<ZKP> call, Response<ZKP> response) {
                Toast.makeText(requireContext(), "Verified", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ZKP> call, Throwable t) {

            }
        });

    }
}
