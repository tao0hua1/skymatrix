package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.screen.slot.Slot;

public class DrawSlotEvent extends Event {
    private DrawContext context;
    private Slot slot;

    public DrawContext getContext() {
        return context;
    }

    public void setContext(DrawContext matrixStack) {
        this.context = context;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public DrawSlotEvent(DrawContext context, Slot slot) {
        this.context = context;
        this.slot = slot;
    }
}
