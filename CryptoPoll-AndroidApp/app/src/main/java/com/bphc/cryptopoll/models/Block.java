package com.bphc.cryptopoll.models;

import com.google.gson.annotations.SerializedName;

public class Block {

    @SerializedName("height")
    public int height;

    @SerializedName("hash")
    public String hash;

    @SerializedName("previous_hash")
    public String previous_hash;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("status")
    public int status;

    @SerializedName("nounce")
    public int nounce;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPrevious_hash() {
        return previous_hash;
    }

    public void setPrevious_hash(String previous_hash) {
        this.previous_hash = previous_hash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNounce() {
        return nounce;
    }

    public void setNounce(int nounce) {
        this.nounce = nounce;
    }
}
