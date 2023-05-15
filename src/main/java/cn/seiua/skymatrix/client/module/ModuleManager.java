package cn.seiua.skymatrix.client.module;

import cn.seiua.skymatrix.client.component.*;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.MapValueHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Event
@Component
@Config(name = "ModuleManager")
public class ModuleManager {
    @Use
    public List<Object> components;

    private static final Logger logger = LoggerFactory.getLogger("ModuleManager");

    private Map<String,Object> modules;

    @Value(name = "state")
    public MapValueHolder<String,Boolean> valueHolder= new MapValueHolder<String, Boolean>(new HashMap<>());

    public static ModuleManager instance;

    @Init
    public void handle() {
        modules=new HashMap<>();
        for (Object o: components) {
            Class c = o.getClass();
            Annotation annotation = c.getAnnotation(SModule.class);
            if(annotation!=null){
                SModule module = (SModule) annotation;
                modules.put(getModuleName(module),new ModuleObj(o,false,module.name(),module.category()));
                valueHolder.value.put(getModuleName(module),false);
                logger.info("Module loaded: "+c.getName()+" "+getModuleName(module));
            }
        }
        instance=this;
    }

    public boolean isEnable(String moduleName){
        if(!valueHolder.value.containsKey(moduleName)){
            logger.warn("不存在的Modulename: "+moduleName);
            return false;
        }
        return valueHolder.value.get(moduleName);
    }
    public void toggle(String moduleName){
        if(valueHolder.value.containsKey(moduleName)){
            valueHolder.value.put(moduleName,!valueHolder.value.get(moduleName));
        }else {
            logger.warn("不存在的Modulename: "+moduleName);
        }
    }

    public String getModuleName(SModule module) {
        return module.category() + "." + module.name();
    }



}
