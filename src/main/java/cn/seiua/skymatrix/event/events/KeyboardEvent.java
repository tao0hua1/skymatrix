package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;

public class KeyboardEvent extends Event {

    private int keyCode;
    private int scanCode;
    private int action;

    public KeyboardEvent(int keyCode, int scanCode, int action) {
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.action = action;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getScanCode() {
        return scanCode;
    }

    public void setScanCode(int scanCode) {
        this.scanCode = scanCode;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
