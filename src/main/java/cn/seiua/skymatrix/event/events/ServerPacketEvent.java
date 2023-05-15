package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.network.packet.Packet;

public class ServerPacketEvent extends Event {

    private Packet packet;

    public ServerPacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

}
