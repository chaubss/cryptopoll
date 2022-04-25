package com.bphc.cryptopoll.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bphc.cryptopoll.R;
import com.bphc.cryptopoll.helper.OnItemClickListener;
import com.bphc.cryptopoll.models.Block;
import com.bphc.cryptopoll.models.Poll;

import java.util.ArrayList;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {

    ArrayList<Block> blocks;
    Context context;

    private OnItemClickListener listener;

    public void setOnBlockClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public BlockAdapter(ArrayList<Block> blocks, Context context) {
        this.blocks = blocks;
        this.context = context;
    }

    @NonNull
    @Override
    public BlockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        View view = layoutInflater.inflate(R.layout.card_block, parent, false);

        return new BlockViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockViewHolder holder, int position) {
        Block block = blocks.get(position);
        holder.blockHeight.setText("Height: " + block.getHeight() + "");
        holder.blockHash.setText("Hash: " + block.getHash());
        holder.blockPrevHash.setText("PrevHash: " + block.getPrevious_hash());
        holder.blockNounce.setText("Nounce: " + block.getNounce() + "");
        holder.blockStatus.setText("Status: " + block.getStatus() + "");
        holder.blockTime.setText("Time: " + block.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return blocks.size();
    }

    public static class BlockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView blockHeight, blockHash, blockPrevHash, blockNounce, blockStatus, blockTime;
        OnItemClickListener listener;

        public BlockViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            this.listener = listener;

            blockHeight = itemView.findViewById(R.id.card_text_block_height);
            blockHash = itemView.findViewById(R.id.card_text_hash);
            blockPrevHash = itemView.findViewById(R.id.card_text_previous_hash);
            blockNounce = itemView.findViewById(R.id.card_text_nounce);
            blockStatus = itemView.findViewById(R.id.card_text_status);
            blockTime = itemView.findViewById(R.id.card_text_block_time);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            }
        }
    }

}
