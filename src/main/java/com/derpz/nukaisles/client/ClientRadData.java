package com.derpz.nukaisles.client;

public class ClientRadData {
    private static int playerRads;

    public static void set(int rads) {
        ClientRadData.playerRads = rads;
    }

    public static int getPlayerRads() {
        return playerRads;
    }
}
