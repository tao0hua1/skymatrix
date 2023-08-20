package cn.seiua.skymatrix.message;

import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.config.Setting;
import net.minecraft.text.Text;

public class Message {

    private String name;

    Message(String name) {
        this.name = name;
    }

    public void sendDebugMessage(Text text) {

        Client.sendDebugMessage(Text.of("§3[§a" + name + "§3]§7: §r").copy().append(text));


    }

    public void sendWarningMessage(Text text) {
        if (Setting.getInstance().simple.isValue()) {
            Client.sendSimpleMessage(text);
        } else {
            sendMessage(Text.of("§9[§ewarning§9]§7: §r").copy().append(text));
        }

    }

    public void sendWarningMessage(String text) {
        sendWarningMessage(Text.of("§7" + text));

    }

    public void sendMessage(Text text) {

        Client.sendMessage(Text.of("§3[§a" + name + "§3]§7: §r").copy().append(text));


    }

}
