package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.event.events.ViewClipEvent;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Camera.class)
public abstract class MixinCamera {

    @Shadow
    private float pitch;
    @Shadow
    private float yaw;
    @Shadow
    private Quaternionf rotation;

    @Shadow
    private Vector3f horizontalPlane;
    @Shadow
    private Vector3f verticalPlane;
    @Shadow
    private Vector3f diagonalPlane;

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
//
//    @Inject(method = "getYaw", at = @At("RETURN"), cancellable = true)
//    private void getYaw(CallbackInfoReturnable<Float> cir) {
//        HeadRotationEvent event = new HeadRotationEvent(pitch, yaw);
//        event.call();
//        cir.setReturnValue(event.getYaw());
//    }
//
//    @Inject(method = "getPitch", at = @At("RETURN"), cancellable = true)
//    private void getPitch(CallbackInfoReturnable<Float> cir) {
//        HeadRotationEvent event = new HeadRotationEvent(pitch, yaw);
//        event.call();
//        cir.setReturnValue(event.getPitch());
//    }

    @Inject(method = "update", at = @At("RETURN"))
    protected void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {


    }


}
