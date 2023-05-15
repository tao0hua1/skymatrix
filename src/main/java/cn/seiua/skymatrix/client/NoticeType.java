package cn.seiua.skymatrix.client;

import java.awt.*;

public enum NoticeType {

    WARN("WARN", new Color(222, 214, 0), "\uE90F"),
    INFO("INFO", new Color(106, 222, 3), "\uE910"),
    ERROR("ERROR", new Color(222, 96, 3), "\uE911");

    public Color color;
    public String name;
    public String icon;

    NoticeType(String name, Color color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
