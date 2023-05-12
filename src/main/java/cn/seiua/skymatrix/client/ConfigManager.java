package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.component.*;
import cn.seiua.skymatrix.client.component.Module;
import cn.seiua.skymatrix.client.module.ModuleManager;
import cn.seiua.skymatrix.config.ExtraConfig;
import cn.seiua.skymatrix.config.LocalConfigStore;
import cn.seiua.skymatrix.config.Store;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.utils.ReflectUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.jna.Native;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConfigManager {

    @Use
    public List<Object> components;

    @Use
    public List<ExtraConfig> extraConfigs;
    @Use
    public LocalConfigStore localConfigStore;

    @Use
    public ModuleManager moduleManager;
    public Store store1= (Store) Native.loadLibrary(Store.class);
    private Store store;
    private String uuid;

    private static final Logger logger = LoggerFactory.getLogger("ConfigManager");
    public Map<String,Object> configs;

    @Init(level = 9999999)
    public void handle() {
        configs=new HashMap<>();
        switchStore(localConfigStore);
        for (Object o: components) {
            Class target=o.getClass();
            String config=getConfigName(target);
            if(config==null)continue;
            for (Field field: target.getDeclaredFields()) {
                String value=getValueName(field);
                if(value==null)continue;
                String cname=config+"."+value;
                try {
                    configs.put(cname,field.get(o));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        bindConfigFromFile();
        writeConfig();
    }
    public String getConfigName(Class c){
        Category category= (Category) c.getAnnotation(Category.class);
        Module module= (Module) c.getAnnotation(Module.class);
        Config config= (Config) c.getAnnotation(Config.class);
        String name=null;
        if(category!=null){
            name=CATEGORY+"."+category.name();
        }
        if(module!=null){
            name=MODULE+"."+module.category()+"."+module.name();
        }
        if(config!=null){
            name=NORMAIL+"."+config.name();
        }
        return name;
    }

    public static final String CATEGORY="category";
    public static final String MODULE="module";
    public static final String NORMAIL="normal";


    public String getValueName(Field field){
        Value value=field.getAnnotation(Value.class);
        String name=null;
        if(value!=null){
            name=value.name();
        }
        return name;
    }
    public static final String EXTRA="extra";

    private void bindConfigFromFile(){
        JSONObject jo=null;
        try {
            jo=JSON.parseObject(new String(store.load(uuid)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String key: jo.keySet()) {
            if(key==EXTRA)continue;
            Object current=configs.get(key);
            if(current==null){

                logger.warn("Removed setting: key: "+key);
                continue;
            }
            logger.info("Loaded setting: "+key);
            Object o=jo.getJSONObject(key).toJavaObject(current.getClass());
            ReflectUtils.copyData(current,o);
        }
    }
    private void writeConfig(){
//        for (ExtraConfig extraConfig: extraConfigs) {
//           Object o =extraConfig.read();
//           HashMap hashMap=new HashMap();
//            hashMap.put(EXTRA+"."+extraConfig.name(),o);
//            configs.put(EXTRA,hashMap);
//
//        }
        store.write(JSON.toJSONString(configs, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat).getBytes(),uuid);
    }

    public void switchStore(Store store){
        uuid=null;
        this.store=store;

    }

}
