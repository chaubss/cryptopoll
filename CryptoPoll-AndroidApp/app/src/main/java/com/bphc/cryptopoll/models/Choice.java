package com.bphc.cryptopoll.models;

import com.google.gson.annotations.SerializedName;

public class Choice {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("votes")
    public int votes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
