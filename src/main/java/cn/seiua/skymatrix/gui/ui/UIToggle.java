package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.config.option.ToggleSwitch;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.OptionInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;

public class UIToggle extends UI {
    private DrawLine drawLine = new DrawLine(250);
    private OptionInfo<ToggleSwitch> optionInfo;
    private float o = 0;

    public UIToggle(OptionInfo<ToggleSwitch> optionInfo) {
        this.optionInfo = optionInfo;

        super.setInBoxLeft(this::toggle);
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
        setHeight(38);
        super.update(x, y + getHeight() / 2);
    }

    public void toggle() {
        this.optionInfo.getTarget().setValue(!this.optionInfo.getTarget().isValue());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.updateMouse(mouseX, mouseY);
        MatrixStack matrixStack = context.getMatrices();
        drawLine.reset(getX() - 125);
        setWidth(250);
        setHeight(38);
        setMid(true);
        RenderUtils.cent();
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 250, getY() + 40, 0), matrixStack, 0);
        RenderUtils.setColor(optionInfo.getSign().color);
        RenderUtils.drawRound2D(new Box(getX() + 124, getY(), 0, getX() + 127, getY() + 40, 0), matrixStack, 0);
        RenderUtils.resetCent();
        ClickGui.fontRenderer16.centeredH();
        ClickGui.fontRenderer16.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer16.drawString(matrixStack, drawLine.get(25), getY(), upperFirst(optionInfo.getName()));
        ClickGui.fontRenderer16.resetCenteredH();
        ClickGui.fontRenderer16.resetCenteredV();
        ClickGui.iconfontRenderer24.centeredH();
        ClickGui.iconfontRenderer24.centeredV();
        drawLine(matrixStack);
        if (optionInfo.getTarget().isValue()) {

            ClickGui.iconfontRenderer24.setColor(Theme.getInstance().THEME_UI_SELECTED.geColor());
            ClickGui.iconfontRenderer24.drawString(matrixStack, getX() + 96 - ClickGui.iconfontRenderer24.getStringWidth("\uE90E") / 2, getY(), "\uE90E");
        } else {
            ClickGui.iconfontRenderer24.setColor(Theme.getInstance().THEME.geColor());
            ClickGui.iconfontRenderer24.drawString(matrixStack, getX() + 96 - ClickGui.iconfontRenderer24.getStringWidth("\uE90E") / 2, getY(), "\uE90D");
        }
        ClickGui.iconfontRenderer24.resetCenteredH();
        ClickGui.iconfontRenderer24.resetCenteredV();

    }

    @Override
    public void initUI() {

    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }
}
