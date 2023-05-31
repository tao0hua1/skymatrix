package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.event.events.FluidRenderEvent;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "me.jellysquid.mods.sodium.client.render.chunk.compile.pipeline.FluidRenderer")
public class MixinSodiumFluidRenderer {

    @Inject(method = "isFluidOccluded", at = @At(value = "HEAD"), remap = false)
    private void isFluidOccluded(BlockRenderView world, int x, int y, int z, Direction dir, Fluid fluid, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (dir.equals(Direction.UP)) {
            BlockPos blockPos = new BlockPos(x, y, z);
            new FluidRenderEvent(blockPos, SkyMatrix.mc.world.getFluidState(blockPos)).call();
        }
    }
}
