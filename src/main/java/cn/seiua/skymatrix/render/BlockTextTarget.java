package cn.seiua.skymatrix.render;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class BlockTextTarget extends BlockTarget {

    private String text;

    public BlockTextTarget(BlockPos blockPos, GetColor geColor, String text) {
        super(blockPos, geColor);
        this.text = text;
    }

    @Override
    public void render(MatrixStack matrixStack, float delta) {
        super.render(matrixStack, delta);
        matrixStack.push();

        LivingEntity player = SkyMatrix.mc.player;
        RenderUtils.translatePos(matrixStack, super.getPos().toCenterPos().add(0, 0.9f, 0));
        float scale = 0.008F;
        double distance = player.getCameraPosVec(delta).distanceTo(this.getPos().toCenterPos());
        if (distance > 10)
            scale *= distance / 10;
        matrixStack.multiply(SkyMatrix.mc.getEntityRenderDispatcher().getRotation());
        matrixStack.scale(-scale, -scale, scale);
        ClickGui.fontRenderer24.setColor(new Color(255, 255, 255));
        ClickGui.fontRenderer24.centeredV();
        ClickGui.fontRenderer24.centeredH();
        ClickGui.fontRenderer24.drawString(matrixStack, 0, 0, 0, text);
        matrixStack.pop();
    }


}
