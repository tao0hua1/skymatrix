package cn.seiua.skymatrix.client.module;

import com.alibaba.fastjson.annotation.JSONField;



public class ModuleObj {
    public ModuleObj(Object target, boolean enable, String name, String category) {
        this.target = target;
        this.enable = enable;
        this.name = name;
        this.category = category;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private transient Object target;
    @JSONField(alternateNames = "enable")
    private boolean enable;
    private transient String name;
    private transient String category;
}
