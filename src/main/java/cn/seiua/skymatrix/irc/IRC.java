package cn.seiua.skymatrix.irc;

import com.sun.jna.Library;

public interface IRC extends Library {


    void sendMessage(String msg);

    void sendPrivateMessage(String msg, String target);

    void sendCommand(String cmd, String[] args);

    void sendTitle(String msg);

    boolean isConnected();

    void reconnect();

    boolean addMessageHandler(String classname, String methodname);
}
