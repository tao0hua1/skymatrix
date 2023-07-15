package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;

public class ASneakEvent extends Event {
    boolean flag;

    public ASneakEvent(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
