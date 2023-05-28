package cn.seiua.skymatrix.hud;

import net.minecraft.client.util.math.MatrixStack;

public interface Hud {
    void draw(MatrixStack matrixStack, int x, int y);


    int getHudWidth();

    int getHudHeight();

}
