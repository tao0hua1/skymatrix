package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.config.UIComponent;
import com.alibaba.fastjson.annotation.JSONField;


import java.io.Serializable;


public class ToggleSwitch implements Serializable , UIComponent {

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @JSONField(alternateNames = "value")
    private boolean value;

    public ToggleSwitch(boolean value) {
        this.value = value;
    }

    @Override
    public String getID() {
        return "ToggleSwitch";
    }
}
