package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.hud.ClientHud;
import cn.seiua.skymatrix.utils.OptionInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;

public class UIClientHudToggle extends UI {
    private DrawLine drawLine = new DrawLine(250);
    private OptionInfo<ClientHud> optionInfo;
    private float o = 0;

    public UIClientHudToggle(OptionInfo<ClientHud> optionInfo) {
        this.optionInfo = optionInfo;

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
        Color color = Theme.getInstance().SUBBOARD.geColor();
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        Color ac = Color.getHSBColor(hsb[0], hsb[1], Math.max(hsb[2], 0.35f * o));
        return ac;
    }

    public void update(int x, int y) {
        setWidth(250);
        setHeight(52);
        setMid(true);
        super.update(x, y + getHeight() / 2);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        drawLine.reset(getX() - 125);

        RenderUtils.cent();
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), -1, getX() + 250, getY() + getHeight(), -1), matrixStack, 0);
        RenderUtils.setColor(isInBox() || this.optionInfo.getTarget().enable ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        RenderUtils.drawRound2D(new Box(getX(), getY(), -1, getX() + 200, getY() + 42, -1), matrixStack, 7);
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), -1, getX() + 196, getY() + 38, -1), matrixStack, 7);
        RenderUtils.setColor(optionInfo.getSign().color);
        RenderUtils.drawRound2D(new Box(getX() + 124, getY(), 0, getX() + 127, getY() + getHeight(), 0), matrixStack, 0);
        RenderUtils.resetCent();

        ClickGui.iconfontRenderer26.centeredH();
        ClickGui.iconfontRenderer26.centeredV();
        ClickGui.iconfontRenderer26.setColor(this.optionInfo.getTarget().enable ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        String icon = "\uE9CE";
        ClickGui.iconfontRenderer26.drawString(matrixStack, getX() - 77, getY(), icon);
        ClickGui.fontRenderer16.centeredH();
        ClickGui.fontRenderer16.setColor(Theme.getInstance().THEME.geColor());

        String v = "HUD: " + this.optionInfo.getName();

        int w = ClickGui.fontRenderer16.getStringWidth(v);
        ClickGui.fontRenderer16.drawString(matrixStack, drawLine.get(65), getY(), v);
        drawLine.append(w);

        ClickGui.fontRenderer16.resetCenteredH();
        ClickGui.fontRenderer16.resetCenteredV();


        drawLine(matrixStack);


    }

    @Override
    public void initUI() {

    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean isInButton(double mouseX, double mouseY) {
        double x = getX() + 77;
        double y = getY();
        double r = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));
        if (r <= 18) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (isInBox()) {
                this.optionInfo.getTarget().setEnable(!this.optionInfo.getTarget().isEnable());

            }

        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {


        super.keyPressed(keyCode, scanCode, modifiers);
    }
}
