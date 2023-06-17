package cn.seiua.skymatrix.font;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.DynamicTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class CharInfo {

    private byte[] texture;
    private int height;
    private int width;
    private Identifier identifier;


    public CharInfo(byte[] texture, int height, int width) {
        this.texture = texture;
        this.height = height;
        this.width = width;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public byte[] getTexture() {
        return texture;
    }

    public void setTexture(byte[] texture) {
        this.texture = texture;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public int getGlid() {
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        if (identifier != null) {
            return textureManager.getTexture(identifier).getGlId();
        }
        DynamicTexture dynamicTexture = null;
        try {
            dynamicTexture = new NativeImageBackedTexture(NativeImage.read(texture));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        identifier = textureManager.registerDynamicTexture("textf", (NativeImageBackedTexture) dynamicTexture);
        return textureManager.getTexture(identifier).getGlId();
    }
}
