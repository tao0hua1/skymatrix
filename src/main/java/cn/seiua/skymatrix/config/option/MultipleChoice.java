package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.gui.ui.UI;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;


public class MultipleChoice<V> implements Serializable , UIComponent {

    private transient List<V> value;

    public List<V> getValue() {
        return value;
    }

    public void setValue(List<V> value) {
        this.value = value;
    }

    @JSONField(alternateNames = "selected")
    private List<Integer> selected;

    public MultipleChoice(List<V> value) {
        this.value = value;
    }

    @Override
    public String getID() {
        return "MultipleChoice";
    }

    @Override
    public UI build(String module, String category, String name, Signs sign) {
        return null;
    }


}
