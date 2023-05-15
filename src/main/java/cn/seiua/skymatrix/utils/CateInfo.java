package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.module.Signs;

import java.io.Serializable;

public class CateInfo implements Serializable {

    private String fullName;
    private String name;
    private transient Class aClass;
    private transient Object target;
    private transient Signs sign;

    public CateInfo(String fullName, String name, Class aClass, Object target, Signs sign) {
        this.fullName = fullName;
        this.name = name;
        this.aClass = aClass;
        this.target = target;
        if (sign == null) {
            sign = Signs.NORMAL;
        }
        this.sign = sign;
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

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Signs getSign() {
        return sign;
    }

    public void setSign(Signs sign) {
        this.sign = sign;
    }
}
