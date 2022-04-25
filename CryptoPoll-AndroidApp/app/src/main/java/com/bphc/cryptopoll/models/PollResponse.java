package com.bphc.cryptopoll.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PollResponse {

    @SerializedName("polls")
    public ArrayList<Poll> polls;

    public ArrayList<Poll> getPolls() {
        return polls;
    }

    public void setPolls(ArrayList<Poll> polls) {
        this.polls = polls;
    }
}
