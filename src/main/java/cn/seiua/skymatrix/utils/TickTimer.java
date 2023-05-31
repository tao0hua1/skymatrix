package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.Run;

public class TickTimer {

    private int tick;

    private Run callBack;

    public TickTimer(int tick, Run callBack) {
        this.tick = tick;
        this.callBack = callBack;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void update() {
        if (tick == 0) {
            if (callBack != null)
                callBack.run();
        }
        tick--;

    }
}
