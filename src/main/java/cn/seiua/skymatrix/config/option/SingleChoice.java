package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UISingleChoice;
import cn.seiua.skymatrix.utils.OptionInfo;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class SingleChoice<V> implements Serializable, UIComponent {

    private transient List<V> value;

    public List<V> getValue() {
        return value;
    }

    public V selectedValue() {
        return this.value.get(selected);
    }

    public void setValue(List<V> value) {
        this.value = value;
    }

    public static String MODE = "Mode";
    public static String BLOCK = "Block";
    public static String ITEM = "Item";
    public static String CREATURE = "Creature";

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {

        this.selected = selected;
    }

    @JSONField(alternateNames = "selected")
    private int selected;
    private transient String type;

    public SingleChoice(List<V> value, String type) {
        this.value = value;
        this.type = type;
    }

    public SingleChoice(List<V> value) {
        this.value = value;
        this.type = SingleChoice.BLOCK;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getID() {
        return "SingleChoice";
    }

    @Override
    public UI build(String module, String category, String name, Signs sign) {
        OptionInfo<SingleChoice> optionInfo = new OptionInfo<>(this, category + "." + name, name, module, category, sign);
        UISingleChoice uiToggle = new UISingleChoice(optionInfo);
        return uiToggle;
    }
}
