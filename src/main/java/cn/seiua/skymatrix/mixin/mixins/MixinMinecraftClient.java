package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.event.events.GameExitEvent;
import cn.seiua.skymatrix.event.events.WorldChangeEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {


    @Inject(at = @At("HEAD"), method = "run")
    public void run(CallbackInfo ci) {
        System.setProperty("java.awt.headless", "false");
    }

    @Inject(at = @At("HEAD"), method = "close")
    public void close(CallbackInfo ci) {
        new GameExitEvent().call();
    }

    @Inject(at = @At("HEAD"), method = "joinWorld")
    public void joinWorld(ClientWorld world, CallbackInfo ci) {
        new WorldChangeEvent(world).call();
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onEndTick(CallbackInfo info) {
        new ClientTickEvent().call();
    }


}
