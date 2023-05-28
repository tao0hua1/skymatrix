package cn.seiua.skymatrix.client.module;

import cn.seiua.skymatrix.client.*;
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
    @Use
    public EventManager eventManager;

    @Use
    public Notification notification;
    @Use
    public ConfigManager configManager;

    private static final Logger logger = LoggerFactory.getLogger("ModuleManager");
    @Value(name = "state")
    public MapValueHolder<String, Boolean, Boolean> valueHolder = new MapValueHolder<String, Boolean, Boolean>(new HashMap<>());

    private Map<String, ModuleObj> modules;

    public static ModuleManager instance;

    @Init(level = 999999)
    public void handle() {
        modules = new HashMap<>();
        for (Object o : components) {
            Class c = o.getClass();
            Annotation annotation = c.getAnnotation(SModule.class);
            if (annotation != null) {
                SModule module = (SModule) annotation;
                modules.put(getModuleName(module), new ModuleObj(o, false, module.name(), module.category()));
                valueHolder.value.put(getModuleName(module), false);
                logger.info("Module loaded: " + c.getName() + " " + getModuleName(module));
            }
        }
        instance = this;
        configManager.addCallBack(this::registerEvents);
    }

    public void registerEvents() {
        for (Object key : valueHolder.value.keySet()) {
            if (valueHolder.value.get(key)) {
                eventManager.register(modules.get(key).getTarget().getClass());
            }
        }
    }


    public boolean isEnable(String moduleName) {
        if (!valueHolder.value.containsKey(moduleName)) {
            logger.warn("不存在的Modulename: " + moduleName);
            valueHolder.value.put(moduleName, false);
            return false;
        }
        return valueHolder.value.get(moduleName);
    }

    public void toggle(String moduleName) {
        if (valueHolder.value.containsKey(moduleName)) {

            if (!valueHolder.value.get(moduleName).booleanValue()) {
                eventManager.register(this.modules.get(moduleName).getTarget().getClass());
                valueHolder.value.put(moduleName, true);
            } else {
                eventManager.unregister(this.modules.get(moduleName).getTarget().getClass());
                valueHolder.value.put(moduleName, false);

            }

        } else {
            valueHolder.value.put(moduleName, false);
            logger.warn("不存在的Modulename: " + moduleName);
        }
        Object o = this.modules.get(moduleName).getTarget();
        boolean flag = false;
        IToggle toggle = null;
        if (o instanceof IToggle) {
            toggle = (IToggle) o;
            flag = true;
        }

        if (valueHolder.value.get(moduleName)) {
            notification.push(new Notice("Module", "Enable " + this.modules.get(moduleName).getName(), NoticeType.INFO));
            if (flag) toggle.enable();
        } else {
            notification.push(new Notice("Module", "Disable " + this.modules.get(moduleName).getName(), NoticeType.INFO));
            if (flag) toggle.disable();
        }

    }

    public String getModuleName(SModule module) {
        return module.category() + "." + module.name();
    }


}
