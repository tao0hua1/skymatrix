package cn.seiua.skymatrix.gui;


import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.*;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.*;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.config.option.MapValueHolder;
import cn.seiua.skymatrix.config.option.ValueInput;
import cn.seiua.skymatrix.font.FontRenderer;
import cn.seiua.skymatrix.font.FontUtils;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.gui.ui.UIButton;
import cn.seiua.skymatrix.gui.ui.UIModules;
import cn.seiua.skymatrix.hud.HudManager;
import cn.seiua.skymatrix.utils.CateInfo;
import cn.seiua.skymatrix.utils.ModuleInfo;
import cn.seiua.skymatrix.utils.RenderUtils;
import cn.seiua.skymatrix.utils.UiInfo;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.List;
import java.util.*;

@Component
@Config(name = "clickgui")
public class ClickGui extends Screen {

    public static FontRenderer fontRenderer18;
    public static FontRenderer fontRenderer16;

    public static FontRenderer fontRenderer24;
    public static FontRenderer fontRenderer22;
    public static FontRenderer fontRenderer20;
    public static FontRenderer fontRenderer26;
    public static FontRenderer fontRenderer28;
    public static FontRenderer fontRenderer30;
    public static FontRenderer fontRenderer32;
    public static FontRenderer iconfontRenderer30;
    public static FontRenderer iconfontRenderer28;
    public static FontRenderer iconfontRenderer26;
    public static ClickGui instance;
    @Use
    public Notification notification;
    public static FontRenderer iconfontRenderer24;
    public static FontRenderer iconfontRenderer22;
    public static FontRenderer iconfontRenderer20;
    public static FontRenderer iconfontRenderer18;
    public static FontRenderer iconfontRenderer16;
    public static FontRenderer iconfontRenderer40;
    @Use
    public Client client;
    @Value(name = "KEYBIND")
    public KeyBind bind = new KeyBind("KEYBIND", Arrays.asList(GLFW.GLFW_KEY_RIGHT_SHIFT), this::open);
    @Use
    public List<Object> component;
    private Map<String, UIModules> modules = new HashMap<>();
    private int t;
    @Use
    public ConfigManager configManager;
    @Value(name = "state")
    public MapValueHolder<String, UiInfo, UiInfo> valueHolder = new MapValueHolder<>(new HashMap(), new UiInfo());
    public KeyBind keyBind;
    int k1 = -1;
    int k2 = -1;

    public ClickGui() {
        super(Text.empty());
    }

    public static UiInfo getValue(String key) {
        if (!instance.valueHolder.value.containsKey(key)) {
            instance.valueHolder.value.put(key, new UiInfo());
        }
        return instance.valueHolder.value.get(key);
    }

    private List<UI> uiList = new ArrayList<>();
    @Value(name = "last")
    private ValueInput last = new ValueInput("NMSL", "");

    int k3 = -1;
    int shiftign;
    int mouse1ign;
    private UI focus;

    public UI getFocus() {
        return focus;
    }

    public void setFocus(UI focus) {
        this.focus = focus;
    }

    public void tick1() {
        while (true) {
            try {
                Thread.sleep(3);
                if (SkyMatrix.mc.currentScreen == this) {
                    for (UIModules ui : modules.values()) {
                        ui.updateUI();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void close() {

        configManager.writeToProfile();
        configManager.saveProfiles();
        notification.push(new Notice("Config", "profile: " + configManager.getCurrent().getName() + " saved successfully", NoticeType.INFO));
        super.close();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        for (UIModules uiModules : modules.values()) {
            uiModules.mouseScrolled(mouseX, mouseX, amount);
        }

        return super.mouseScrolled(mouseX, mouseY, amount);
    }

    @Init
    public void initClickGui() {
        instance = this;
        fontRenderer32 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 32);
        fontRenderer30 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 30);
        fontRenderer28 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 28);
        fontRenderer26 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 26);
        fontRenderer24 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 24);
        fontRenderer22 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 22);
        fontRenderer20 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 20);
        fontRenderer18 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 18);
        fontRenderer16 = FontUtils.getFontRenderer("fzb.ttf", Font.PLAIN, 16);
        iconfontRenderer30 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 30);
        iconfontRenderer28 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 28);
        iconfontRenderer26 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 26);
        iconfontRenderer24 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 24);
        iconfontRenderer22 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 22);
        iconfontRenderer20 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 20);
        iconfontRenderer18 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 18);
        iconfontRenderer16 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 16);
        iconfontRenderer40 = FontUtils.getFontRenderer("icomoon.ttf", Font.PLAIN, 70);
        Map<String, CateInfo> categorys = new HashMap<>();
        Map<String, ModuleInfo> modules = new HashMap<>();
        for (Object o : component) {
            Class c = o.getClass();
            SModule module = (SModule) c.getAnnotation(SModule.class);
            Category category = (Category) c.getAnnotation(Category.class);
            Sign sign = (Sign) c.getAnnotation(Sign.class);
            if (module != null) {
                modules.put(module.category() + "." + module.name(), new ModuleInfo(module.category() + "." + module.name(), module.name(), c, o, sign == null ? null : sign.sign(), module.category()));
            }
            if (category != null) {
                categorys.put(category.name(), new CateInfo(category.name(), category.name(), c, o, sign == null ? null : sign.sign()));
            }
        }
        int yt = 230;
        for (String key : modules.keySet()) {
            ModuleInfo moduleInfo = modules.get(key);
            String category = moduleInfo.getCategory();
            if (categorys.containsKey(category)) {
                UIModules u = null;
                if (!this.modules.containsKey(category)) {
                    u = new UIModules(categorys.get(category));
                    this.modules.put(category, u);
                    u.setX(500);
                    u.setY(yt);
                    u.update(-1, -1);
                    yt += 58;
                }
                u = this.modules.get(category);
                u.addModuleInfo(moduleInfo);
            }
        }
        uiList.add(new UIButton(() -> {
            client.openGui(HudManager.class);
        }, "hud", "Open gui to edit hud!"));
        uiList.add(new UIButton(() -> {
            client.openGui(HudManager.class);
        }, "hud", "Open gui to edit hud!"));
        uiList.add(new UIButton(() -> {
            client.openGui(HudManager.class);
        }, "hud", "Open gui to edit hud!"));
        uiList.add(new UIButton(() -> {
            client.openGui(HudManager.class);
        }, "hud", "Open gui to edit hud!"));
        new Thread(this::tick1).start();

    }

    public void drawMask(MatrixStack matrices) {


    }

    @Override
    protected void init() {


        for (UI ui : modules.values()) {
            ui.initUI();
        }
        super.init();
    }

    private void append(int key, int t) {
        if (k1 == key) {
            return;
        }
        if (k2 == key) {
            return;
        }
        if (k3 == key) {
            return;
        }


        if (k1 == -1) {
            k1 = t == 0 ? key : KeyBindManger.MOUSE + t;
        } else {
            if (k2 == -1) {
                k2 = t == 0 ? key : KeyBindManger.MOUSE + t;
            } else {
                if (k3 == -1) {
                    k3 = t == 0 ? key : KeyBindManger.MOUSE + t;
                }
            }
        }


        List<Integer> keys = new ArrayList<>();
        if (k1 != -1) keys.add(k1);
        if (k2 != -1) keys.add(k2);
        if (k3 != -1) keys.add(k3);
        this.keyBind.setKeys(keys);

    }

    private void resetKeyBind() {
        this.keyBind = null;
        k1 = -1;
        k2 = -1;
        k3 = -1;
        ClickGui.instance.setFocus(null);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        mouseY = mouseY * UI.getS();
        mouseX = mouseX * UI.getS();
        int height = this.height * UI.getS();
        int width = this.width * UI.getS();
        matrices.scale(1f / UI.getS(), 1f / UI.getS(), 1f / UI.getS());

        int ap = 0;
        for (UI ui : uiList) {
            ui.update(36 + ap, height - 36);
            ui.render(matrices, mouseX, mouseY, delta);
            ap += ui.getWidth() + 6;
        }
        drawMask(matrices);
        UIModules l = modules.get(this.last.getValue());

        for (UIModules ui : modules.values()) {

            if (!ui.getID().equals(this.last.getValue())) {


                int uih = ui.getMaskHeight() + ui.getHeight();
                RenderUtils.drawMask(matrices, new Box(ui.getX() - ui.getWidth() / 2, ui.getY() - ui.getHeight() / 2, 1, ui.getX() - ui.getWidth() / 2 + 250, ui.getY() - ui.getHeight() / 2 + uih, 1));

                ui.render(matrices, mouseX, mouseY, delta);
                RenderUtils.clearMask();
            }


        }
        if (l != null) {
            int uih = l.getMaskHeight() + l.getHeight();
            RenderUtils.drawMask(matrices, new Box(l.getX() - l.getWidth() / 2, l.getY() - l.getHeight() / 2, 1, l.getX() - l.getWidth() / 2 + 250, l.getY() - l.getHeight() / 2 + uih, 1));

            l.render(matrices, mouseX, mouseY, delta);
            RenderUtils.clearMask();
        }

        if (focus != null) {

            Screen.fillGradient(matrices, 0, 0, width, height, -1072689136, -804253680);
            focus.render(matrices, mouseX, mouseY, delta);
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mouseY = mouseY * UI.getS();
        mouseX = mouseX * UI.getS();

        if (mouse1ign > 0) {
            mouse1ign--;
            return true;
        }
        if (keyBind != null) {
            append(button, 1);
            return true;
        }

        if (focus != null) {
            focus.mouseClicked(mouseX, mouseY, button);
            return true;
        }
        for (UI ui : uiList) {

            ui.mouseClicked(mouseX, mouseY, button);

        }
        List<UIModules> list = new ArrayList<>(modules.values().stream().toList());
        Collections.reverse(list);


        for (UIModules uiModules : list) {
            if (uiModules.mouseClicked(mouseX, mouseY, button)) {
                last.setValue(uiModules.getID());
            }
        }


        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        mouseY = mouseY * UI.getS();
        mouseX = mouseX * UI.getS();

        if (mouse1ign > 0) {
            mouse1ign--;
            return true;
        }
        if (keyBind != null) {
            resetKeyBind();
            return true;
        }

        if (focus != null) {
            focus.mouseReleased(mouseX, mouseY, button);
            return true;
        }
        List<UIModules> list = new ArrayList<>(modules.values().stream().toList());
        Collections.reverse(list);
        for (UIModules uiModules : list) {
            uiModules.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        mouseY = mouseY * UI.getS();
        mouseX = mouseX * UI.getS();
        List<UIModules> list = new ArrayList<>(modules.values().stream().toList());
        Collections.reverse(list);
        if (focus != null) {
            focus.mouseMoved(mouseX, mouseY);
            return;
        }
        for (UI ui : uiList) {

            ui.mouseMoved(mouseX, mouseY);

        }
        for (UIModules uiModules : list) {
            uiModules.mouseMoved(mouseX, mouseY);

        }
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (shiftign == 2) {

            return true;
        }
        if (keyBind != null) {
            if (GLFW.GLFW_KEY_ESCAPE == keyCode) {
                resetKeyBind();
                return true;
            }
            append(keyCode, 0);


            return true;
        }

        if (focus != null) {
            focus.keyPressed(keyCode, scanCode, modifiers);
            return true;
        }

        for (UIModules uiModules : modules.values()) {
            uiModules.keyPressed(keyCode, scanCode, modifiers);
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void open() {


        client.openGui(ClickGui.class);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (shiftign == 2) {
            shiftign = -1;
            return true;
        }
        if (keyBind != null) {
            resetKeyBind();
            return true;
        }

        if (focus != null) {
            focus.keyReleased(keyCode, scanCode, modifiers);
            return true;
        }
        for (UIModules uiModules : modules.values()) {
            uiModules.keyReleased(keyCode, scanCode, modifiers);
        }

        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    public void setupKeyBind(KeyBind keyBind) {
        shiftign = 2;
        mouse1ign++;
        this.keyBind = keyBind;
        this.keyBind.setKeys(new ArrayList<Integer>());
    }
}
