package cn.seiua.skymatrix.config.option;

import cn.seiua.skymatrix.config.ConfigInit;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

public class MapValueHolder<K, V, C> implements ConfigInit {
    @JSONField(alternateNames = "value")
    public Map<Object, V> value;

    private C e;

    public MapValueHolder(Map value) {
        this.value = value;
    }

    public MapValueHolder(Map value, C e) {
        this.value = value;
        this.e = e;
    }

    public Map<Object, V> getValue() {
        return value;
    }

    public void setValue(Map<Object, V> value) {
        this.value = value;
    }

    @Override
    public void init() {
        if (e == null) return;
        Map<Object, V> map = new HashMap<>();
        for (Object key : value.keySet()) {
            Object v = value.get(key);
            map.put(key, (V) ((JSONObject) v).toJavaObject(e.getClass()));


        }


        value = map;


    }
}
