package cn.seiua.skymatrix.event.events;


import cn.seiua.skymatrix.event.Event;
import net.minecraft.util.math.BlockPos;

public class BlockRenderEvent extends Event {


    private BlockPos blockPos;

    public BlockRenderEvent(BlockPos blockPos) {
        this.blockPos = blockPos;

    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
