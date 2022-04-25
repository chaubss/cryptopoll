package com.bphc.cryptopoll.models;

import com.google.gson.annotations.SerializedName;

public class Vote {

    @SerializedName("vote_id")
    public int vote_id;

    @SerializedName("id")
    public int id;

    @SerializedName("datetime")
    public String datetime;

    @SerializedName("user")
    public String user;

    @SerializedName("choice")
    public String choice;

    @SerializedName("poll")
    public String poll;

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getPoll() {
        return poll;
    }

    public void setPoll(String poll) {
        this.poll = poll;
    }
}
