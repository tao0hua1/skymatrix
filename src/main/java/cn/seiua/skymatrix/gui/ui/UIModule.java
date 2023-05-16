package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.ModuleInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import cn.seiua.skymatrix.utils.UiInfo;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.util.List;


public class UIModule extends UI {
    private ModuleInfo moduleInfo;
    private List<UI> uis;
    private DrawLine drawLine = new DrawLine(250);
    private float o = 0;
    private int uiy;
    private boolean SHIFT;

    public UIModule(ModuleInfo moduleInfo) {
        this.moduleInfo = moduleInfo;

        setInBoxLeft(this::toggle);
        setInBoxRight(this::toggle1);
    }


    public List<UI> getUis() {
        return uis;
    }

    public void setUis(List<UI> uis) {
        this.uis = uis;
    }

    private Color getBoardColoar() {
        if (isInBox()) {

            o += 0.05;
//            if (tempdis) {
//                o = 0;
//            }
        } else {
            o -= 0.05;
//            tempdis = false;
        }
        if (o > 1) o = 1;
        if (o < 0) o = 0;
        Color color = Theme.getInstance().BOARD.geColor();
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        Color ac = Color.getHSBColor(hsb[0], hsb[1], moduleInfo.isEnable() ? 0.35f : Math.max(hsb[2], 0.35f * o));
        if (moduleInfo.isEnable()) {
            ac = Theme.getInstance().HOVERANDSELECTED.geColor();
        }
        return ac;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        drawLine.reset(getX() - 125);
        setWidth(250);
        setHeight(58);
        setMid(true);
        RenderUtils.cent();
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 250, getY() + 58, 0), matrixStack, 0);
        RenderUtils.setColor(moduleInfo.getSign().color);
        RenderUtils.drawRound2D(new Box(getX() + 124, getY(), 0, getX() + 127, getY() + 58, 0), matrixStack, 0);
        if (isInBox()) {
            if (Screen.hasShiftDown()) {
                RenderUtils.setColor(Theme.getInstance().SUBBOARD.geColor());
                RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 30, getY() + 30, 0), matrixStack, 2);

            }
        }
        ClickGui.fontRenderer22.centeredH();
        ClickGui.fontRenderer22.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer22.drawString(matrixStack, drawLine.get(25), getY(), upperFirst(moduleInfo.getName()));
        ClickGui.fontRenderer22.resetCenteredH();
        ClickGui.fontRenderer22.resetCenteredV();
        ClickGui.iconfontRenderer24.centeredH();
        ClickGui.iconfontRenderer24.centeredV();
        ClickGui.iconfontRenderer24.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer24.setDrawSize(30);
        ClickGui.iconfontRenderer24.drawString(matrixStack, getX() + 96, getY(), "\uE90C");
        ClickGui.iconfontRenderer24.resetCenteredH();
        ClickGui.iconfontRenderer24.resetCenteredV();

        if (this.isOpen()) {
            int sty = getY() + 58 / 2;
            for (UI ui : uis) {
                ui.update(getX(), sty);
                ui.render(matrixStack, mouseX, mouseY, delta);
                sty += ui.getHeight();
            }
            uiy = sty + 58 / 2;
        } else {
            uiy = 0;
        }
    }

    public void toggle() {
        moduleInfo.setEnable(!moduleInfo.isEnable());
    }

    public void toggle1() {
        this.setOpen(!this.isOpen());
    }

    public boolean isOpen() {
        UiInfo uiInfo = ClickGui.getValue(moduleInfo.getFullName() + ".state");
        return uiInfo.isValue();
    }

    private void setOpen(boolean b) {
        ClickGui.getValue(moduleInfo.getFullName() + ".state").setValue(b);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (this.isOpen()) {
            for (UI ui : uis) {
                ui.mouseMoved(mouseX, mouseY);
            }
        }
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isInBox()) {
            if (button == 0) {

            }
        }


        if (this.isOpen()) {
            for (UI ui : uis) {
                ui.mouseClicked(mouseX, mouseY, button);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        if (this.isOpen()) {
            for (UI ui : uis) {
                ui.mouseReleased(mouseX, mouseY, button);
            }
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    void initUI() {

    }


    @Override
    public int getHeight() {
        if (uiy == 0) {
            return super.getHeight();
        }
        return uiy - getY();
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        SHIFT = false;


        if (moduleInfo.isOpen()) {

            for (UI ui : uis) {
                ui.keyReleased(keyCode, scanCode, modifiers);
            }
        }

        return true;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {
        SHIFT = true;
        if (moduleInfo.isOpen()) {
            for (UI ui : uis) {
                ui.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }
}
