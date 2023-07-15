package cn.seiua.skymatrix.client.module.modules.life;

import baritone.api.BaritoneAPI;
import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.IToggle;
import cn.seiua.skymatrix.client.RotationFaker;
import cn.seiua.skymatrix.client.SmoothRotation;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.component.Use;
import cn.seiua.skymatrix.client.module.ModuleManager;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.client.waypoint.WPProcess;
import cn.seiua.skymatrix.client.waypoint.Waypoint;
import cn.seiua.skymatrix.client.waypoint.WaypointGroupEntity;
import cn.seiua.skymatrix.config.Hide;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.*;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.*;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.Icons;
import cn.seiua.skymatrix.hud.ClientHud;
import cn.seiua.skymatrix.hud.Hud;
import cn.seiua.skymatrix.message.Message;
import cn.seiua.skymatrix.message.MessageBuilder;
import cn.seiua.skymatrix.utils.*;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.List;
import java.util.*;

@Event
@Sign(sign = Signs.FREE)
@SModule(name = "farmingHelper", category = "life")

public class FarmingHelper implements IToggle, Hud {

    @Use
    RotationFaker rotationFaker;
    @Use
    Waypoint waypoint;
    @Use
    SmoothRotation smoothRotation;

    @Value(name = "keyBind")
    public KeyBind keyBind = new KeyBind(Arrays.asList(), ReflectUtils.getModuleName(this));
    @Value(name = "farm hud")
    public ClientHud clientHud = new ClientHud(100, 100, true, this);
    @Value(name = "angle")
    ValueSlider angle = new ValueSlider(50, 0, 180, 0.1f);
    @Value(name = "range")
    ValueSlider range = new ValueSlider(4, 0, 6, 1);
    @Value(name = "crops")
    ToggleSwitch crops = new ToggleSwitch(false);
    @Value(name = "blocks")
    @Hide(following = "crops")
    MultipleChoice blocks = new MultipleChoice(SkyBlockUtils.getAllCrop(), Icons.ORE);
    @Value(name = "waypoint")
    ValueInput waypointName = new ValueInput("", Icons.LOCATION);
    @Value(name = "keep world")
    ToggleSwitch keep = new ToggleSwitch(false);
    @Value(name = "escape")
    ToggleSwitch escape = new ToggleSwitch(false);

    @Value(name = "cmd")
    @Hide(following = "escape")
    ValueInput cmd = new ValueInput("", Icons.CMD);

    @Value(name = "waiting time(m)")
    @Hide(following = "escape")
    ValueSlider wTime = new ValueSlider(4, 0, 6, 1);
    @Value(name = "escapes")
    @Hide(following = "escape")
    MultipleChoice escapeWay = new MultipleChoice(Map.of("slot", false, "fov", false, "location", false), Icons.MODE);
    private Message message = MessageBuilder.build("farming helper");
    private List<BlockPos> blockPos;
    private BlockPos target;
    private Status status = Status.NONE;

    public static enum Status {
        NONE, DOING, ESCAPING
    }

    @EventTarget
    public void onPacket(ServerPacketEvent event) {
        if (this.status == Status.ESCAPING) return;
        if (event.getPacket() instanceof UpdateSelectedSlotS2CPacket) {
            UpdateSelectedSlotS2CPacket packet = (UpdateSelectedSlotS2CPacket) event.getPacket();
            if (packet.getSlot() != SkyMatrix.mc.player.getInventory().selectedSlot) {
                escape("slot");
                this.status = Status.ESCAPING;
                event.setCancelled(true);
            }
        }
        if (event.getPacket() instanceof PlayerPositionLookS2CPacket) {
            PlayerPositionLookS2CPacket packet = (PlayerPositionLookS2CPacket) event.getPacket();
            if (!packet.getFlags().contains((Object) PositionFlag.X_ROT) || (!packet.getFlags().contains((Object) PositionFlag.Y_ROT))) {

                if (this.escape.isValue()) {

                    if (this.escapeWay.getValue().get("location")) {
                        boolean bl = packet.getFlags().contains((Object) PositionFlag.X);
                        boolean bl2 = packet.getFlags().contains((Object) PositionFlag.Y);
                        boolean bl3 = packet.getFlags().contains((Object) PositionFlag.Z);
                        if ((!bl) || (!bl2) || (!bl3)) {
                            if (SkyMatrix.mc.player.getBlockPos().toCenterPos().distanceTo(new Vec3d(packet.getX(), packet.getY(), packet.getZ())) > 10) {
                                this.escape("location");
                                message.sendWarningMessage(Text.of("§4Admin Location Transfrom!"));
                                this.status = Status.ESCAPING;
                            }
                        }

                    }
                    if (this.escapeWay.getValue().get("fov")) {
                        message.sendWarningMessage(Text.of("§4Admin Rotation!"));
                        this.status = Status.ESCAPING;
                    }
                }


            }
        }
    }

    public void escape(String type) {

        this.wpProcess.stop();
        this.oneTickTimer = TickTimer.build(20 * 60 * this.wTime.getValue().intValue(), this::continua);
    }

    public void continua() {
        this.status = Status.NONE;
        wpProcess.start();
    }

    public void keepWorld() {
        SkyMatrix.mc.getNetworkHandler().sendCommand(this.cmd.getValue());
        worldChange1 = TickTimer.build(40, this::keepWorld1);
        this.status = Status.NONE;
        this.oneTickTimer = null;
    }

    private void keepWorld1() {
        this.status = Status.NONE;
        this.oneTickTimer = null;
        this.wpProcess.start();
    }

    OneTickTimer oneTickTimer;
    OneTickTimer worldChange;
    OneTickTimer worldChange1;

    @EventTarget
    public void onWorldChange(WorldChangeEvent e) {
        if (this.keep.isValue()) {
            this.wpProcess.stop();
            this.worldChange = TickTimer.build(60, this::keepWorld);
        }

    }

    public boolean isTarget(BlockPos bp) {

        String name = BlockUtils.getName(bp).replace("minecraft:", "");
        name = SkyBlockUtils.getNameByMapper(name);
        BlockState blockState = SkyMatrix.mc.world.getBlockState(bp);
        if (blockState.isAir() || SkyMatrix.mc.world.isWater(bp) || SkyMatrix.mc.world.getFluidState(bp).getFluid().isIn(FluidTags.LAVA))
            return false;
        if (blocks.value.containsKey(name)) {
            if (blocks.value.get(name)) {
                switch (name) {
                    case "sugar_cane", "cactus" -> {
                        BlockPos bp1 = bp.down();
                        return SkyMatrix.mc.world.getBlockState(bp1).getBlock() == SkyMatrix.mc.world.getBlockState(bp).getBlock();
                    }
                    case "nether_wart" -> {
                        int age = SkyMatrix.mc.world.getBlockState(bp).get(IntProperty.of("age", 0, 3));
                        return age == 3;
                    }
                    case "carrots", "potatoes", "wheat" -> {
                        int age = SkyMatrix.mc.world.getBlockState(bp).get(IntProperty.of("age", 0, 7));
                        return age == 7;
                    }
                    default -> {
                        return true;
                    }
                }


            }
        }
        return false;
    }


    private HashMap<String, Integer> black = new HashMap<>();

    @EventTarget
    public void onUpdateTarget(UpdateTargetedEntityEvent.Over e) {
        if (SkyMatrix.mc.currentScreen == null && SkyMatrix.mc.options.attackKey.isPressed()) {
            if (SkyMatrix.mc.crosshairTarget instanceof BlockHitResult) {
                BlockHitResult blockHitResult = (BlockHitResult) SkyMatrix.mc.crosshairTarget;
//                if (!this.isTarget(blockHitResult.getBlockPos())) {
//                    SkyMatrix.mc.crosshairTarget = null;
//                }
            }
        }

    }

    int tick;

    @EventTarget
    public void onTick(ClientTickEvent e) {

        if (tick % 10 == 0) {
            Client.sendDebugMessage(Text.of(String.valueOf(count)));
            BaritoneAPI.getSettings().allowSprint.value = count != 10;
            count = 0;
        }
        tick++;
        if (this.oneTickTimer != null) {
            oneTickTimer.update();
        }
        if (this.worldChange != null) {
            worldChange.update();
        }
        if (this.worldChange1 != null) {
            worldChange1.update();
        }
        ArrayList<String> s = new ArrayList<>(this.black.keySet());
        for (String hash : s) {
            this.black.put(hash, this.black.get(hash) - 1);
            if (this.black.get(hash) <= 0) {
                this.black.remove(hash);
            }
        }
        if (this.status == Status.ESCAPING) {
            this.wpProcess.stop();
            return;
        }
        if (!(SkyMatrix.mc.currentScreen instanceof HandledScreen<?>)) {
            if (this.wpProcess != null) {
                if (this.wpProcess.getCurrentIndex() <= 1) {
                    BaritoneAPI.getSettings().allowSprint.value = true;
                    return;
                }
            }
            assert SkyMatrix.mc.player != null;
            ItemStack is = SkyMatrix.mc.player.getInventory().getMainHandStack();
            String type = SkyBlockUtils.getItemType(is);
            String name = is.getItem().getName().getString();
            assert SkyMatrix.mc.crosshairTarget != null;
            if (SkyMatrix.mc.crosshairTarget.getType() == HitResult.Type.BLOCK) {
                SkyMatrix.mc.doAttack();
            }
            if (name.contains("Air")) return;
            if ((name.contains("Pickaxe") || name.contains("Axe") || name.contains("Shovel") || name.contains("Hoe")) || ((type != null) && (type.equals("AXE") || type.equals("DRILL") || type.equals("PICKAXE") || type.equals("SHOVEL")))) {
//                SkyMatrix.mc.options.attackKey.setPressed(true);
                blockPos = new ArrayList<>();

                Vec3d viewPos = Vec3d.fromPolar(rotationFaker.getServerPitch(), rotationFaker.getServerYaw());
                Vec3d viewc = SkyMatrix.mc.player.getRotationVecClient();
                Vec3d playerPos = SkyMatrix.mc.player.getEyePos();
                BlockPos blockPos1 = new BlockPos((int) playerPos.x, (int) playerPos.y, (int) playerPos.z);
                Vec3d targetVec = null;
                double lastAngle = 100000;
                double lastD = 100000;
                int flag = 0;
                Vec3d vec3d = null;
                int RANGE = range.getValue().intValue() * 2;
                for (int i = 0; i < RANGE; i++) {
                    for (int j = 0; j < RANGE; j++) {
                        for (int k = 0; k < RANGE; k++) {
                            BlockPos cp = blockPos1.add(i - RANGE / 2, j - RANGE / 2, k - RANGE / 2);
                            if (this.black.getOrDefault(Objects.hash(cp.getX(), cp.getY(), cp.getZ()), 0) > 0)
                                continue;
                            if (SkyMatrix.mc.player.getCameraPosVec(1.0f).distanceTo(cp.toCenterPos()) > range.getValue().doubleValue()) {
                                continue;
                            }
                            if (MathUtils.calculateAngle(viewc, cp.toCenterPos().subtract(playerPos)) > angle.getValue().doubleValue())
                                continue;
                            if (!isTarget(cp)) {
                                continue;
                            }
                            vec3d = canSee(cp);


                            if (vec3d != null) {
                                Vec3d vec3d1 = cp.toCenterPos().subtract(playerPos);
                                double angle = MathUtils.calculateAngle(vec3d1, viewPos);
                                double d = vec3d1.length();

                                if (target == null) {
                                    target = cp;
                                    lastAngle = angle;
                                    continue;
                                } else {
//                                    if (angle < lastAngle) {
//                                        lastAngle = angle;
//                                        targetVec = vec3d;
//                                        target = cp;
//                                        flag++;
//                                    }
                                    if (d < lastD) {
                                        lastD = d;
                                        targetVec = vec3d;
                                        target = cp;
                                        flag++;
                                    }
                                }

                            }
                        }
                    }
                }
                if (flag != 0) {
                    status = Status.DOING;
                    Vec3d vec3d1 = targetVec.multiply(targetVec.length());
                    rotationFaker.keepServer();
                    smoothRotation.smoothLook(RotationUtils.toRotation(vec3d1), 2f, null, false);
//                    System.out.println(target);
                    SkyMatrix.mc.interactionManager.attackBlock(target, Direction.UP);
                    SkyMatrix.mc.player.swingHand(Hand.MAIN_HAND);
                    count++;
                    black.put(String.valueOf(Objects.hash(this.target.getX(), this.target.getY(), this.target.getZ())), 20);
                }
            }
            rotationFaker.release();
        } else {

        }

    }

    int count = 0;
    private static final ArrayList<Vec3d> SHIFTING = new ArrayList<>();

    static {

        SHIFTING.add(new Vec3d(0.5, 0, 0));
        SHIFTING.add(new Vec3d(0, 0.5, 0));
        SHIFTING.add(new Vec3d(0, 0, 0.5));
        SHIFTING.add(new Vec3d(-0.5, 0, 0));
        SHIFTING.add(new Vec3d(0, -0.5, 0));
        SHIFTING.add(new Vec3d(0, 0, -0.5));


    }

    public Vec3d traversal(BlockPos blockPos) {
        assert SkyMatrix.mc.player != null;
        Vec3d viewPos = SkyMatrix.mc.player.getRotationVecClient();
        Vec3d playerPos = SkyMatrix.mc.player.getEyePos();
        SHIFTING.clear();
        SHIFTING.add(new Vec3d(0.5, 0, 0));
        SHIFTING.add(new Vec3d(0, 0, 0.5));
        SHIFTING.add(new Vec3d(-0.5, 0, 0));
        SHIFTING.add(new Vec3d(0, -0.5, 0));
        SHIFTING.add(new Vec3d(0, 0, -0.5));
        for (Vec3d vec3dv : SHIFTING) {

            Vec3d vec3d1 = blockPos.toCenterPos().subtract(playerPos);
            double angle = MathUtils.calculateAngle(vec3d1, viewPos);

            Vec3d vec3d = SkyMatrix.mc.player.getCameraPosVec(1.0f);
            float dd = 4.5F;
            Vec3d vec3d2 = new Vec3d(1, 1, 1);
            Vec3d bvec = blockPos.toCenterPos().add(vec3dv).subtract(vec3d);
            bvec = bvec.multiply(1 / bvec.length());

            Vec3d vec3d3 = vec3d.add(bvec.x * dd, bvec.y * dd, bvec.z * dd);
            return bvec;
//            HitResult hitResult = SkyMatrix.mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, SkyMatrix.mc.player));
//            if (hitResult instanceof BlockHitResult) {
//                BlockHitResult blockHitResult = (BlockHitResult) hitResult;
//                if (blockHitResult.getBlockPos().equals(blockPos)) {
////                    System.out.println("OK" + vec3dv.add(blockPos.getX(), vec3dv.getY(), vec3dv.getZ()));
//                    return bvec;
//                }
//            }
        }
        return null;
    }

    public Vec3d canSee(BlockPos blockPos) {

        return traversal(blockPos);

    }


    @EventTarget
    public void onRender(WorldRenderEvent e) {


        HitResult hitResult = SkyMatrix.mc.crosshairTarget;

        if (hitResult instanceof BlockHitResult blockHitResult) {
            e.getMatrixStack().push();
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderUtils.translateView(e.getMatrixStack());
            RenderUtils.setColor(new Color(0, 255, 220, 20));
            LivingEntity player = SkyMatrix.mc.player;
            RenderUtils.translatePos(e.getMatrixStack(), blockHitResult.getBlockPos());
            RenderUtils.drawSolidBox(new Box(0, 0, 0, 1, 1, 1), e.getMatrixStack());
            e.getMatrixStack().pop();
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
        }


    }

    WPProcess wpProcess = null;

    @Override
    public void disable() {
        this.target = null;
        if (wpProcess != null) {
            wpProcess.stop();
        }
        SkyMatrix.mc.options.attackKey.setPressed(false);
    }

    @Override
    public void enable() {
        this.status = Status.NONE;
        this.oneTickTimer = null;
        WaypointGroupEntity waypointGroupEntity = waypoint.getByName(this.waypointName.getValue());


        if (waypointGroupEntity == null) {
            ModuleManager.instance.disable(this);
            Client.sendDebugMessage(Text.of(waypoint.getLastMessage()));
        } else {
            wpProcess = new WPProcess(true, waypointGroupEntity);
            wpProcess.start();
        }

    }

    @Override
    public void draw(MatrixStack matrixStack, float x, float y) {

        RenderUtils.resetCent();
        RenderUtils.setColor(new Color(0, 0, 0, 100));
        RenderUtils.drawSolidBox(new Box(x, y, 0, x + getHudWidth(), y + getHudHeight(), 0), matrixStack);
        ClickGui.fontRenderer20.setColor(Color.WHITE);
        ClickGui.fontRenderer20.resetCenteredH();
        ClickGui.fontRenderer20.resetCenteredV();
        float startX = x + 15;
        float startY = y + 12;

        ClickGui.fontRenderer20.drawString(matrixStack, startX, startY, getV());


    }

    @Override
    public int getHudWidth() {

        return ClickGui.fontRenderer20.getStringWidth(getV()) + 32;

    }

    public String getV() {
        var v1 = "0%";
        var v2 = "0";
        if (this.wpProcess != null) {
            v1 = ((int) (this.wpProcess.getCDegree() * 100)) + "%";
        }

        if (this.wpProcess != null) {
            v2 = String.valueOf(this.wpProcess.getCount() + 1);
        }
        return String.format("当前路径点完成度: %s 第%s个循环 每 10tick 挖掘次数 %d 状态 %s %dm", v1, v2, this.count, this.status, this.oneTickTimer != null ? this.oneTickTimer.getTick() / 1200 : 0);
    }

    @Override
    public int getHudHeight() {
        return 53;
    }
}
