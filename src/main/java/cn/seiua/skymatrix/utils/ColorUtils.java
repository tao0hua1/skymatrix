package cn.seiua.skymatrix.utils;

import java.awt.*;

public class ColorUtils {

    public static Color darkenColor(Color color, double factor) {
        int red = (int) (color.getRed() * (1 - factor));
        int green = (int) (color.getGreen() * (1 - factor));
        int blue = (int) (color.getBlue() * (1 - factor));

        // 防止 RGB 值小于 0
        red = Math.max(0, red);
        green = Math.max(0, green);
        blue = Math.max(0, blue);

        return new Color(red, green, blue);
    }

}
