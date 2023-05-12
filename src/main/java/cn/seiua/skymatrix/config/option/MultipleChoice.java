package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.config.UIComponent;
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
}
