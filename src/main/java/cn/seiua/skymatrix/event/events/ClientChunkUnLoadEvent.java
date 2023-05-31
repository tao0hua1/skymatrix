package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;

public class ClientChunkUnLoadEvent extends Event {
    private int chunkX;
    private int chunkZ;

    public ClientChunkUnLoadEvent(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }
}
