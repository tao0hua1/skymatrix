package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.Run;

public class CycleTickTimer implements TickTimer {

    private int targetTick;

    private int tick;
    private int times;
    private int currentTimes;

    private Run callBack;

    public CycleTickTimer(int tick, Run callBack, int times) {
        this.tick = tick;
        this.targetTick = tick;
        this.times = times;
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
            if (callBack != null) {
                callBack.run();
                tick = targetTick;
                currentTimes++;
                if (times != -1 && currentTimes > times) {
                    tick = 1;
                }
            }
        }
        tick--;
    }

    @Override
    public void reset() {
        tick = targetTick;
    }
}
