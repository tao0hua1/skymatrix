package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.event.events.ViewClipEvent;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public class MixinCamera {

    @ModifyVariable(at = @At("HEAD"),
            method = "clipToSpace(D)D",
            argsOnly = true)
    private double clipToSpaceHead(double desiredCameraDistance) {
        ViewClipEvent event = new ViewClipEvent(desiredCameraDistance, "HEAD");
        event.call();

        return event.getDis();
    }

    @Inject(method = "clipToSpace", at = @At("RETURN"), cancellable = true)
    private void clipToSpaceReturn(double desiredCameraDistance, CallbackInfoReturnable<Double> cir) {
        ViewClipEvent event = new ViewClipEvent(desiredCameraDistance, "RETURN");
        event.call();
        cir.setReturnValue(event.getDis());
    }


}
