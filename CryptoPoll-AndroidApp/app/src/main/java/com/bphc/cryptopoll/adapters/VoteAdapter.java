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
import com.bphc.cryptopoll.models.Vote;

import java.util.ArrayList;

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteViewHolder> {

    ArrayList<Vote> votes;
    Context context;

    private OnItemClickListener listener;

    public void setOnTransactionClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public VoteAdapter(ArrayList<Vote> votes, Context context) {
        this.votes = votes;
        this.context = context;
    }

    @NonNull
    @Override
    public VoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        View view = layoutInflater.inflate(R.layout.card_vote, parent, false);

        return new VoteViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VoteViewHolder holder, int position) {
        Vote vote = votes.get(position);
        holder.voteUser.setText(vote.getUser());
        holder.votePollName.setText(vote.getPoll());
        holder.votePollChoice.setText(vote.getChoice());
        holder.voteDateTime.setText(vote.getDatetime());
    }

    @Override
    public int getItemCount() {
        return votes.size();
    }

    public static class VoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemClickListener listener;
        TextView voteUser, voteDateTime, votePollName, votePollChoice;

        public VoteViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            this.listener = listener;
            voteUser = itemView.findViewById(R.id.card_text_user_name);
            voteDateTime = itemView.findViewById(R.id.card_text_vote_time);
            votePollName = itemView.findViewById(R.id.card_text_poll_name);
            votePollChoice = itemView.findViewById(R.id.card_text_poll_choice);

        }

        @Override
        public void onClick(View view) {

        }
    }

}
