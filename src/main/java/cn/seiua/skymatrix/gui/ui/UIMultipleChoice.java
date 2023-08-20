package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.config.option.MultipleChoice;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.OptionInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class UIMultipleChoice extends UI {
    private DrawLine drawLine = new DrawLine(250);
    private OptionInfo<MultipleChoice> optionInfo;
    private float o = 0;

    public UIMultipleChoice(OptionInfo<MultipleChoice> optionInfo) {
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
        setHeight(52 * this.optionInfo.getTarget().value.size());
        setMid(true);
        super.update(x, y + getHeight() / 2);
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.updateMouse(mouseX, mouseY);
        MatrixStack matrixStack = context.getMatrices();
        drawLine.reset(getX() - 125);
        int t = -1 * (getHeight() / 2) + 52 / 2;

        RenderUtils.cent();
        RenderUtils.setColor(Theme.getInstance().SUBBOARD.geColor());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 250, getY() + getHeight(), 0), matrixStack, 0);


        ClickGui.iconfontRenderer26.centeredH();
        ClickGui.iconfontRenderer26.centeredV();
        ClickGui.iconfontRenderer26.setColor(Theme.getInstance().THEME_UI_SELECTED.geColor());

        String icon = this.optionInfo.getTarget().getIcon();
        if (icon == null) icon = "1";
        ClickGui.fontRenderer16.centeredH();
        ClickGui.fontRenderer16.setColor(Theme.getInstance().THEME.geColor());

        int j = 0;

        int c = getHoverIndex(mouseX, mouseY);
        for (Object key : this.optionInfo.getTarget().getValue().keySet()) {
            boolean b = this.optionInfo.getTarget().getValue().get(key);
            if (c == j) {
                RenderUtils.setColor(getBoardColoar());

                RenderUtils.drawRound2D(new Box(getX(), getY() + t, 0, getX() + getWidth(), getY() + t + 42, 0), matrixStack, 0);
            }


            RenderUtils.setColor(b ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
            RenderUtils.drawRound2D(new Box(getX(), getY() + t, 0, getX() + 200, getY() + t + 42, 0), matrixStack, 7);
            RenderUtils.setColor(Theme.getInstance().HOVERANDSELECTED.geColor());
            RenderUtils.drawRound2D(new Box(getX(), getY() + t, 0, getX() + 196, getY() + t + 38, 0), matrixStack, 7);
            ClickGui.iconfontRenderer26.setColor(Theme.getInstance().THEME_UI_SELECTED.geColor());
            ClickGui.iconfontRenderer26.drawString(matrixStack, getX() - 77, getY() + t, icon);
            ClickGui.fontRenderer16.setColor(b ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
            ClickGui.fontRenderer16.drawString(matrixStack, drawLine.get(65), getY() + t, key.toString());

            drawLine.reset(getX() - 125);
            t += 52;
            j++;
        }
        RenderUtils.setColor(optionInfo.getSign().color);
        RenderUtils.drawRound2D(new Box(getX() + 124, getY(), 0, getX() + 127, getY() + getHeight(), 0), matrixStack, 0);
        RenderUtils.resetCent();
        ClickGui.fontRenderer16.resetCenteredH();
        ClickGui.fontRenderer16.resetCenteredV();
        drawLine(matrixStack);


    }

    public int getHoverIndex(double mouseX, double mouseY) {
        if (isInBox()) {
            int start = (int) (getY() - getHeight() / 2);
            int val = 52;
            return (int) ((mouseY - start) / val);
        }
        return -1;
    }

    @Override
    public void initUI() {

    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean isInButton(double mouseX, double mouseY) {

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isInBox()) {
            if (InputUtil.isKeyPressed(SkyMatrix.mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) && button == 1) {
                this.optionInfo.openDoc();
                return super.mouseClicked(mouseX, mouseY, button);
            }
        }
//        System.out.println(button);
        if (button == 0) {
            if (isInBox()) {
                int i = getHoverIndex(mouseX, mouseY);
                if (i == -1) return true;
                int j = 0;
                for (Object o1 : this.optionInfo.getTarget().value.keySet()) {
                    if (j == i) {
                        this.optionInfo.getTarget().value.put(o1, !this.optionInfo.getTarget().value.get(o1));
                    }
                    j++;
                }

            }

        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {


        super.keyPressed(keyCode, scanCode, modifiers);
    }
}
