package cn.seiua.skymatrix.event.events;


import cn.seiua.skymatrix.event.Event;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockRenderEvent extends Event {


    private BlockPos blockPos;
    private BlockState blockState;

    public BlockRenderEvent(BlockPos blockPos, BlockState blockState) {
        this.blockPos = blockPos;
        this.blockState = blockState;

    }

    public BlockState getBlockState() {
        return blockState;
    }

    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
