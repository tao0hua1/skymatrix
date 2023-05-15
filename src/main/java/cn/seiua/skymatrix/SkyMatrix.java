package cn.seiua.skymatrix;


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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SkyMatrix implements ModInitializer, ClientTickEvents.StartTick {
    public static final String MODID = "skymatrix";
    public static final Logger LOGGER = LoggerFactory.getLogger("skymatrix");

    public static void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {


        dispatcher.register(
                // 使用 literal 方法创建一个字面量参数节点，表示命令的名称
                ClientCommandManager.literal("hello")
                        // 使用 then 方法添加一个子节点，表示命令的参数
                        .then(
                                // 使用 argument 方法创建一个参数节点，表示要打招呼的对象
                                ClientCommandManager.argument("target", StringArgumentType.string())
                                        // 使用 executes 方法添加一个执行器，表示命令的逻辑
                                        .executes(context -> {
                                            // 从上下文中获取参数的值
                                            String target = StringArgumentType.getString(context, "target");
                                            // 从上下文中获取命令源，即执行命令的玩家
                                            FabricClientCommandSource source = context.getSource();
                                            // 向玩家发送一条消息，打招呼
                                            source.sendFeedback(Text.literal("Hello, " + target + "!"));
                                            // 返回命令的结果，0 表示成功
                                            return 1;
                                        })
                        )
        );

    }

    @Override
    public void onInitialize() {
        ClientCommandRegistrationCallback.EVENT.register(SkyMatrix::registerCommands);
        ClientTickEvents.START_CLIENT_TICK.register(this);
        // 注册一个名为 /hello 的命令

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
