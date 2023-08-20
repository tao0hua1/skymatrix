package cn.seiua.skymatrix.gui.ui;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.KeyBindManger;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.DrawLine;
import cn.seiua.skymatrix.gui.HideB;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.ModuleInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import cn.seiua.skymatrix.utils.UiInfo;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashMap;
import java.util.List;


public class UIModule extends UI {
    public ModuleInfo moduleInfo;
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

    @Override
    public boolean charTyped(char chr, int modifiers) {

        for (UI ui : uis) {
            ui.charTyped(chr, modifiers);
        }

        return true;
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

    private int scroll = 0;

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

    private HashMap<UI, List<HideB>> hideMap;

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
    public void setMouse(double i, double i1) {
        for (UI ui : uis) {
            ui.setMouse(i, i1);
        }
        super.setMouse(i, i1);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.updateMouse(mouseX, mouseY);
        MatrixStack matrixStack = context.getMatrices();
        drawLine.reset(getX() - 125);
        setWidth(250);
        setHeight(58);
        setMid(true);
        RenderUtils.cent();
        RenderUtils.setColor(getBoardColoar());
        RenderUtils.drawRound2D(new Box(getX(), getY(), getZ(), getX() + 250, getY() + 58, getZ()), matrixStack, 0);
        RenderUtils.setColor(moduleInfo.getSign().color);
        RenderUtils.drawRound2D(new Box(getX() + 124, getY(), getZ(), getX() + 127, getY() + 58, getZ()), matrixStack, 0);

        ClickGui.fontRenderer22.centeredH();
        ClickGui.fontRenderer22.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer22.drawString(matrixStack, drawLine.get(25), getY(), getZ(), upperFirst(Text.translatable(moduleInfo.getName()).getString()));
        ClickGui.fontRenderer22.resetCenteredH();
        ClickGui.fontRenderer22.resetCenteredV();
        if (!this.uis.isEmpty()) {
            ClickGui.iconfontRenderer24.centeredH();
            ClickGui.iconfontRenderer24.centeredV();
            ClickGui.iconfontRenderer24.setColor(Theme.getInstance().THEME.geColor());
            ClickGui.iconfontRenderer24.setDrawSize(30);
            ClickGui.iconfontRenderer24.drawString(matrixStack, getX() + 96, getY(), getZ(), "\uE90C");
            ClickGui.iconfontRenderer24.resetCenteredH();
            ClickGui.iconfontRenderer24.resetCenteredV();
        }

        KeyBind keyBind = this.moduleInfo.getKeyBind();
        if (Screen.hasShiftDown() || ClickGui.instance.keyBind == keyBind) {

            if (keyBind != null) {
                String name = "";
                if (keyBind.getKeys().size() != 0) {
                    for (int i : keyBind.getKeys()) {
                        if (name.equals("")) {
                            name = UI.upperFirst(KeyBindManger.getKeyName(i));
                            continue;
                        }
                        name = name + "+" + UI.upperFirst(KeyBindManger.getKeyName(i));
                    }
                } else {
                    if (ClickGui.instance.keyBind == keyBind) {
                        name = "press keys";
                    } else {
                        name = "none";
                    }

                }

                if (name != "") {
                    int w = ClickGui.fontRenderer16.getStringWidth(name);
                    int st = getX() + 80 - w / 2;
                    RenderUtils.setColor(Theme.getInstance().SUBBOARD.geColor());
                    RenderUtils.drawRound2D(new Box(st, getY(), getZ(), st + w + 20, getY() + 24, getZ()), matrixStack, 4);
                    ClickGui.fontRenderer16.centeredH();
                    ClickGui.fontRenderer16.centeredV();
                    ClickGui.fontRenderer16.setColor(Theme.getInstance().THEME.geColor());
                    ClickGui.fontRenderer16.drawString(matrixStack, st, getY(), getZ(), name);
                    ClickGui.fontRenderer16.resetCenteredH();
                    ClickGui.fontRenderer16.resetCenteredV();
                }
            }
        }
        if (this.isOpen()) {
            int sty = getY() + 58 / 2;
            int i = 0;


            for (UI ui : uis) {
                if (i < scroll) {
                    i++;
                    continue;
                }
                if (hideMap != null && hideMap.get(ui) != null) {
                    boolean flag = true;
                    for (HideB hideB : hideMap.get(ui)) {
                        flag = flag && hideB.canRender();
                    }
                    if (!flag) {
                        continue;
                    }
                }
                ui.update(getX(), sty);
                ui.render(context, mouseX, mouseY, delta);
                sty += ui.getHeight();

                i++;
            }


            uiy = sty + 58 / 2;
        } else {
            uiy = 0;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isInBox()) {
            if (InputUtil.isKeyPressed(SkyMatrix.mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT) && button == 1) {
                this.moduleInfo.openDoc();
                return super.mouseClicked(mouseX, mouseY, button);
            }
        }
        if (isInBox()) {
            if (Screen.hasShiftDown() && button == 0) {
                if (this.moduleInfo.getKeyBind() != null) {
                    ClickGui.instance.setupKeyBind(this.moduleInfo.getKeyBind());
                    ClickGui.instance.setFocus(this);
                }
                return true;
            }
        }


        if (this.isOpen()) {
            int i = 0;
            for (UI ui : uis) {
                if (i < scroll) {
                    i++;
                    continue;
                }
                if (hideMap != null && hideMap.get(ui) != null) {
                    boolean flag = true;
                    for (HideB hideB : hideMap.get(ui)) {
                        flag = flag && hideB.canRender();
                    }
                    if (!flag) {
                        continue;
                    }
                }

                ui.mouseClicked(mouseX, mouseY, button);
                i++;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void initUI() {

        for (UI ui : uis) {
            ui.initUI();
        }
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

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (isInBox()) {
            scroll += (-1 * amount);
            if (scroll <= 0) {
                scroll = 0;
            }
            int cu = 0;
            for (UI ui : this.getUis()) {
                if (hideMap != null && hideMap.get(ui) != null) {
                    boolean flag = true;
                    for (HideB hideB : hideMap.get(ui)) {
                        flag = flag && hideB.canRender();
                    }
                    if (!flag) {
                        cu++;
                    }
                }
            }
            if (scroll >= this.uis.size() - cu) {
                scroll = this.uis.size() - cu;

            }
        }
        if (this.isOpen()) {
            for (UI ui : uis) {
                ui.mouseScrolled(mouseX, mouseY, amount);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    public void setHideMap(HashMap<UI, List<HideB>> hideMap) {

        this.hideMap = hideMap;
    }


}
