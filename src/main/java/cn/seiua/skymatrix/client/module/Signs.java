package cn.seiua.skymatrix.client.module;

import java.awt.*;

public enum Signs {

    NORMAL(new Color(0, 0, 0),"normal"),
    PRO(new Color(99, 45, 255),"pro"),
    BETA(new Color(49, 238, 255),"beta"),
    FREE(new Color(66, 255, 90),"free");

    public Color color;
    public String name;

    Signs(Color color, String name) {

        this.color=color;
        this.name=name;
    }
    }
