package cn.seiua.skymatrix.utils;


import java.lang.reflect.Method;


public class MethodA {
    private Method method;
    private int level;
    private Object object;

    public MethodA(Method method, int level, Object object) {
        this.method = method;
        this.level = level;
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
