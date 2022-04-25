package com.bphc.cryptopoll.models;

import com.google.gson.annotations.SerializedName;

public class ZKP {

    @SerializedName("h")
    public int h;

    @SerializedName("y")
    public int y;

    @SerializedName("b")
    public int b;

    @SerializedName("zkp_id")
    public int zkp_id;

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getZkp_id() {
        return zkp_id;
    }

    public void setZkp_id(int zkp_id) {
        this.zkp_id = zkp_id;
    }
}
