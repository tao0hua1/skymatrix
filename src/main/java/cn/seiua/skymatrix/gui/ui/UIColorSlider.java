package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.config.option.ColorHolder;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.ColorUtils;
import cn.seiua.skymatrix.utils.OptionInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;

public class UIColorSlider extends UI {
    public static final double TR = 8;
    private DrawLine drawLine = new DrawLine(250);
    private OptionInfo<ColorHolder> optionInfo;
    private double rate;
    private float o = 0;
    private double min;
    private double max;
    private double interval;
    private double w;
    private boolean hd;

    public UIColorSlider(OptionInfo<ColorHolder> optionInfo) {
        this.optionInfo = optionInfo;
        super.setInBoxLeft(this::toggle);
    }

    private Color getBoardColoar() {
        if (isInBox()) {
            o += 0.05;
        } else {
            o -= 0.05;
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
        setHeight(50);
        super.update(x, y + getHeight() / 2);
    }

    public void toggle() {
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        drawLine.reset(getX() - 125);
        setMid(true);


        RenderUtils.cent();
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 250, getY() + getHeight(), 0), matrixStack, 0);
        RenderUtils.setColor(optionInfo.getSign().color);
        RenderUtils.drawRound2D(new Box(getX() + 124, getY(), 0, getX() + 127, getY() + getHeight(), 0), matrixStack, 0);
        RenderUtils.resetCent();
        RenderUtils.cent();
        RenderUtils.setColor(Theme.getInstance().THEME.geColor());
        RenderUtils.drawColorRound2D(new Box(getX(), getY() + 10, 0, getX() + 200, getY() + 12, 0), matrixStack, 1);
        RenderUtils.resetCent();


        drawLine(matrixStack);

        ClickGui.fontRenderer16.centeredH();
        ClickGui.fontRenderer16.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer16.drawString(matrixStack, drawLine.get(25), getY() - 10, upperFirst(optionInfo.getName()));
        drawLine.append(ClickGui.fontRenderer16.getStringWidth(upperFirst(optionInfo.getName())));
        ClickGui.fontRenderer16.centeredV();

        ClickGui.fontRenderer16.resetCenteredH();
        ClickGui.fontRenderer16.resetCenteredV();


        double uw = 200;

        ClickGui.iconfontRenderer24.centeredH();
        ClickGui.iconfontRenderer24.centeredV();
        ClickGui.iconfontRenderer24.setColor(getBoardColoar());
        ClickGui.iconfontRenderer24.centeredV();
        ClickGui.iconfontRenderer24.drawString(matrixStack, (int) (getX() - uw / 2 + getTarget()), getY() + 8, "\uE904");
        ClickGui.iconfontRenderer24.resetCenteredH();
        ClickGui.iconfontRenderer24.resetCenteredV();

        if (isInButton(mouseX, mouseY) || hd) {
            ClickGui.iconfontRenderer18.centeredH();
            ClickGui.iconfontRenderer18.centeredV();
            ClickGui.iconfontRenderer18.setColor(this.optionInfo.getTarget().geColor());
            ClickGui.iconfontRenderer18.centeredV();
            ClickGui.iconfontRenderer18.drawString(matrixStack, (int) (getX() - uw / 2 + getTarget()), getY() + 9, "\uE904");
            ClickGui.iconfontRenderer18.resetCenteredH();
            ClickGui.iconfontRenderer18.resetCenteredV();
        } else {
            ClickGui.iconfontRenderer16.centeredH();
            ClickGui.iconfontRenderer16.centeredV();
            ClickGui.iconfontRenderer16.setColor(Theme.getInstance().THEME.geColor());
            ClickGui.iconfontRenderer16.centeredV();
            ClickGui.iconfontRenderer16.drawString(matrixStack, (int) (getX() - uw / 2 + getTarget()), getY() + 9, "\uE904");
            ClickGui.iconfontRenderer16.resetCenteredH();
            ClickGui.iconfontRenderer16.resetCenteredV();
        }


    }

    public double getTarget() {
        float f = ColorUtils.getHue(this.optionInfo.getTarget().geColor());
//        rate=(this.optionInfo.getTarget().getValue().doubleValue()-this.min)/w;
        double target = f * 200;
        return target;
    }

    @Override
    public void initUI() {

    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean isInButton(double mouseX, double mouseY) {
        double x = (getX() - 100 + getTarget());
        double y = getY() + 9;
        double r = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2));
        if (r <= TR) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (hd) {
            double max = (getX() + 100);
            double min = (getX() - 100);
            double value = mouseX;
            if (value > max) value = max;
            if (value < min) value = min;
            double v = ((value - min) / (max - min));
            if (v == 1) {
                v = 0.99f;
            }
            System.out.println(min + "   " + v + "   " + max);
            int a = this.optionInfo.getTarget().geColor().getAlpha();
            Color color = ColorUtils.setHue(this.optionInfo.getTarget().geColor(), (float) v);
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            this.optionInfo.getTarget().seColor(new Color(r, g, b, a));
        }
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            if (isInButton(mouseX, mouseY)) {
                hd = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            hd = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
