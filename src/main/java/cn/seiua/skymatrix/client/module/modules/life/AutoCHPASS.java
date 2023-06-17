package cn.seiua.skymatrix.client.module.modules.life;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ServerPacketEvent;
import cn.seiua.skymatrix.utils.ReflectUtils;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;

import java.util.Arrays;

@Event
@Sign(sign = Signs.FREE)
@SModule(name = "AutoRenewPass", category = "life")
public class AutoCHPASS {
    @Value(name = "keyBind")
    public KeyBind keyBind = new KeyBind(Arrays.asList(), ReflectUtils.getModuleName(this));

    @EventTarget
    public void onPacket(ServerPacketEvent event) {
        if (event.getPacket() instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket eventPacket = (GameMessageS2CPacket) event.getPacket();

            if (eventPacket.content().getString().contains("Your pass to the Crystal Hollows will expire in 1 minute")) {
                SkyMatrix.mc.getNetworkHandler().sendCommand("purchasecrystallhollowspass");
            }
        }
    }


}
