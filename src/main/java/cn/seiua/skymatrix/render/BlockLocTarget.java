package cn.seiua.skymatrix.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

public class BlockLocTarget extends BlockTextTarget {


    public BlockLocTarget(BlockPos blockPos, GetColor geColor) {
        super(blockPos, geColor, String.format("x:%d y:%d z:%d", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    @Override
    public void render(MatrixStack matrixStack, float delta) {
        super.render(matrixStack, delta);
    }


}
