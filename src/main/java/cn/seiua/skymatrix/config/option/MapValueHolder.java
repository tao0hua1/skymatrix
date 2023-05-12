package cn.seiua.skymatrix.config.option;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class MapValueHolder<K,V>{
    @JSONField(alternateNames = "value")
    public Map<K,V> value;

    public MapValueHolder(Map value) {
        this.value = value;
    }

}
