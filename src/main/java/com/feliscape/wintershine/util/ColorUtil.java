package com.feliscape.wintershine.util;

import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.awt.*;

public class ColorUtil {
    public static int getIntColor(String hex){
        Color col = Color.decode(hex);
        int result = getIntFromRGB(col.getRed(), col.getGreen(), col.getBlue());
        return result;
    }

    public static int getIntFromRGB(int Red, int Green, int Blue){

        int R = (Red << 16)  & 0x00FF0000;
        int G = (Green << 8) & 0x0000FF00;
        int B = Blue & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }

    public static Vector3f RGB255ToRGB1(float red, float green, float blue){
        return new Vector3f(red / 255f, green / 255f, blue / 255f);
    }

    public static Vector3f getVec3Color(String hex) {
        Color col = Color.decode(hex);
        return new Vector3f(col.getRed() / 255f, col.getGreen() / 255f, col.getBlue() / 255f);
    }

    public static Vector3f lerp(String hexA, String hexB, float t) {
        Vector3f colorA = getVec3Color(hexA);
        Vector3f colorB = getVec3Color(hexB);
        return new Vector3f(Mth.lerp(t, colorA.x, colorB.x), Mth.lerp(t, colorA.y, colorB.y), Mth.lerp(t, colorA.z, colorB.z));
    }
}
