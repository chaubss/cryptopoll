package com.bphc.cryptopoll.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VoteResponse {

    @SerializedName("votes")
    public ArrayList<Vote> votes;

    public ArrayList<Vote> getVotes() {
        return votes;
    }

    public void setVotes(ArrayList<Vote> votes) {
        this.votes = votes;
    }
}
