package cn.seiua.skymatrix.utils;

import java.awt.*;

public class ColorUtils {

    public static Color darkenColor(Color color, double factor) {
        int red = (int) (color.getRed() * (1 - factor));
        int green = (int) (color.getGreen() * (1 - factor));
        int blue = (int) (color.getBlue() * (1 - factor));
        red = Math.max(0, red);
        green = Math.max(0, green);
        blue = Math.max(0, blue);

        return new Color(red, green, blue);
    }

    public static float getHue(Color color) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
        return hsv[0];
    }

    public static Color setHue(Color color, float hue) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
        hsv[0] = hue;
        int rgb = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
        return new Color(rgb);
    }


}
