package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.SkyMatrix;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;

public class BlockUtils {

    public static BlockState getState(BlockPos pos) {
        return SkyMatrix.mc.world.getBlockState(pos);
    }

    public static Block getBlock(BlockPos pos) {
        return getState(pos).getBlock();
    }

    public static int getId(BlockPos pos) {
        return Block.getRawIdFromState(getState(pos));
    }

    public static String getName(BlockPos pos) {
        return getName(getBlock(pos));
    }

    public static String getName(Block block) {
        return Registries.BLOCK.getId(block).toString();
    }


}
