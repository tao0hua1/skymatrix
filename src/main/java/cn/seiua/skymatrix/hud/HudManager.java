package cn.seiua.skymatrix.hud;

import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.client.component.Use;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.HudRenderEvent;
import cn.seiua.skymatrix.gui.ui.UI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
@Event(register = true)
public class HudManager extends Screen {

    private static HudManager instance;
    @Use
    private List<Object> objects;
    private List<ClientHud> huds;

    public HudManager() {
        super(Text.of("HudManager"));
    }

    @Init
    public void init() {
        instance = this;
        huds = new ArrayList<>();
        for (Object o : objects) {
            for (Field f : o.getClass().getDeclaredFields()) {
                if (f.getType() == ClientHud.class) {
                    try {
                        huds.add((ClientHud) f.get(o));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }


    @EventTarget
    public void onRender(HudRenderEvent event) {
        for (ClientHud hud : huds) {
            if (!hud.enable) continue;
            int ms = UI.getS();

            MatrixStack matrixStack = event.getContext().getMatrices();
            matrixStack.push();
            matrixStack.scale(1.0f / ms, 1.0f / ms, 1.0f / ms);
            hud.getTarget().draw(matrixStack, hud.x, hud.y);

            matrixStack.scale(ms, ms, ms);
            matrixStack.pop();
        }
    }


}
