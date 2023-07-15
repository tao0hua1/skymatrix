package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.client.Run;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;

public class UIButton extends UI {

    public Run target;

    public String name;
    public String desc;

    public UIButton(Run target, String name, String desc) {
        this.target = target;
        this.name = name;
        this.desc = desc;
        this.setInBoxRight(target);
        setWidth(52);
        setHeight(52);
        this.setMid(true);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MatrixStack matrixStack = context.getMatrices();
        RenderUtils.setColor(isInBox() ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().BOARD.geColor());
        RenderUtils.cent();
        RenderUtils.drawRound2D(new Box(getX(), getY(), 1, getX() + 52, getY() + 52, 0), matrixStack, 8);
        RenderUtils.setColor(Theme.getInstance().SUBBOARD.geColor());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 1, getX() + 48, getY() + 48, 0), matrixStack, 8);
        ClickGui.fontRenderer26.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer26.centeredH();
        ClickGui.fontRenderer26.centeredV();
        ClickGui.fontRenderer26.drawString(matrixStack, getX(), getY(), upperFirst(this.name.substring(0, 1)));
        drawHoverBox(matrixStack, mouseX, mouseY, delta);

    }

    public void drawHoverBox(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        if (!isInBox()) return;
        ClickGui.fontRenderer24.setColor(Color.WHITE);
        ClickGui.fontRenderer24.centeredH();
        ClickGui.fontRenderer24.resetCenteredV();
        ClickGui.fontRenderer24.drawString(matrixStack, 20, getY() - getHeight(), desc);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!isInBox()) return false;
        if (button == 0) {
            this.target.run();
        }
        return true;
    }

    @Override
    public void initUI() {

    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }
}
