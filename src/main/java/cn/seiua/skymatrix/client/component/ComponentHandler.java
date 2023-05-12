package cn.seiua.skymatrix.client.component;

import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.utils.MethodA;
import cn.seiua.skymatrix.utils.ReflectUtils;
import com.google.common.reflect.ClassPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;


import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ComponentHandler {

    private static final Logger logger = LogManager.getLogger();
    private static HashMap<Class, Object> classes=new HashMap<>();
    private static HashSet<Object> objects=new HashSet<>();

    private static HashMap<Object, MethodA> init=new HashMap<>();
    private static HashMap<Field,Object> use=new HashMap<>();

    private static List<String> clazzs=new ArrayList<>();

    public static void loadAllClasesName(){
        String classpath = System.getProperty("java.class.path");
        String[] classpathEntries = classpath.split(File.pathSeparator);
        for (String classpathEntry : classpathEntries) {
            File file = new File(classpathEntry);
            if (file.isDirectory()) {
                String[] files = file.list();
                traverseDirectory(file,file);
            }else {
                traverseJar(file);
            }
        }
    }
    private static void traverseDirectory(File dir,File root) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    traverseDirectory(file,root);
                }
            }
        } else {
            if(dir.toString().endsWith(".class")){
                addClass(dir.toString().replace(root.toString()+"\\","").replace("\\",".").replace(".class",""));
            }
        }
    }
    public static final String MYPACKAGE="cn.seiua.skymatrix";
    public static final String MYMIXINPACKAGE="cn.seiua.skymatrix.mixin";
    private static void addClass(String c){
        if(c.startsWith(MYPACKAGE)){
        if(!c.startsWith(MYMIXINPACKAGE)){
            clazzs.add(c);
        }}
    }
    private static void traverseJar(File jarFile)  {
        try {
            URL[] urls = {jarFile.toURI().toURL()};
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        JarFile jar = null;
        try {
            jar = new JarFile(jarFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                String className = entry.getName().replace("/", ".").substring(0, entry.getName().length() - 6);
//                Class<?> clazz = classLoader.loadClass(className);
                addClass(className);
            }
        }

        try {
            jar.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void setup(){
        try {

            for (String s: ComponentHandler.clazzs) {
                Class c= ComponentHandler.class.getClassLoader().loadClass(s);
                if(ReflectUtils.withAnnotation(c, Component.class)){
                    Object o=c.newInstance();
                    classes.put(c,o);
                    logger.info("Component Loaded "+o.getClass().toString());
                    //加载自动写入
                    for (Field f: c.getDeclaredFields()) {
                        Annotation annotation=f.getAnnotation(Use.class);
                        if(annotation!=null){
                            use.put(f,o);
                            logger.info("Wrote Loaded "+o.getClass().toString()+"."+f.getName()+"."+f.getName());
                        }
                    }
                    //加载初始化方法
                    for (Method m: c.getDeclaredMethods()) {
                        Annotation annotation=m.getAnnotation(Init.class);
                        if(annotation!=null){
                            init.put(o,new MethodA(m,((Init)annotation).level(),o));
                            logger.info("Initialization Loaded "+o.getClass().toString()+"."+m.getName());
                        }
                    }
                }
            }
            for (Field o: use.keySet()) {
                Object target=use.get(o);
                Class type=o.getType();
                o.setAccessible(true);
                if(o.getType() == List.class||List.class.isAssignableFrom(o.getType())){
                    Type typee = o.getGenericType();
                    if (typee instanceof ParameterizedType) {
                        ParameterizedType pType = (ParameterizedType) typee;
                        Type[] arr = pType.getActualTypeArguments();
                        List rt=ReflectUtils.IsCoincidentWith(classes,(Class)arr[0]);
                        o.set(target,rt);
                    }
                }else {
                    o.set(target,classes.get(o.getType()));
                }
                logger.info("Wrote "+o.getClass().toString()+"."+o.getName());
            }
            ArrayList<MethodA> arrayList=new ArrayList<>(init.values());
            arrayList.sort(Comparator.comparingInt(MethodA::getLevel));
            for (MethodA o:arrayList ) {
                MethodA target=o;
                target.getMethod().setAccessible(true);
                target.getMethod().invoke(target.getObject());
                logger.info("Inited "+target.getObject().getClass().toString()+"."+target.getMethod().getName());
            }

        } catch (IllegalArgumentException  e) {
            throw new RuntimeException("初始化函数不能有参数");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }


    private static void init(){

    }


}
