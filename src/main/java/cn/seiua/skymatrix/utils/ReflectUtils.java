package cn.seiua.skymatrix.utils;

import net.minecraft.client.util.math.MatrixStack;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReflectUtils {

    private static boolean withAnnotation1(Class c,Class target){
        for (Annotation an: c.getAnnotations()) {
            if(an.annotationType() == target){
                return true;
            }
            if(!(an instanceof Retention||an instanceof Target || an instanceof Documented)){
                return withAnnotation1(an.annotationType(),target);
            }

        }
        return false;
    }
    public static boolean withAnnotation(Class c,Class target){
        if(c==null||target==null){
            throw new NullPointerException("NMSL");
        }
        if(!c.isInterface()){
            return withAnnotation1(c,target);
        }
        return false;
    }
    public static List<Object> IsCoincidentWith(HashMap<Class,Object> from,Class target){
        if(from==null||target==null){
            throw new NullPointerException("NMSL");
        }
        List<Object> ret=new ArrayList<>();
        for (Class key: from.keySet()) {
            if (target == key||target.isAssignableFrom(key)) {
                ret.add(from.get(key));
            }
        }
        
        return ret.size()==0?null:ret;
        
        
    }
    public static void copyData(Object o,Object oo){
        try {
        if(o.getClass()==oo.getClass()){
            for (Field f: o.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                    Object obj=f.get(oo);
                    if(obj!=null) f.set(o,f.get(oo));

            }
        }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
