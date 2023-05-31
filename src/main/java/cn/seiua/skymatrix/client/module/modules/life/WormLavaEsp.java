package cn.seiua.skymatrix.client.module.modules.life;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.IToggle;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.ColorHolder;
import cn.seiua.skymatrix.config.option.DoubleValueSlider;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.event.events.FluidRenderEvent;
import cn.seiua.skymatrix.event.events.WorldChangeEvent;
import cn.seiua.skymatrix.event.events.WorldRenderEvent;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.RenderUtils;
import cn.seiua.skymatrix.utils.TickTimer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Event
@Sign(sign = Signs.FREE)
@SModule(name = "wormLavaESP", category = "life")
public class WormLavaEsp implements IToggle {

//Objects.hash(chunkX, chunkZ);

    public boolean tempDisable;
    private HashSet<BlockPos> renderList = new HashSet<>();
    @Value(name = "Color")
    private ColorHolder colorHolder = new ColorHolder(new Color(255, 184, 0, 100));
    @Value(name = "range")
    private DoubleValueSlider range = new DoubleValueSlider(6, 50, 0, 80, 0.5);
    private TickTimer tickTimer = new TickTimer(2, this::clear);

    @EventTarget
    public void onBlock(FluidRenderEvent e) {

        if (e.getFluidState().getFluid() instanceof LavaFluid) {
            FluidState fluidState = e.getFluidState();

            if ((Boolean) fluidState.getEntries().get(BooleanProperty.of("falling")) == false) {
                BlockPos blockPos = e.getBlockPos();
                if (blockPos.getY() >= 64 && blockPos.getX() >= 513 && blockPos.getZ() >= 513) {
                    renderList.add(blockPos);
                }

            }


        }
        tickTimer.setTick(1);

    }

    private void clear() {
        List<BlockPos> temp = new ArrayList<>();
        temp.addAll(renderList);
        for (BlockPos blockPos : temp) {
            if (!(SkyMatrix.mc.world.getFluidState(blockPos).getFluid() instanceof LavaFluid)) {
                renderList.remove(blockPos);
            }
        }

    }

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (tickTimer != null) {
            tickTimer.update();
        }
    }

    @EventTarget
    public void onWorldChange(WorldChangeEvent e) {
        this.renderList = new HashSet<>();
    }

    public boolean isTarget(BlockPos blockPos) {

        return SkyMatrix.mc.world.getBlockState(blockPos).toString().contains(":lava");
    }

    @EventTarget
    public void onRender(WorldRenderEvent e) {

        RenderSystem.disableDepthTest();
        ClickGui.fontRenderer28.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer28.centeredH();
        ClickGui.fontRenderer28.centeredV();


        ClientPlayerEntity player = SkyMatrix.mc.player;
        Vec3d camPos = SkyMatrix.mc.getBlockEntityRenderDispatcher().camera.getPos();
        BlockPos blockPos = SkyMatrix.mc.getBlockEntityRenderDispatcher().camera.getBlockPos();

        int regionX = (blockPos.getX() >> 9) * 512;
        int regionZ = (blockPos.getZ() >> 9) * 512;


        e.getMatrixStack().translate(regionX - camPos.x, -camPos.y,
                regionZ - camPos.z);


        BlockPos camPos1 = SkyMatrix.mc.getBlockEntityRenderDispatcher().camera.getBlockPos();
        int regionX1 = (camPos1.getX() >> 9) * 512;
        int regionZ1 = (camPos1.getZ() >> 9) * 512;
        RenderUtils.setColor(colorHolder.geColor());

//            e.getMatrixStack().translate(
//                    entity.prevX + (entity.getX() - entity.prevX) * e.getTickDelta() - regionX1,
//                    entity.prevY + (entity.getY() - entity.prevY) * e.getTickDelta(),
//                    entity.prevZ + (entity.getZ() - entity.prevZ) * e.getTickDelta() - regionZ1);
        HashSet<BlockPos> render = new HashSet<>();
        render.addAll(renderList);
        for (BlockPos blockPos1 : render) {
            double v = Math.sqrt(Math.pow(blockPos1.getX() - player.getX(), 2) + Math.pow(blockPos1.getZ() - player.getZ(), 2));
            if (!(v <= range.maxValue().doubleValue() && v >= range.minValue().doubleValue())) {
                continue;
            }

            e.getMatrixStack().push();
            e.getMatrixStack().translate(
                    blockPos1.getX() - regionX1,
                    blockPos1.getY(),
                    blockPos1.getZ() - regionZ1);
            RenderUtils.drawSolidBox(new Box(0, 0, 0, 1, 1, 1), e.getMatrixStack());
            e.getMatrixStack().pop();

        }


        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();

    }

    @Override
    public void disable() {
        this.renderList = null;
    }

    @Override
    public void enable() {
        SkyMatrix.mc.worldRenderer.reload();
        this.renderList = new HashSet<>();
    }
}
