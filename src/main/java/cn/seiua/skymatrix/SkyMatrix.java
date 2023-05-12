package cn.seiua.skymatrix;


import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.component.ComponentHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SkyMatrix implements ModInitializer, ClientTickEvents.StartTick {
    public static final String MODID="skymatrix";
public static final Logger LOGGER = LoggerFactory.getLogger("skymatrix");
    @Override
    public void onInitialize() {

        ClientTickEvents.START_CLIENT_TICK.register(this);

    }

    private boolean flag;
    @Override
    public void onStartTick(MinecraftClient client) {
               if(flag==false){
                   ComponentHandler.loadAllClasesName();
                   ComponentHandler.setup();
                   flag=true;
               }
    }
}
