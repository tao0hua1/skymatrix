package cn.seiua.skymatrix.utils;

import cn.seiua.skymatrix.client.module.ModuleManager;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.option.KeyBind;

import java.io.Serializable;

public class ModuleInfo implements Serializable {

    private String fullName;
    private String name;
    private String category;
    private boolean open;
    private transient Class aClass;
    private transient Object target;
    private transient Signs sign;
    private Boolean enable;


    private KeyBind keyBind;

    public KeyBind getKeyBind() {
        return keyBind;
    }

    public void setKeyBind(KeyBind keyBind) {
        this.keyBind = keyBind;
    }

    public ModuleInfo(String fullName, String name, Class aClass, Object target, Signs sign, String category) {
        this.fullName = fullName;
        this.name = name;
        this.aClass = aClass;
        this.target = target;
        this.category = category;
        if (sign == null) {
            sign = Signs.NORMAL;
        }
        this.sign = sign;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        ModuleManager.instance.toggle(fullName);
    }

    public Boolean isEnable() {
        return ModuleManager.instance.isEnable(fullName);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
