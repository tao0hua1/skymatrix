package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.component.*;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;

import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import net.minecraft.client.MinecraftClient;

import net.minecraft.client.gui.screen.Screen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

@Component
@Event(register = true)
public final class Client {


    private static final Logger logger = LogManager.getLogger();
    @Use
    private ConfigManager configManager;
    @Use
    private EventManager eventManager;
    @Use
    private ConnectManager connectManager;
    public int stage;
    public static File root=new File(MinecraftClient.getInstance().runDirectory,"skymartix");
    private Client instance;


    @Init(level = 999)
    public void start() {

        root.mkdirs();


    }

    private boolean flag;
//    @SubscribeEvent
//    public void render(RenderGameOverlayEvent e) {
//        if (flag == false) {
//            FontUtils.init();
//        }
//    }
//
    @EventTarget
    public void ClientTickEvent (ClientTickEvent e){
        if(MinecraftClient.getInstance().world!=null){
            updataGuiScreen();
        }
    }
    @Use
    public List<Screen> guiScreens;

    private Screen targetGui;
    public void openGui(Class gui){
        for (Screen guiScreen: guiScreens) {
            if(guiScreen.getClass()==gui){
                logger.debug("display guiscreen: "+guiScreen);
                targetGui=guiScreen;
                return;
            }
        }
    }

    private void updataGuiScreen(){

        if(targetGui!=null){
            MinecraftClient.getInstance().setScreen(targetGui);
            targetGui=null;
        }

    }




}
