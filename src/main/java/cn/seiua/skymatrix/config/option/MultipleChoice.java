package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.ConfigInit;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UIMultipleChoice;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class MultipleChoice implements ConfigInit, Serializable, UIComponent {
    public static String MODE = "Mode";
    public static String BLOCK = "Block";
    public static String ITEM = "Item";
    public static String CREATURE = "Creature";
    @JSONField(alternateNames = "value")
    public Map<Object, Boolean> value;

    private String type;

    public MultipleChoice(Map value) {

        this.value = new HashMap<>();
        this.value.putAll(value);
        type = "Creature";
    }

    public MultipleChoice(Map value, String type) {

        this.value = new HashMap<>();
        this.value.putAll(value);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public String getID() {
        return null;
    }

    @Override
    public UI build(String module, String category, String name, Signs sign) {
        OptionInfo<MultipleChoice> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UIMultipleChoice uiMultipleChoice = new UIMultipleChoice(optionInfo);
        return uiMultipleChoice;
    }


}
