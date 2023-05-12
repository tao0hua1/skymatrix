package cn.seiua.skymatrix.config.option;

import com.alibaba.fastjson.annotation.JSONField;

public class ValueHolder <T>{
    @JSONField(alternateNames = "value")
    public T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ValueHolder(T value) {
        this.value = value;
    }

}
