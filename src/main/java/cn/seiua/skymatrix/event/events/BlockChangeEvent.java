package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockChangeEvent extends Event {

    BlockState old;

    BlockState ne;

    BlockPos pos;

    public BlockChangeEvent(BlockState old, BlockState ne, BlockPos pos) {
        this.old = old;
        this.ne = ne;
        this.pos = pos;
    }

    public BlockState getOld() {
        return old;
    }

    public void setOld(BlockState old) {
        this.old = old;
    }

    public BlockState getNe() {
        return ne;
    }

    public void setNe(BlockState ne) {
        this.ne = ne;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }
}
