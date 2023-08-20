package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.Run;

public class OneTickTimer implements TickTimer {

    private int tick;
    private int targetTick;

    private Run callBack;

    OneTickTimer(int tick, Run callBack) {
        this.tick = tick;
        this.targetTick = tick;
        this.callBack = callBack;
    }

    @Override
    public void reset() {
        tick = targetTick;
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
