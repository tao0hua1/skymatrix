package cn.seiua.skymatrix.render;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.utils.MathUtils;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class BlockPointTarget extends BlockTarget {


    public BlockPointTarget(BlockPos blockPos, GetColor geColor) {
        super(blockPos, geColor);

    }

    @Override
    public void render(MatrixStack matrixStack, float delta) {
        matrixStack.push();
        RenderUtils.setColor(super.getColor.getColor());
        LivingEntity player = SkyMatrix.mc.player;
        assert player != null;
        int i = 1;
        if (SkyMatrix.mc.options.getPerspective().isFrontView()) {
            i = -1;
        }
        double angle = MathUtils.calculateAngle(SkyMatrix.mc.player.getRotationVec(delta).multiply(i), pos.toCenterPos().subtract(SkyMatrix.mc.gameRenderer.getCamera().getPos()));
        if (angle > 100) return;

        RenderUtils.translatePos(matrixStack, pos);
        RenderUtils.drawOutlineBox(new Box(0, 0, 0, 0.3, 0.3, 0.3), matrixStack);

        matrixStack.pop();
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public int hashCode() {
        return pos.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(pos);
    }
}
