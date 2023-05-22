package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.IHide;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UIToggle;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;


public class ToggleSwitch implements Serializable, UIComponent, IHide {

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

    @Override
    public UI build(String module, String category, String name, Signs sign) {

        OptionInfo<ToggleSwitch> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UIToggle uiToggle = new UIToggle(optionInfo);
        return uiToggle;
    }


    @Override
    public boolean canRender(String v) {
        return isValue();
    }
}
