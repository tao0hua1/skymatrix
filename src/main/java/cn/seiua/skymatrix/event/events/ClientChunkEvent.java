package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class ClientChunkEvent extends Event {
    private ClientWorld world;
    private WorldChunk worldChunk;

    public ClientChunkEvent(ClientWorld world, WorldChunk worldChunk) {
        this.world = world;
        this.worldChunk = worldChunk;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(ClientWorld world) {
        this.world = world;
    }

    public WorldChunk getWorldChunk() {
        return worldChunk;
    }

    public void setWorldChunk(WorldChunk worldChunk) {
        this.worldChunk = worldChunk;
    }
}
