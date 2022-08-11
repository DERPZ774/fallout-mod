package com.derpz.dfm.rads;

import net.minecraft.nbt.CompoundTag;

public class PlayerRads {
    private int rads;
    private final int MIN_RADS = 0;
    private final int MAX_RADS = 1000;

    public int getRads() {
        return rads;
    } 

    public void addRads(int add) {
        this.rads = Math.min(rads + add, MAX_RADS);
    }

    public void subRads(int sub) {
        this.rads = Math.max(rads - sub, MIN_RADS);
    }

    public void setRads(int set) {
        this.rads = set;
    }

    public void copyFrom(PlayerRads source) {
        this.rads = source.rads;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("rads", rads);
    }

    public void loadNBTData(CompoundTag nbt) {
        rads = nbt.getInt("rads");
    }

}
