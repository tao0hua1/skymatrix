package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.entity.LivingEntity;

public class LivingEntityRenderEvent extends Event {
    private String type;
    private LivingEntity entity;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntityRenderEvent(String type, LivingEntity entity) {
        this.type = type;
        this.entity = entity;
    }
}
