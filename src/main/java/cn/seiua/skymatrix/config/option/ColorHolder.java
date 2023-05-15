package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UIColorSlider;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;

import java.awt.*;
import java.io.Serializable;

public class ColorHolder implements Serializable, UIComponent {
    @JSONField(alternateNames = "red")
    public Integer r;
    @JSONField(alternateNames = "green")
    public Integer g;
    @JSONField(alternateNames = "blue")
    public Integer b;
    @JSONField(alternateNames = "alpha")
    public Integer a;


    @JSONField(alternateNames = "value")
    public transient Color value;


    public ColorHolder(Color value) {
        seColor(value);
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Color geColor() {
        return new Color(r, g, b, a);
    }

    public void seColor(Color value) {
        if (value == null) return;
        r = value.getRed();
        g = value.getGreen();
        b = value.getBlue();
        a = value.getAlpha();
    }

    @Override
    public String getID() {


        return null;
    }

    @Override
    public UI build(String module, String category, String name, Signs sign) {
        OptionInfo<ColorHolder> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UIColorSlider uiColorSlider = new UIColorSlider(optionInfo);
        return uiColorSlider;
    }
}
