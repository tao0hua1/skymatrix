package xyz.Melody.Injection.tooltips;

import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
//
//    @Shadow
//    private MinecraftClient client;
//
//    @Shadow
//    private VertexConsumerProvider.Immediate vertexConsumers;
//
//    @Shadow
//    private MatrixStack matrices;
//
//    @Shadow
//    public abstract int getScaledWindowWidth();
//
//    @Shadow
//    public abstract int getScaledWindowHeight();
//
//    @Shadow
//    @Deprecated
//    public abstract void draw(Runnable drawCallback);
//
//    @Inject(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V", at = @At("HEAD"), cancellable = true)
//    private void drawTooltip(TextRenderer textRenderer, List<TooltipComponent> components, int x, int y,
//
//
//    }
}
