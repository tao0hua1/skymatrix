package cn.seiua.skymatrix.mixin.mixins;

import cn.seiua.skymatrix.event.events.BlockChangeEvent;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class MixinClientWorld {


    @Inject(
            at = @At(value = "INVOKE",
                    target = "net/minecraft/world/World.setBlockState (Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z",
                    opcode = Opcodes.INVOKESPECIAL,
                    shift = At.Shift.BEFORE,
                    ordinal = 0),
            method = "handleBlockUpdate")
    public void handleBlockUpdate(BlockPos pos, BlockState state, int flags, CallbackInfo ci) {
        new BlockChangeEvent(MinecraftClient.getInstance().world.getBlockState(pos), state, pos).call();
    }

}
