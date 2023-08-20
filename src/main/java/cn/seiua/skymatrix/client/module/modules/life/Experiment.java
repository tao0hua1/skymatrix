package cn.seiua.skymatrix.client.module.modules.life;


import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.config.option.ValueSlider;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.event.events.DrawSlotEvent;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.utils.ColorUtils;
import cn.seiua.skymatrix.utils.ReflectUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Event
@Sign(sign = Signs.FREE)
@SModule(name = "experiment", category = "life")
public class Experiment {
    @Value(name = "keyBind")
    public KeyBind keyBind = new KeyBind(Arrays.asList(), ReflectUtils.getModuleName(this));
    @Value(name = "delay(tick)")
    ValueSlider angle = new ValueSlider(5, 0, 20, 1);

    private enum ExperimentType {
        CHRONOMATRON, ULTRASEQUENCER
    }

    private enum ExperimentStatus {
        RECORDING, DOING
    }

    private ExperimentType type;
    private ExperimentStatus status;
    private ArrayList<Integer> clickSlots;
    int index;
    boolean switc;

    int tick;

    private Slot last;

    private HashMap<Integer, Integer> mapper = new HashMap<>();

    @EventTarget
    public void onDrawSlot(DrawSlotEvent e) {
        //render
        if (status == ExperimentStatus.DOING) {
            if (this.type == ExperimentType.ULTRASEQUENCER) {

                if (mapper != null) {

                    if (mapper.get(e.getSlot().id) != null) {
                        MatrixStack matrixStack = e.getContext().getMatrices();
                        matrixStack.push();
                        float scale = UI.getS();
                        matrixStack.scale(1f / scale, 1f / scale, 1f / scale);
                        int index = mapper.get(e.getSlot().id);
                        if (index == -1) return;
                        RenderSystem.enableDepthTest();
                        ClickGui.fontRenderer22.centeredH();
                        ClickGui.fontRenderer22.setColor(ColorUtils.setHue(Color.RED, index * 1.0f / 20));
                        ClickGui.fontRenderer22.drawString(matrixStack, e.getSlot().x * scale, e.getSlot().y * scale + 8, 10, String.valueOf(index));
                        ClickGui.fontRenderer22.resetCenteredV();
                        ClickGui.fontRenderer22.resetCenteredH();
                        RenderSystem.disableDepthTest();
                        matrixStack.pop();
                    }
                }

            }
        }
        //
        if (status == ExperimentStatus.RECORDING) {
            if (this.type == ExperimentType.CHRONOMATRON) {
                String name = e.getSlot().getStack().getItem().getName().getString();
                if (last != null && e.getSlot().id == last.id) {
                    ItemStack itemStack = last.inventory.getStack(last.getIndex());
                    int flag = 0;
                    if (!switc && !itemStack.getItem().getName().getString().contains("Terracotta")) {
                        flag++;
                    }
                    itemStack = last.inventory.getStack(last.getIndex() + 9);
                    if (!switc && !itemStack.getItem().getName().getString().contains("Terracotta")) {
                        flag++;
                    }
                    itemStack = last.inventory.getStack(last.getIndex() + 18);
                    if (!switc && !itemStack.getItem().getName().getString().contains("Terracotta")) {
                        flag++;
                    }
                    if (flag == 3) {
                        switc = true;
                    }
                }
                if (name.contains("Terracotta")) {
                    if (switc) {
                        last = e.getSlot();
                        clickSlots.add(last.id);
                        switc = false;
                    }

                }
            }
            if (this.type == ExperimentType.ULTRASEQUENCER) {
                try {
                    int a = Integer.parseInt(e.getSlot().getStack().getName().getString());
                    if (this.clickSlots.size() + 1 == a) {
                        this.clickSlots.add(e.getSlot().id);
                        mapper.put(e.getSlot().id, a);
                    }
                } catch (NumberFormatException ee) {

                }
            }
        }


    }

    @EventTarget

    public void onTick(ClientTickEvent e) {
        tick++;
        if (SkyMatrix.mc.currentScreen != null) {
            if (SkyMatrix.mc.currentScreen instanceof GenericContainerScreen) {
                GenericContainerScreen screen = (GenericContainerScreen) SkyMatrix.mc.currentScreen;
                String type1 = screen.getTitle().getString();
                ScreenHandler screenHandler = screen.getScreenHandler();
                if (type1.contains("Experimentation")) {
                    this.switc = true;
                    status = null;
                    clickSlots = new ArrayList<>();

                }
                if (type1.contains("Experiment Over")) {
                    status = null;
                    return;
                }
                if (type1.contains("Chronomatron (")) {

                    type = ExperimentType.CHRONOMATRON;
                } else {
                    status = null;
                }
                if (type1.contains("Ultrasequencer (")) {
                    type = ExperimentType.ULTRASEQUENCER;
                } else {
                    status = null;
                }
                String name = screenHandler.getSlot(screenHandler.slots.size() - 41).getStack().getName().getString();
                if (name.equals("Remember the pattern!")) {
                    status = ExperimentStatus.RECORDING;
                    index = 0;

                } else {
                    if (type1.contains("Chronomatron (") || type1.contains("Ultrasequencer (")) {

                        status = ExperimentStatus.DOING;
                    } else {
                        status = null;
                        return;
                    }
                }
                if (status == ExperimentStatus.DOING) {
                    if (index >= clickSlots.size()) {
                        this.clickSlots = new ArrayList<>();
                        mapper = new HashMap<>();
                        return;
                    }
                    if (tick % angle.getValue().intValue() == 0) {
                        if (clickSlots.get(index) != null) {
                            SkyMatrix.mc.interactionManager.clickSlot(screenHandler.syncId, clickSlots.get(index), 0, SlotActionType.PICKUP, SkyMatrix.mc.player);
                        }
                        index++;
                    }

                }
            } else {
                status = null;
            }
        } else {
            status = null;
        }


    }

}
