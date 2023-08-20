package cn.seiua.skymatrix.client.module.modules.skyblock;

import cn.seiua.skymatrix.client.HypixelWay;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.component.Use;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.client.module.modules.ModuleTools;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.ToggleSwitch;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.utils.OneTickTimer;
import cn.seiua.skymatrix.utils.TickTimer;

@Event
@Sign(sign = Signs.FREE)
@SModule(name = "AutoJoinSkyBlock", category = "skyblock")
public class AutoJoinSkyBlock {


    @Value(name = "anti Limbo")
    private ToggleSwitch limbo = new ToggleSwitch(false);
    @Use
    HypixelWay hypixelWay;

    private OneTickTimer limboTimer = TickTimer.build(40, 0, () -> {
        ModuleTools.execute("lobby");
    });
    private OneTickTimer skyblock = TickTimer.build(40, 0, () -> {
        ModuleTools.execute("play skyblock");
    });

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (hypixelWay.way().equals("LIMBO") && limbo.isValue()) {
            if (this.limboTimer.getTick() < 0) {
                limboTimer.reset();
            }
        }
        if (hypixelWay.way().equals("NONE")) {
            if (this.skyblock.getTick() < 0) {
                skyblock.reset();
            }

        }
        limboTimer.update();
        skyblock.update();
    }

}
