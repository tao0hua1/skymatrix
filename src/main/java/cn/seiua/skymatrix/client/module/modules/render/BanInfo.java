package cn.seiua.skymatrix.client.module.modules.render;

import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.SModule;
import cn.seiua.skymatrix.client.component.Use;
import cn.seiua.skymatrix.client.entity.BanInfoEntity;
import cn.seiua.skymatrix.client.httpclient.HttpClient;
import cn.seiua.skymatrix.client.module.Sign;
import cn.seiua.skymatrix.client.module.Signs;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.font.FontRenderer;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.hud.ClientHud;
import cn.seiua.skymatrix.hud.Hud;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.io.IOException;

@Event
@Sign(sign = Signs.FREE)
@SModule(name = "banInfo", category = "render")
public class BanInfo implements Hud {


    @Value(name = "hud")
    public ClientHud clientHud = new ClientHud(30, 30, true, this);
    @Use
    public HttpClient httpClient;

    int tick = 0;

    @EventTarget
    public void onTick(ClientTickEvent e) {
        tick++;
        if (tick % (20 * 60 * 5) == 0) {
            try {
                httpClient.get("https://api.plancke.io/hypixel/v1/punishmentStats", this::callBack, BanInfoEntity.class);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    }

    public void callBack(BanInfoEntity value, String data) {
        if (last == null) {
            last = value;
            currnet = value;
            return;
        }
        last = currnet;
        currnet = value;
    }

    private BanInfoEntity last;
    private BanInfoEntity currnet;

    @Override
    public void draw(MatrixStack matrixStack, float x, float y) {

        RenderUtils.resetCent();
        RenderUtils.setColor(new Color(0, 0, 0, 100));
        RenderUtils.drawSolidBox(new Box(x, y, 0, x + getHudWidth(), y + getHudHeight(), 0), matrixStack);
        ClickGui.fontRenderer20.resetCenteredH();
        ClickGui.fontRenderer20.resetCenteredV();
        ClickGui.fontRenderer20.side = FontRenderer.RIGHT_TOP;
        float startX = x + 15;
        float startY = y + 12;
        String v = "0";
        if (this.last != null) {
            v = String.valueOf(Math.abs(this.currnet.getRecord().getStaff_total() - this.last.getRecord().getStaff_total()));
        }
        ClickGui.fontRenderer20.drawString(matrixStack, startX, startY, String.format("Staff banned %s people within 5 minutes", v));
    }

    @Override
    public int getHudWidth() {
        String v = "0";
        if (this.last != null) {
            v = String.valueOf(Math.abs(this.currnet.getRecord().getStaff_total() - this.last.getRecord().getStaff_total()));
        }
        return ClickGui.fontRenderer20.getStringWidth(String.format("Staff banned %s people within 5 minutes", v) + 34);
    }

    @Override
    public int getHudHeight() {
        return 53;
    }
}
