package cn.seiua.skymatrix.event.events;


import cn.seiua.skymatrix.event.Event;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;

public class FluidRenderEvent extends Event {


    private BlockPos blockPos;
    private FluidState fluidState;

    public FluidRenderEvent(BlockPos blockPos, FluidState fluidState) {
        this.blockPos = blockPos;
        this.fluidState = fluidState;

    }

    public FluidState getFluidState() {
        return fluidState;
    }

    public void setFluidState(FluidState fluidState) {
        this.fluidState = fluidState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
