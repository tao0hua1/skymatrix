package cn.seiua.skymatrix.event.events;


import cn.seiua.skymatrix.event.Event;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class InteractItemEvent extends Event {
    private ItemStack itemStack;
    private Hand hand;

    public InteractItemEvent(ItemStack itemStack, Hand hand) {
        this.itemStack = itemStack;
        this.hand = hand;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
