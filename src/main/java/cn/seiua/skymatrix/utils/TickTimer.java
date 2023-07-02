package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.Run;

public interface TickTimer {
    void update();

    void reset();

    static OneTickTimer build(int tick, Run callback) {
        return new OneTickTimer(tick, callback);
    }

    /**
     * @param tick
     * @param callback
     * @param times    -1 表示无线
     * @return
     */
    static CycleTickTimer build(int tick, Run callback, int times) {
        return new CycleTickTimer(tick, callback, times);
    }

}
