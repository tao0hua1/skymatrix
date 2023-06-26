package cn.seiua.skymatrix.event.events;


import cn.seiua.skymatrix.event.Event;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class InteractItemMEvent extends Event {
    private ItemStack itemStack;
    private Hand hand;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InteractItemMEvent(ItemStack itemStack, Hand hand, String type) {
        this.itemStack = itemStack;
        this.hand = hand;
        this.type = type;
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
