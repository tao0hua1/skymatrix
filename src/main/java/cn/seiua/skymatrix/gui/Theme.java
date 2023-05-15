package cn.seiua.skymatrix.gui;

import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Config;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.ColorHolder;

import java.awt.*;

@Component
@Config(name = "theme")
public class Theme {
    public static int start = 40;
    private static Theme theme;
    @Value(name = "THEME")
    public ColorHolder THEME = new ColorHolder(new Color(207, 222, 238));
    @Value(name = "THEME_UI")
    public ColorHolder THEME_UI_SELECTED = new ColorHolder(new Color(140, 247, 255));
    @Value(name = "UNSELECTED")
    public ColorHolder UNSELECTED = new ColorHolder(new Color(173, 185, 197));
    @Value(name = "SUBBOARD")
    public ColorHolder SUBBOARD = new ColorHolder(new Color(29, 41, 54));
    @Value(name = "LINE")
    public ColorHolder LINE = new ColorHolder(new Color(42, 58, 76));
    @Value(name = "SUBLINE")
    public ColorHolder SUBLINE = new ColorHolder(new Color(36, 48, 61));
    @Value(name = "HOVERANDSELECTED")
    public ColorHolder HOVERANDSELECTED = new ColorHolder(new Color(42, 58, 77));
    @Value(name = "BOARD")
    public ColorHolder BOARD = new ColorHolder(new Color(34, 47, 62));

    public static Theme getInstance() {

        return theme;
    }

    @Init
    public void init() {
        theme = this;
    }

}
