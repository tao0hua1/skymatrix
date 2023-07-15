package cn.seiua.skymatrix.client.module.modules.combat;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.IToggle;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.utils.ReflectUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Event
@Sign(sign = Signs.FREE)
@SModule(name = "antibot", category = "combat")
public class AntiBot implements IToggle {
    @Value(name = "keyBind")
    public KeyBind keyBind = new KeyBind(Arrays.asList(), ReflectUtils.getModuleName(this));

    private Map<String, String> playerMap;

    @Init
    public void init() {


    }

    int tickCount;

    @EventTarget
    public void onTick(ClientTickEvent event) {
        Entity entity = SkyMatrix.mc.getEntityRenderDispatcher().targetedEntity;
        if (entity instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity ee = (AbstractClientPlayerEntity) entity;

        }

        tickCount++;
        if (tickCount % 40 == 0) {
            playerMap = new HashMap<>();
            int i = 0;
            for (UUID uuid : SkyMatrix.mc.player.networkHandler.getPlayerUuids()) {
                String name = SkyMatrix.mc.player.networkHandler.getPlayerListEntry(uuid).getProfile().getName();

                if (!name.startsWith("!")) {
                    if (SkyMatrix.mc.world.getPlayerByUuid(uuid) != null) {
                        if (!SkyMatrix.mc.world.getPlayerByUuid(uuid).getStatusEffects().isEmpty()) {
                            playerMap.put(uuid.toString(), name);
                        }
                    }


                }


            }
        }
    }

    public boolean isPlayer(String uuid) {
        if (playerMap == null) {
            return true;
        }

        if (playerMap.get(uuid) == null) {
            return false;
        }
        return true;
    }

    public boolean isBot(String uuid) {
        return !isPlayer(uuid);
    }

    @Override
    public void disable() {
        playerMap = null;
    }

    @Override
    public void enable() {

    }
}
