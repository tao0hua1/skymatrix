package cn.seiua.skymatrix.client.entity;

import java.io.Serializable;

public class RecordEntity implements Serializable {

    private int watchdog_lastMinute;
    private int staff_rollingDaily;
    private int watchdog_total;
    private int watchdog_rollingDaily;
    private int staff_total;

    public int getWatchdog_lastMinute() {
        return watchdog_lastMinute;
    }

    public void setWatchdog_lastMinute(int watchdog_lastMinute) {
        this.watchdog_lastMinute = watchdog_lastMinute;
    }

    public int getStaff_rollingDaily() {
        return staff_rollingDaily;
    }

    public void setStaff_rollingDaily(int staff_rollingDaily) {
        this.staff_rollingDaily = staff_rollingDaily;
    }

    public int getWatchdog_total() {
        return watchdog_total;
    }

    public void setWatchdog_total(int watchdog_total) {
        this.watchdog_total = watchdog_total;
    }

    public int getWatchdog_rollingDaily() {
        return watchdog_rollingDaily;
    }

    public void setWatchdog_rollingDaily(int watchdog_rollingDaily) {
        this.watchdog_rollingDaily = watchdog_rollingDaily;
    }

    public int getStaff_total() {
        return staff_total;
    }

    public void setStaff_total(int staff_total) {
        this.staff_total = staff_total;
    }
}
