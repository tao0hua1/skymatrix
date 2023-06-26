package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.ClientTickEvent;
import cn.seiua.skymatrix.event.events.WorldChangeEvent;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;

@Component
@Event(register = true)
public class HypixelWay {

    private final String HUB = "HUB";


    private String way;

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

    int tick;

    @EventTarget
    public void onTick(ClientTickEvent e) {
        if (tick % 20 == 0) {
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
                    if (name.contains("www.hypixel.net")) {

                    }
                }
            }
            this.hypixel = hypixel;
            this.skyblock = skyblock;
        }
        tick++;
    }


    @EventTarget
    public void onTick(WorldChangeEvent e) {
        check = true;
    }


}
