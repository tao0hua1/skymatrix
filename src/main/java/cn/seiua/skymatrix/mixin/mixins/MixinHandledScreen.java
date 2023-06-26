package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.event.events.DrawSlotEvent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class MixinHandledScreen {
    @Inject(method = "drawSlot", at = @At(value = "HEAD"))
    public void drawSlot(DrawContext context, Slot slot, CallbackInfo ci) {
        new DrawSlotEvent(context, slot).call();
    }
}
