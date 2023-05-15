package cn.seiua.skymatrix.event.events;

import cn.seiua.skymatrix.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class HudRenderEvent extends Event {

    private MatrixStack matrixStack;

    private float tickDelta;

    public HudRenderEvent(MatrixStack matrixStack, float tickDelta) {
        this.matrixStack = matrixStack;
        this.tickDelta = tickDelta;
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

    public void setMatrixStack(MatrixStack matrixStack) {
        this.matrixStack = matrixStack;
    }

    public float getTickDelta() {
        return tickDelta;
    }

    public void setTickDelta(float tickDelta) {
        this.tickDelta = tickDelta;
    }
}
