package com.bphc.cryptopoll.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BlockResponse {

    @SerializedName("blocks")
    public ArrayList<Block> blocks;

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }
}
