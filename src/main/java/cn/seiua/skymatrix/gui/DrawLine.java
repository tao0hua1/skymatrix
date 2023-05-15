package cn.seiua.skymatrix.gui;

public class DrawLine {

    int width;
    int current;
    int start;

    public DrawLine(int i) {
        width = i;
    }

    public void append(int v) {
        current += v;
    }

    public int get(int v) {
        current += v;
        return current;


    }

    public void reset(int x) {
        current = 0;
        start = x;
        current += x;
    }


}
