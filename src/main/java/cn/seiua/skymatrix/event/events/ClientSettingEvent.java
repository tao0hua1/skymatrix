package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;

public class ClientSettingEvent extends Event {
    String type;

    public ClientSettingEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
