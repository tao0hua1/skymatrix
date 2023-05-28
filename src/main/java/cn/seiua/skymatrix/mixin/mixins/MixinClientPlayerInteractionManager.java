package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.event.events.InteractItemEvent;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinClientPlayerInteractionManager {

    @Inject(at = @At("HEAD"), method = "interactItem")
    public void onInteractItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        InteractItemEvent event = new InteractItemEvent(player.getStackInHand(hand), hand);
        event.call();

        if (event.isCancelled()) cir.cancel();
    }


}
