package cn.seiua.skymatrix.hud;

import net.minecraft.client.util.math.MatrixStack;

public interface Hud {
    void draw(MatrixStack matrixStack, float x, float y);


    int getHudWidth();

    int getHudHeight();

}
