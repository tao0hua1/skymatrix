package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.Run;

public class TickTimer {

    private int tick;

    private Run callBack;

    public TickTimer(int tick, Run callBack) {
        this.tick = tick;
        this.callBack = callBack;
    }

    public void update() {
        if (tick == 0) {
            callBack.run();
        }
        tick--;

    }
}
