package cn.seiua.skymatrix;


import cn.seiua.skymatrix.client.Notice;
import cn.seiua.skymatrix.client.NoticeType;
import cn.seiua.skymatrix.client.Notification;
import cn.seiua.skymatrix.client.component.ComponentHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SkyMatrix implements ModInitializer, ClientTickEvents.StartTick {
    public static final String MODID = "skymatrix";
    public static final Logger LOGGER = LoggerFactory.getLogger("skymatrix");
    public static MinecraftClient mc;

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {


        dispatcher.register(
                ClientCommandManager.literal("Test")
                        .then(
                                ClientCommandManager.argument("notice", StringArgumentType.string())
                                        .executes(context -> {
                                            FabricClientCommandSource source = context.getSource();
                                            source.sendFeedback(Text.literal("notice"));
                                            Notification.getInstance().push(new Notice("Test", "a test message", NoticeType.ERROR));
                                            Notification.getInstance().push(new Notice("Test", "a test message", NoticeType.INFO));
                                            Notification.getInstance().push(new Notice("Test", "a test message", NoticeType.WARN));
                                            return 1;
                                        })
                        )
        );

    }

    @Override
    public void onInitialize() {

        ClientCommandRegistrationCallback.EVENT.register(SkyMatrix::registerCommands);
        ClientTickEvents.START_CLIENT_TICK.register(this);

    }

    private boolean flag;

    @Override
    public void onStartTick(MinecraftClient client) {
        if (flag == false) {
            GLFW.glfwSetWindowTitle(MinecraftClient.getInstance().getWindow().getHandle(), "Genshin impact");
            mc = MinecraftClient.getInstance();
            LOGGER.info("client loaded!");
            ComponentHandler.loadAllClasesName();
            ComponentHandler.setup();
            flag = true;
        }
    }
}
