package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

public class RenderLabelEvent extends Event {
    private Entity entity;
    private Text text;
    private MatrixStack matrixStack;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

    public void setMatrixStack(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }

    public RenderLabelEvent(Entity entity, Text text, MatrixStack matrixStack) {
        this.entity = entity;
        this.text = text;
        this.matrixStack = matrixStack;
    }
}
