package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.client.world.ClientWorld;

public class WorldChangeEvent extends Event {
    private ClientWorld clientWorld;

    public WorldChangeEvent(ClientWorld world) {
        clientWorld = world;
    }
}
