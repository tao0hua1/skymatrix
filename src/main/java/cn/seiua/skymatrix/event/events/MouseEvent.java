package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;

public class MouseEvent extends Event {

    long window;
    int button;
    int action;
    int mods;

    public MouseEvent(long window, int button, int action, int mods) {
        this.window = window;
        this.button = button;
        this.action = action;
        this.mods = mods;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public int getButton() {
        return button;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getMods() {
        return mods;
    }

    public void setMods(int mods) {
        this.mods = mods;
    }
}
