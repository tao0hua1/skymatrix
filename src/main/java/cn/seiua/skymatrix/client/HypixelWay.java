package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.event.events.ServerPacketEvent;
import cn.seiua.skymatrix.event.events.WorldChangeEvent;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.hud.ClientHud;
import cn.seiua.skymatrix.hud.Hud;
import cn.seiua.skymatrix.utils.PlayerListUtils;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.Box;

import java.awt.*;

@Component
@Event(register = true)
public class HypixelWay implements Hud {

    private final String HUB = "HUB";

    @Value(name = "waypoint")
    public ClientHud clientHud = new ClientHud(100, 100, true, this);
    private String way;
    private String subWay;

    private boolean hypixel;
    private boolean skyblock;

    private boolean check;

    public boolean isHypixel() {
        return hypixel;
    }

    public void setHypixel(boolean hypixel) {
        this.hypixel = hypixel;
    }

    public boolean isSkyblock() {
        return skyblock;
    }

    public void setSkyblock(boolean skyblock) {
        this.skyblock = skyblock;
    }

    public boolean isIn(String name) {

        return this.way.equals(name);
    }

    public String subWay() {
        return subWay;
    }

    public String way() {

        return way;
    }

    int tick;

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (tick % 20 == 0) {
            this.way = "NONE";
            this.subWay = "NONE";
            boolean hypixel = false;
            boolean skyblock = false;
            for (ScoreboardObjective scoreboard : SkyMatrix.mc.world.getScoreboard().getObjectives()) {
                if (scoreboard.getDisplayName().getString().contains("SKYBLOCK")) {
                    skyblock = true;
                }
                for (Team team : SkyMatrix.mc.world.getScoreboard().getTeams()) {
                    String name = team.getPrefix().getString() + team.getSuffix().getString();
                    if (name.contains("www.hypixel.net")) {
                        hypixel = true;
                    }
                    String point = Character.toString(9187);
                    if (name.contains(point)) {
                        int a = name.indexOf(point);
                        this.subWay = name.substring(a + 2).trim();
                    }
                }
            }
            String temp = PlayerListUtils.copyContainsLine("Area: ");
            if (temp != null) {
                String name = temp.replace("Area: ", "").trim();
                this.way = name;
            }


            this.hypixel = hypixel;
            this.skyblock = skyblock;
        }
        tick++;
    }

    @EventTarget
    public void onPacket(ServerPacketEvent event) {
        if (event.getPacket() instanceof GameMessageS2CPacket) {
            GameMessageS2CPacket eventPacket = (GameMessageS2CPacket) event.getPacket();


        }
    }

    @EventTarget
    public void onTick(WorldChangeEvent e) {
        check = true;
    }


    @Override
    public void draw(MatrixStack matrixStack, float x, float y) {

        RenderUtils.resetCent();
        RenderUtils.setColor(new Color(0, 0, 0, 100));
        RenderUtils.drawSolidBox(new Box(x, y, 0, x + getHudWidth(), y + getHudHeight(), 0), matrixStack);
        ClickGui.fontRenderer20.setColor(Color.WHITE);
        ClickGui.fontRenderer20.resetCenteredH();
        ClickGui.fontRenderer20.resetCenteredV();
        float startX = x + 15;
        float startY = y + 12;
        ClickGui.fontRenderer20.drawString(matrixStack, startX, startY, String.format("当前位置: %s  %s", way(), subWay()));


    }

    @Override
    public int getHudWidth() {

        return ClickGui.fontRenderer20.getStringWidth(String.format("当前位置: %s  %s", way(), subWay())) + 32;

    }


    @Override
    public int getHudHeight() {
        return 53;
    }
}
