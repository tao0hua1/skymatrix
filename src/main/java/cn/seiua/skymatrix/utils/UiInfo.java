package cn.seiua.skymatrix.utils;

public class UiInfo {

    private boolean value;

    private int x;

    private int y;

    public UiInfo(boolean value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
