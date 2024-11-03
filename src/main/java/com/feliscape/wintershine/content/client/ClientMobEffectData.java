package com.feliscape.wintershine.content.client;

public class ClientMobEffectData {
    private static int siphonedHearts;

    public static int getSiphonedHearts() {
        return siphonedHearts;
    }

    public static void setSiphonedHearts(int siphonedHearts) {
        ClientMobEffectData.siphonedHearts = siphonedHearts;
    }

    public static void set(int siphonedHearts) {
        setSiphonedHearts(siphonedHearts);
    }
}
