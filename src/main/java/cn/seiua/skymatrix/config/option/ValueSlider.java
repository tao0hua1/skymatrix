package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.config.UIComponent;
import com.alibaba.fastjson.annotation.JSONField;


import java.io.Serializable;



public class ValueSlider <V extends Number> implements Serializable , UIComponent {
    @JSONField(alternateNames = "value")
    private V value;
    private transient V min;
    private transient V max;
    private transient V interval;

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public ValueSlider(V value, V min, V max, V interval) {
        this.value = value;
        this.min = min;
        this.max = max;
        this.interval = interval;
    }

    @Override
    public String getID() {
        return "ValueSlider";
    }
}
