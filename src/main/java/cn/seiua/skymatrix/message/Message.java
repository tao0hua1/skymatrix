package cn.seiua.skymatrix.message;

import cn.seiua.skymatrix.client.Client;
import net.minecraft.text.Text;

public class Message {

    private String name;

    Message(String name) {
        this.name = name;
    }

    public void sendDebugMessage(Text text) {

        Client.sendDebugMessage(Text.of("§3[§a" + name + "§3]§7: §r").copy().append(text));
    }

    public void sendMessage(Text text) {
        Client.sendMessage(Text.of("§3[§a" + name + "§3]§7: §r").copy().append(text));
    }

}
