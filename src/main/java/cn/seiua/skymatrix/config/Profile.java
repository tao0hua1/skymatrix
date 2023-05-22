package cn.seiua.skymatrix.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class Profile {

    @JSONField(alternateNames = "name")
    private String name;

    @JSONField(alternateNames = "selected")
    private boolean selected;
    @JSONField(alternateNames = "uuid")
    private String uuid;
    @JSONField(alternateNames = "version")
    private String version;
    @JSONField(alternateNames = "config")
    private JSONObject config;

    public Profile(String name, String uuid, String version) {
        this.name = name;
        this.uuid = uuid;
        this.version = version;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        this.config = config;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
