package cn.seiua.skymatrix.font;

import cn.seiua.skymatrix.SkyMatrix;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.io.IOException;

public class FontUtils {


    public static FontRenderer getFontRenderer(String fontname,int fontstyle){
        Identifier identifier=new Identifier(SkyMatrix.MODID,"font/"+fontname);
        MinecraftClient mc=MinecraftClient.getInstance();
        if(mc.getResourceManager()!=null){
            try {
                Font font=Font.createFont(Font.TRUETYPE_FONT,mc.getResourceManager().getResource(identifier).get().getInputStream());
                System.out.println(font.getFontName()+"               abc");
                font=font.deriveFont(fontstyle,512);
                font=font.deriveFont(512);
                return new FontRenderer(font);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else {
            throw new NullPointerException("");
        }

    }



}
