package com.bphc.cryptopoll.fragments;

import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.JWTS_TOKEN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bphc.cryptopoll.R;
import com.bphc.cryptopoll.adapters.BlockAdapter;
import com.bphc.cryptopoll.adapters.VoteAdapter;
import com.bphc.cryptopoll.helper.APIClient;
import com.bphc.cryptopoll.helper.OnItemClickListener;
import com.bphc.cryptopoll.helper.Webservices;
import com.bphc.cryptopoll.models.Block;
import com.bphc.cryptopoll.models.BlockResponse;
import com.bphc.cryptopoll.models.Vote;
import com.bphc.cryptopoll.models.VoteResponse;
import com.bphc.cryptopoll.prefs.SharedPrefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BlockchainFragment extends Fragment {

    BlockAdapter blockAdapter;
    VoteAdapter voteAdapter;
    ArrayList<Block> blocks;
    ArrayList<Vote> votes;
    private TextView textBlockNumber, textBlockHeight, textBlockHash, textBlockPrevHash, textBlockTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blockchain, container, false);

        textBlockNumber = view.findViewById(R.id.text_block_number);
        textBlockHeight = view.findViewById(R.id.text_height);
        textBlockHash = view.findViewById(R.id.text_hash);
        textBlockPrevHash = view.findViewById(R.id.text_previous_hash);
        textBlockTime = view.findViewById(R.id.text_time);

        blocks = new ArrayList<>();

        blockAdapter = new BlockAdapter(blocks, requireContext());
        RecyclerView recyclerViewBlocks = view.findViewById(R.id.recycler_blocks);
        recyclerViewBlocks.setHasFixedSize(true);
        recyclerViewBlocks.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewBlocks.setAdapter(blockAdapter);

        blockAdapter.setOnBlockClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int choiceId, int positionId) {

            }

            @Override
            public void onItemClick(int id) {

                votes = new ArrayList<>();

                voteAdapter = new VoteAdapter(votes, requireContext());
                RecyclerView recyclerViewVotes = view.findViewById(R.id.recycler_votes);
                recyclerViewVotes.setHasFixedSize(true);
                recyclerViewVotes.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerViewVotes.setAdapter(voteAdapter);

                Block block = blocks.get(id);

                textBlockNumber.setText("Block #" + block.getHeight());
                textBlockHeight.setText("Height: " + block.getHeight());
                textBlockHash.setText("Hash: " + block.getHash());
                textBlockPrevHash.setText("Prev Hash: " + block.getPrevious_hash());
                textBlockTime.setText("Time: " + block.getTimestamp());

                getBlockVotes(block.getHeight());
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getBlocks();
    }

    private void getBlocks() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        Call<BlockResponse> call = webservices
                .getBlocks("Bearer " + SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""));
        call.enqueue(new Callback<BlockResponse>() {
            @Override
            public void onResponse(Call<BlockResponse> call, Response<BlockResponse> response) {
                BlockResponse blockResponse = response.body();
                blocks.addAll(blockResponse.getBlocks());
                blockAdapter.notifyItemRangeInserted(0, blocks.size());
            }

            @Override
            public void onFailure(Call<BlockResponse> call, Throwable t) {

            }
        });
    }

    private void getBlockVotes(int blockHeight) {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        Call<VoteResponse> call = webservices
                .getBlockVotes( "Bearer " + SharedPrefs.getStringParams(requireContext(), JWTS_TOKEN, ""),
                        blockHeight);
        call.enqueue(new Callback<VoteResponse>() {
            @Override
            public void onResponse(Call<VoteResponse> call, Response<VoteResponse> response) {
                VoteResponse voteResponse = response.body();
                votes.addAll(voteResponse.getVotes());
                voteAdapter.notifyItemRangeInserted(0, votes.size());
                Toast.makeText(requireContext(), "" + votes.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<VoteResponse> call, Throwable t) {

            }
        });
    }

}
