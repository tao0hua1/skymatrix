package cn.seiua.skymatrix.mixin.mixins;


import cn.seiua.skymatrix.event.events.KeyboardEvent;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Project: FoxBase
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(at = @At("HEAD"), method = "onKey")
    private void onOnKey(long windowHandle, int keyCode, int scanCode, int action, int modifiers, CallbackInfo callbackInfo) {


        new KeyboardEvent(keyCode, scanCode, action).call();

    }
}