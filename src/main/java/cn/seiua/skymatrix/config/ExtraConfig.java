package cn.seiua.skymatrix.config;


import com.alibaba.fastjson.JSONObject;

public interface ExtraConfig {

    String name();
    void write(JSONObject jo);
    Object read();


}
