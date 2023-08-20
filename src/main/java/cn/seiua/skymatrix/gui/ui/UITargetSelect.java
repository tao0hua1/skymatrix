package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.config.option.TargetSelect;
import cn.seiua.skymatrix.font.FontRenderer;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.Icons;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.OptionInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Map;

public class UITargetSelect extends UI {

    private DrawLine drawLine = new DrawLine(250);
    private OptionInfo<TargetSelect> optionInfo;
    private float o = 0;
    private Map<String, String> itemMap;
    private int index;

    public UITargetSelect(OptionInfo<TargetSelect> optionInfo) {
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

    public void de(DrawContext context, double x, double y, float d) {

        MatrixStack matrixStack = context.getMatrices();
        String str = null;

        if (this.isInBox0(x, y)) {
            str = "Player-" + (this.optionInfo.getTarget().isPlayer() ? "enable" : "disable");
        }
        if (this.isInBox1(x, y)) {
            str = "Mob-" + (this.optionInfo.getTarget().isMob() ? "enable" : "disable");
        }
        if (this.isInBox2(x, y)) {
            str = "Friendly-" + (this.optionInfo.getTarget().isFriendly() ? "enable" : "disable");
        }
        x += 40;
        if (str == null) return;
        RenderUtils.setColor(Theme.getInstance().THEME_UI_SELECTED.geColor());
        RenderUtils.drawRound2D(new Box(x, y, 0, x + 170, y + 42, 0), matrixStack, 7);
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(x + 2, y + 2, 0, x + 168, y + 40, 0), matrixStack, 7);

        ClickGui.fontRenderer16.centeredH();
        ClickGui.fontRenderer16.centeredV();
        ClickGui.fontRenderer16.side = FontRenderer.LEFT_TOP;
        int w = ClickGui.fontRenderer16.getStringWidth(str);
        ClickGui.fontRenderer16.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer16.drawString(matrixStack, (int) (x + 20) + w / 2, (int) (y + 19), str);

        ClickGui.fontRenderer16.resetCenteredH();
        ClickGui.fontRenderer16.resetCenteredV();
    }

    public void update(int x, int y) {
        setWidth(250);
        setHeight(52);
        setMid(true);
        super.update(x, y + getHeight() / 2);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.updateMouse(mouseX, mouseY);
        MatrixStack matrixStack = context.getMatrices();
        drawLine.reset(getX() - 125);
        RenderUtils.cent();
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 250, getY() + getHeight(), 0), matrixStack, 0);
        RenderUtils.setColor(Theme.getInstance().THEME_UI_SELECTED.geColor());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 200, getY() + 42, 0), matrixStack, 7);
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 0, getX() + 196, getY() + 38, 0), matrixStack, 7);
        RenderUtils.setColor(optionInfo.getSign().color);
        RenderUtils.drawRound2D(new Box(getX() + 124, getY(), 0, getX() + 127, getY() + getHeight(), 0), matrixStack, 0);
        RenderUtils.resetCent();
        ClickGui.iconfontRenderer18.centeredH();
        ClickGui.iconfontRenderer18.centeredV();
        Color color = Theme.getInstance().THEME_UI_SELECTED.geColor();
        String v = "Target->";
        ClickGui.iconfontRenderer18.setColor(color);
        String icon = Icons.TARGET;
        ClickGui.iconfontRenderer18.drawString(matrixStack, getX() - 77, getY(), icon);
        ClickGui.iconfontRenderer18.setColor(this.optionInfo.getTarget().isPlayer() ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer18.drawString(matrixStack, getX() + 28, getY(), Icons.STEVE);
        ClickGui.iconfontRenderer18.setColor(this.optionInfo.getTarget().isMob() ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer18.drawString(matrixStack, getX() + 51, getY(), Icons.CREEPER);
        ClickGui.iconfontRenderer18.setColor(this.optionInfo.getTarget().isFriendly() ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer18.drawString(matrixStack, getX() + 74, getY(), Icons.SHEEP);
        ClickGui.fontRenderer16.centeredH();
        ClickGui.fontRenderer16.setColor(Theme.getInstance().THEME.geColor());
        int w = ClickGui.fontRenderer16.getStringWidth(v);
        ClickGui.fontRenderer16.setColor(color);
        ClickGui.fontRenderer16.drawString(matrixStack, drawLine.get(65), getY() - 2, v);
        drawLine.append(w);
        ClickGui.fontRenderer16.resetCenteredH();
        ClickGui.fontRenderer16.resetCenteredV();
        ClickGui.iconfontRenderer18.resetCenteredH();
        ClickGui.iconfontRenderer18.resetCenteredV();
        if (isInBox()) {

            UI.addRenderDetail(this::de);
        }
        drawLine(matrixStack);


    }

    private boolean isInBox0(double x, double y) {
        int tx = getX() + 28;
        int ty = getY();

        return new Vec3d(tx, ty, 0).distanceTo(new Vec3d(x, y, 0)) < 14;
    }

    private boolean isInBox1(double x, double y) {
        int tx = getX() + 51;
        int ty = getY();

        return new Vec3d(tx, ty, 0).distanceTo(new Vec3d(x, y, 0)) < 14;
    }

    private boolean isInBox2(double x, double y) {
        int tx = getX() + 74;
        int ty = getY();

        return new Vec3d(tx, ty, 0).distanceTo(new Vec3d(x, y, 0)) < 14;
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

    public String getSelected() {
        return null;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isInBox()) {
            if (InputUtil.isKeyPressed(SkyMatrix.mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) && button == 1) {
                this.optionInfo.openDoc();
                return super.mouseClicked(mouseX, mouseY, button);
            }
        }
        if (button == 0) {
            if (isInBox()) {
                if (isInBox0(mouseX, mouseY)) {
                    this.optionInfo.getTarget().setPlayer(!this.optionInfo.getTarget().isPlayer());
                }
                if (isInBox1(mouseX, mouseY)) {
                    this.optionInfo.getTarget().setMob(!this.optionInfo.getTarget().isMob());
                }
                if (isInBox2(mouseX, mouseY)) {
                    this.optionInfo.getTarget().setFriendly(!this.optionInfo.getTarget().isFriendly());
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
