package cn.seiua.skymatrix.font;

import net.minecraft.client.texture.DynamicTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ClientImageGenerator {

    private static int IMGSIZE = 1024;
    public static BufferedImage generateFontImages(char c, Font font) {
        String charr = c+"";
        BufferedImage bufferedImage = new BufferedImage(IMGSIZE, IMGSIZE, 2);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON );
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int positionX = 0;
        int positionY = 1;
        Rectangle2D dimensions = fontMetrics.getStringBounds(charr, graphics);
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect(0, 0, IMGSIZE, IMGSIZE);
        graphics.setColor(new Color(255, 255, 255, 255));
        graphics.drawString(charr, positionX, positionY + fontMetrics.getAscent());

        return bufferedImage.getSubimage(0, 0, (int)dimensions.getWidth(), (int)dimensions.getHeight());
    }



}
