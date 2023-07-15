package cn.seiua.skymatrix.event.events;


import cn.seiua.skymatrix.event.Event;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

public class CommandRegisterEvent extends Event {


    private CommandDispatcher<FabricClientCommandSource> dispatcher;
    private CommandRegistryAccess registryAccess;

    public CommandRegisterEvent(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        this.dispatcher = dispatcher;
        this.registryAccess = registryAccess;
    }

    public CommandDispatcher<FabricClientCommandSource> getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        this.dispatcher = dispatcher;
    }

    public CommandRegistryAccess getRegistryAccess() {
        return registryAccess;
    }

    public void setRegistryAccess(CommandRegistryAccess registryAccess) {
        this.registryAccess = registryAccess;
    }
}
