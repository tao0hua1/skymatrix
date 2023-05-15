package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.event.events.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {


    @Inject(at = @At("HEAD"), method = "run")
    public void run(CallbackInfo ci) {

    }

    @Inject(at = @At("RETURN"), method = "tick")
    private void onEndTick(CallbackInfo info) {
        new ClientTickEvent().call();
    }


}
