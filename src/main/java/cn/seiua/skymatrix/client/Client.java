package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.component.*;
import cn.seiua.skymatrix.client.config.Setting;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

@Component
@Event(register = true)
@Category(name = "client")
public final class Client {


    private static final Logger logger = LogManager.getLogger();
    @Use
    private ConfigManager configManager;
    @Use
    private EventManager eventManager;
    @Use
    private ConnectManager connectManager;
    @Use
    private Setting setting;
    public int stage;
    public static File root = new File(MinecraftClient.getInstance().runDirectory, "skymartix");
    private Client instance;


    @Init(level = 999)
    public void start() {

        root.mkdirs();


    }

    private boolean flag;

    @EventTarget
    public void ClientTickEvent(ClientTickEvent e) {
        if (setting.title.getValue() != "") {
            SkyMatrix.mc.getWindow().setTitle(setting.title.getValue());
        }

        if (MinecraftClient.getInstance().world != null) {
            updataGuiScreen();
        }
    }

    @Use
    public List<Screen> guiScreens;

    private Screen targetGui;

    public void openGui(Class gui) {
        for (Screen guiScreen : guiScreens) {
            if (guiScreen.getClass() == gui) {
                logger.debug("display guiscreen: " + guiScreen);
                targetGui = guiScreen;
                return;
            }
        }
    }

    private void updataGuiScreen() {

        if (targetGui != null) {
            MinecraftClient.getInstance().setScreen(targetGui);
            targetGui = null;
        }

    }

    public static void sendMessage(Text message) {
        assert SkyMatrix.mc.player != null;
        SkyMatrix.mc.player.sendMessage(Text.of("§8[§9S§9k§9y§9M§9a§9t§9r§9i§9x§8]").copy().append(message));
    }

    public static void sendDebugMessage(Text message) {
        if (!Setting.getInstance().debug.isValue()) return;
        sendMessage(Text.of("§3[§bDebug§3]§7: §r").copy().append(message));
    }


}
