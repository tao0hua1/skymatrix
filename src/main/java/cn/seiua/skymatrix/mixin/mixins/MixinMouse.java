package cn.seiua.skymatrix.mixin.mixins;


import cn.seiua.skymatrix.event.events.MouseEvent;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Mixin(Mouse.class)
public class MixinMouse {
    @Inject(at = @At("HEAD"), method = "onMouseButton")
    private void onOnKey(long window, int button, int action, int mods, CallbackInfo ci) {
//        if (action == GLFW.GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null) {
        new MouseEvent(window, button, action, mods).call();
//        }
    }

    @Inject(at = @At("HEAD"), method = "onMouseScroll")
    private void onOnKey(long window, double horizontal, double vertical, CallbackInfo ci) {
//        if (action == GLFW.GLFW_PRESS && MinecraftClient.getInstance().currentScreen == null) {
//        new KeyboardEvent(keyCode,scanCode,action).call();
//        }
    }
}