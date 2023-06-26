package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.ConfigInit;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UIMultipleChoice;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


public class MultipleChoice implements ConfigInit, Serializable, UIComponent {

    @JSONField(alternateNames = "value")
    public Map<Object, Boolean> value;

    private String icon;

    public MultipleChoice(Map value) {

        this.value = new LinkedHashMap<>();
        this.value.putAll(value);

    }

    public MultipleChoice(Map value, String icon) {

        this.value = new LinkedHashMap<>();
        this.value.putAll(value);
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Map<Object, Boolean> getValue() {
        return value;
    }

    public void setValue(Map<Object, Boolean> value) {
        this.value = value;
    }

    @Override
    public void init() {


    }


    @Override
    public UI build(String module, String category, String name, Signs sign) {
        OptionInfo<MultipleChoice> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UIMultipleChoice uiMultipleChoice = new UIMultipleChoice(optionInfo);
        return uiMultipleChoice;
    }


}
