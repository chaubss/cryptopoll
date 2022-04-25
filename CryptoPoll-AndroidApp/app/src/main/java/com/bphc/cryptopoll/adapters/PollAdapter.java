package com.bphc.cryptopoll.adapters;

import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.EMAIL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bphc.cryptopoll.R;
import com.bphc.cryptopoll.helper.OnItemClickListener;
import com.bphc.cryptopoll.models.Choice;
import com.bphc.cryptopoll.models.Poll;
import com.bphc.cryptopoll.prefs.SharedPrefs;

import java.util.ArrayList;

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.PollViewHolder> {

    ArrayList<Poll> polls;
    Context context;
    int flag;

    private OnItemClickListener listener;

    public void setOnOptionClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PollAdapter(ArrayList<Poll> polls, Context context, int flag) {
        this.polls = polls;
        this.context = context;
        this.flag = flag;
    }


    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        View view = layoutInflater.inflate(R.layout.card_poll, parent, false);

        return new PollViewHolder(view, listener, flag);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {

        String email = SharedPrefs.getStringParams(context, EMAIL, "");

        Poll poll = polls.get(position);
        holder.pollName.setText(poll.getName());
        holder.pollDescription.setText(poll.getDescription());
        ArrayList<Choice> choices = poll.getChoices();
        holder.optionGroup.removeAllViews();
        for (int i = 0; i < choices.size(); i++) {
            RadioButton option = new RadioButton(context);
            option.setId(choices.get(i).getId());
            option.setText(choices.get(i).getName() + "\t\t\t\t\t\t" + choices.get(i).getVotes() + " votes");
            option.setOnClickListener(view -> {
                SharedPrefs.setBooleanParams(context, email + " marked " + poll.getId(), true);
                SharedPrefs.setIntParams(context, email + poll.getId(), option.getId());
                listener.onItemClick(option.getId(), poll.getId());
            });
            if (SharedPrefs.getIntParams(context, email + poll.getId()) == option.getId())
                option.setChecked(true);
            holder.optionGroup.addView(option);
        }

    }

    @Override
    public int getItemCount() {
        return polls.size();
    }

    public static class PollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pollName, pollDescription;
        RadioGroup optionGroup;
        Button buttonVerifyVote;

        OnItemClickListener listener;
        int flag;

        public PollViewHolder(@NonNull View itemView, OnItemClickListener listener, int flag) {
            super(itemView);

            this.listener = listener;
            this.flag = flag;

            pollName = itemView.findViewById(R.id.card_poll_name);
            pollDescription = itemView.findViewById(R.id.card_poll_description);

            optionGroup = itemView.findViewById(R.id.card_radio_group);
            optionGroup.setOnClickListener(this);

            buttonVerifyVote = itemView.findViewById(R.id.button_verify_vote);
            if (flag == 0) {
                buttonVerifyVote.setVisibility(View.GONE);
            } else {
                buttonVerifyVote.setVisibility(View.VISIBLE);
            }
            buttonVerifyVote.setOnClickListener(this);
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
