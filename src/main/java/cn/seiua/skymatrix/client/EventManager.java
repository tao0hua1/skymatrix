package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.client.component.Use;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventManager {

    private static final Logger logger = LoggerFactory.getLogger("EventManager");
    @Use
    public List<Object> components;

    private Map<Class, Object> events;

    @Init
    public void handle() {
        events = new HashMap<>();
        for (Object o : components) {
            Class c = o.getClass();
            Annotation annotation = c.getAnnotation(Event.class);
            if (annotation != null) {
                events.put(c, o);
                if (((Event) annotation).register()) {
                    register(c);
                }
                logger.info("Event loaded: " + c.getName());
            }
        }
    }

    public void register(Class c) {

        cn.seiua.skymatrix.event.EventManager.register(events.get(c));
    }

    public void unregister(Class c) {

        cn.seiua.skymatrix.event.EventManager.unregister(events.get(c));
    }


}
