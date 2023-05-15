package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.module.Signs;

import java.io.Serializable;

public class OptionInfo<T> implements Serializable {

    private transient T target;

    private String fullName;

    private String name;
    private String module;
    private String category;
    private transient Signs sign;
    public OptionInfo(T target, String fullName, String name, String module, String category, Signs sign) {
        this.target = target;
        this.fullName = fullName;
        this.name = name;
        this.module = module;
        this.category = category;
        if (sign == null) {
            sign = Signs.NORMAL;
        }
        this.sign = sign;
    }

    public Signs getSign() {
        return sign;
    }

    public void setSign(Signs sign) {
        this.sign = sign;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
