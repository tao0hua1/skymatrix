package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.HudRenderEvent;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;

@Component
@Event(register = true)
public class Notification {


    public void push(Notice notice) {

    }


    @EventTarget
    public void onRender(HudRenderEvent event) {
        Notice notice = new Notice("Test", NoticeType.INFO, "testMessage");
        int ms = Math.round(MinecraftClient.getInstance().getWindow().getWidth() * 1.0f / MinecraftClient.getInstance().getWindow().getScaledWidth());
        if (ms == 0) return;
        MatrixStack matrixStack = new MatrixStack();

        matrixStack.scale(1.0f / ms, 1.0f / ms, 1.0f / ms);
        int sw = MinecraftClient.getInstance().getWindow().getWidth();
        int baseW = 455;
        int baseH = 100;
        int startX = sw - baseW;
        int startY = 600;
        int oX = startX + baseW;
        int oY = startY + baseH;
        RenderUtils.resetCent();
        RenderUtils.setColor(notice.getType().getColor());
        RenderUtils.drawRound2D(new Box(startX, startY, 0, oX, oY, 0), matrixStack, 5);
        RenderUtils.setColor(Theme.getInstance().BOARD.geColor());
        RenderUtils.drawRound2D(new Box(startX + 96, startY + 3, 0, oX, oY - 3, 0), matrixStack, 0);
        ClickGui.iconfontRenderer40.setColor(Color.white);
        ClickGui.iconfontRenderer40.centeredH();
        ClickGui.iconfontRenderer40.centeredV();
        int iconX = startX + 96 / 2;
        int iconY = startY + baseH / 2 - 1;
        ClickGui.iconfontRenderer40.drawString(matrixStack, iconX, iconY, notice.getType().getIcon());
        int titleX = startX + 96 + 18;
        int titleY = startY + 28;
        ClickGui.fontRenderer24.setColor(Color.white);
        ClickGui.fontRenderer24.centeredH();
        ClickGui.fontRenderer24.drawString(matrixStack, titleX, titleY, notice.getTitle());
        ClickGui.fontRenderer24.resetCenteredH();
        ClickGui.fontRenderer24.resetCenteredV();
        int TextX = titleX;
        int TextY = titleY + 40;
        ClickGui.fontRenderer20.setColor(Color.white);
        ClickGui.fontRenderer20.centeredH();
        ClickGui.fontRenderer20.drawString(matrixStack, TextX, TextY, notice.getMessage());
        ClickGui.fontRenderer20.resetCenteredH();
        ClickGui.fontRenderer20.resetCenteredV();

        MinecraftClient.getInstance().getToastManager().draw(matrixStack);
    }


}
