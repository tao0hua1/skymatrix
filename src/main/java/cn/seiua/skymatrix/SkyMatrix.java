package cn.seiua.skymatrix;


import cn.seiua.skymatrix.ai.Node;
import cn.seiua.skymatrix.ai.PathFinder;
import cn.seiua.skymatrix.client.component.ComponentHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
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
                                ClientCommandManager.argument("notice", Vec3ArgumentType.vec3())
                                        .executes(SkyMatrix::execute)
                        )
        );

    }

    public static int execute(CommandContext context) {
        FabricClientCommandSource source = (FabricClientCommandSource) context.getSource();


        Vec3i vec3i = new Vec3i(0, 4, 0);
        new Thread(() -> {
            BlockPos blockPos = SkyMatrix.mc.player.getBlockPos();
            Node start = new Node(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            Node end = new Node((int) vec3i.getX(), (int) vec3i.getY(), (int) vec3i.getZ());
            PathFinder.instance.findPath(start, end);
        }).start();

        return 1;
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
