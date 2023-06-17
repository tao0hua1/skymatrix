package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.ToggleSwitch;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.HudRenderEvent;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Event(register = true)
public class Notification {

    private static final int BASEW = 455;
    private static final int BASEH = 100;
    private static Notification instance;
    @Value(name = "enable")
    public ToggleSwitch enable = new ToggleSwitch(true);
    private List<Notice> notices;

    public static Notification getInstance() {
        return instance;
    }

    @Init
    public void init() {
        instance = this;
        notices = new CopyOnWriteArrayList<>();
    }

    public void push(Notice notice) {
        if (notice != null)
            notices.add(notice);
    }

    @EventTarget
    public void onRender(HudRenderEvent event) {
        int ms = UI.getS();
        if (ms == 0 || !enable.isValue()) return;
        MatrixStack matrixStack = event.getContext().getMatrices();
        List<Notice> noticeList = new ArrayList<>();
        matrixStack.scale(1.0f / ms, 1.0f / ms, 1.0f / ms);
        noticeList.addAll(notices);
        Collections.reverse(noticeList);
        int sw = MinecraftClient.getInstance().getWindow().getWidth();
        int startX = sw - BASEW;
        int startY = MinecraftClient.getInstance().getWindow().getHeight() * 5 / 6;
        for (Notice notice : noticeList) {
            if (!notice.canRender()) continue;
            drawNotice(matrixStack, notice, startY, startX);
            startY -= 130;
        }
        matrixStack.push();
        matrixStack.scale(ms, ms, ms);

    }

    public void drawNotice(MatrixStack matrixStack, Notice notice, int starY, int startX) {
        startX += BASEW;
        float r = notice.getPercentage(System.currentTimeMillis());
        startX -= BASEW * r;
        int oX = startX + BASEW;
        int oY = starY + BASEH;
        RenderUtils.resetCent();
        RenderUtils.setColor(notice.getType().getColor());
        RenderUtils.drawRound2D(new Box(startX, starY, 0, oX, oY, 0), matrixStack, 5);
        RenderUtils.setColor(Theme.getInstance().BOARD.geColor());
        RenderUtils.drawRound2D(new Box(startX + 96, starY + 3, 0, oX, oY - 3, 0), matrixStack, 0);
        ClickGui.iconfontRenderer40.setColor(Color.white);
        ClickGui.iconfontRenderer40.centeredH();
        ClickGui.iconfontRenderer40.centeredV();
        int iconX = startX + 96 / 2;
        int iconY = starY + BASEH / 2 - 1;
        ClickGui.iconfontRenderer40.drawString(matrixStack, iconX, iconY, notice.getType().getIcon());
        int titleX = startX + 96 + 18;
        int titleY = starY + 28;
        ClickGui.fontRenderer24.setColor(Color.white);
        ClickGui.fontRenderer24.centeredH();
        ClickGui.fontRenderer24.resetCenteredV();
        ClickGui.fontRenderer24.drawString(matrixStack, titleX, titleY, notice.getTitle());
        ClickGui.fontRenderer24.resetCenteredH();
        ClickGui.fontRenderer24.resetCenteredV();
        int daX = titleX + 160;
        ClickGui.fontRenderer16.setColor(Color.white);
        ClickGui.fontRenderer16.centeredH();
        ClickGui.fontRenderer16.drawString(matrixStack, daX, titleY, notice.getDate());
        ClickGui.fontRenderer16.resetCenteredH();
        ClickGui.fontRenderer16.resetCenteredV();
        int TextX = titleX;
        int TextY = titleY + 40;
        ClickGui.fontRenderer20.setColor(Color.white);
        ClickGui.fontRenderer20.centeredH();
        ClickGui.fontRenderer20.drawString(matrixStack, TextX, TextY, notice.getMessage());
        ClickGui.fontRenderer20.resetCenteredH();
        ClickGui.fontRenderer20.resetCenteredV();
    }


}
