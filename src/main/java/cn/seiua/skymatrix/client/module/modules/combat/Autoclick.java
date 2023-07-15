package cn.seiua.skymatrix.client.module.modules.combat;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.IToggle;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.Hide;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.DoubleValueSlider;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.config.option.ToggleSwitch;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.utils.ReflectUtils;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;

import java.util.Arrays;
import java.util.Random;


@Event
@Sign(sign = Signs.FREE)
@SModule(name = "autoclick", category = "combat")
public class Autoclick implements IToggle {
    @Value(name = "keyBind")
    public KeyBind keyBind = new KeyBind(Arrays.asList(), ReflectUtils.getModuleName(this));
    @Value(name = "left click")
    @Sign(sign = Signs.BETA)
    public ToggleSwitch lc = new ToggleSwitch(true);
    @Value(name = "right click")
    @Sign(sign = Signs.BETA)
    public ToggleSwitch rc = new ToggleSwitch(true);

    @Value(name = "left delay")
    @Hide(following = "left click")
    @Sign(sign = Signs.FREE)
    public DoubleValueSlider lDelay = new DoubleValueSlider(7, 13, 0, 20, 1);
    @Value(name = "right delay")
    @Hide(following = "right click")
    @Sign(sign = Signs.FREE)
    public DoubleValueSlider rDelay = new DoubleValueSlider(7, 13, 0, 20, 1);

    int tick;


    @EventTarget
    public void onTick(ClientTickEvent event) {
        Random random = new Random();

        if (lc.isValue() && (SkyMatrix.mc.options.attackKey.isPressed()) && tick > 0) {
            double rv = lDelay.getRandomValue();
            if (rv == 0) {
                return;
            }
            float r = (float) (rv / 20f);
            if (Float.isNaN(r)) {
                r = 1f;
            }
            float ff = random.nextFloat();
            if (ff <= r) {
                if (SkyMatrix.mc.targetedEntity != null) {
                    SkyMatrix.mc.interactionManager.attackEntity(SkyMatrix.mc.player, SkyMatrix.mc.targetedEntity);
                    SkyMatrix.mc.player.swingHand(Hand.MAIN_HAND);
                } else {
                    if (SkyMatrix.mc.crosshairTarget.getType().equals(HitResult.Type.MISS)) {
                        SkyMatrix.mc.player.swingHand(Hand.MAIN_HAND);
                    }
                }
            }
        }

        if (rc.isValue() && (SkyMatrix.mc.options.useKey.isPressed()) && tick > 0) {
            double rv = rDelay.getRandomValue();
            if (rv == 0) {
                return;
            }
            float r = (float) (rv / 20f);
            if (Float.isNaN(r)) {
                r = 1f;
            }
            float ff = random.nextFloat();
            if (ff <= r) {
                if (!SkyMatrix.mc.player.isUsingItem())
                    SkyMatrix.mc.doItemUse();

            }
        }
        if ((SkyMatrix.mc.options.useKey.isPressed()) || (SkyMatrix.mc.options.attackKey.isPressed())) {
            tick++;
        } else {
            tick = 0;
        }
    }


    @Override
    public void disable() {
    }

    @Override
    public void enable() {

    }
}
