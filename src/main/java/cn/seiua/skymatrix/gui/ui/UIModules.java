package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.gui.UIComponent;
import cn.seiua.skymatrix.utils.CateInfo;
import cn.seiua.skymatrix.utils.ModuleInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UIModules extends UI {

    private CateInfo cateInfo;
    private List<ModuleInfo> modules;
    private List<UIModule> uiModules;
    private DrawLine drawLine = new DrawLine(250);

    public UIModules(CateInfo cateInfo) {
        modules = new ArrayList<>();
        this.cateInfo = cateInfo;
        uiModules = new ArrayList<>();
    }

    public void addModuleInfo(ModuleInfo moduleInfo) {
        modules.add(moduleInfo);
        UIModule uiModule = new UIModule(moduleInfo);
        uiModules.add(uiModule);

        String category = moduleInfo.getCategory();
        String name = moduleInfo.getName();
        Object o = moduleInfo.getTarget();
        Class c = moduleInfo.getaClass();
        List<UI> temp = new ArrayList<>();
        for (Field field : c.getDeclaredFields()) {
            Value value = (Value) field.getAnnotation(Value.class);
            Sign sign = (Sign) field.getAnnotation(Sign.class);
            if (value != null) {
                try {
                    Object uobj = field.get(o);
                    if (uobj instanceof UIComponent) {
                        UIComponent uiComponent = (UIComponent) uobj;
                        UI ui = uiComponent.build(name, category, value.name(), sign == null ? null : sign.sign());
                        if (ui != null) {
                            temp.add(ui);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        uiModule.setUis(temp);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        drawLine.reset(getX() - 125);
        setWidth(250);
        setHeight(58);
        setMid(true);
        RenderUtils.cent();
        RenderUtils.setColor(Theme.getInstance().BOARD.geColor());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 250, getY() + 58, 0), matrixStack, 10);
        RenderUtils.resetCent();
        ClickGui.fontRenderer24.centeredH();
        ClickGui.fontRenderer24.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer24.setDrawSize(32);
        ClickGui.fontRenderer24.drawString(matrixStack, drawLine.get(25), getY(), upperFirst(cateInfo.getName()));
        ClickGui.fontRenderer24.resetCenteredH();
        ClickGui.fontRenderer24.resetCenteredV();
        int sty = getY() + 58;
        for (UIModule uiModule : uiModules) {
            uiModule.update(getX(), sty);
            uiModule.render(matrixStack, mouseX, mouseY, delta);
            sty += uiModule.getHeight();
        }

    }


    @Override
    void initUI() {

    }


    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        for (UIModule uiModule : uiModules) {
            uiModule.mouseMoved(mouseX, mouseY);
        }
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        for (UIModule uiModule : uiModules) {
            uiModule.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        for (UIModule uiModule : uiModules) {
            uiModule.mouseReleased(mouseX, mouseY, button);
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {

        for (UIModule uiModule : uiModules) {
            uiModule.keyReleased(keyCode, scanCode, modifiers);
        }


        return true;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        for (UIModule uiModule : uiModules) {
            uiModule.keyPressed(keyCode, scanCode, modifiers);
        }
    }
}
