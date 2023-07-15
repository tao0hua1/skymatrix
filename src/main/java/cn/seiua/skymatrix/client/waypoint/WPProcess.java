package cn.seiua.skymatrix.client.waypoint;

import baritone.api.BaritoneAPI;
import baritone.api.pathing.goals.GoalBlock;

import java.util.ArrayList;

public class WPProcess implements Runnable {

    private boolean circle;
    private WaypointGroupEntity groupEntity;
    private int index = 0;
    private int count = -1;
    private boolean over = false;

    public WPProcess(boolean circle, WaypointGroupEntity groupEntity) {
        this.circle = circle;
        this.groupEntity = groupEntity;
        if (circle) {
            count = 0;
        }
    }

    public void start() {
        reset();
        new Thread(this).start();
    }

    private void reset() {
        over = false;
        count = -1;
        index = 0;
        if (circle) {
            count = 0;
        }
    }

    public void stop() {
        BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().cancelEverything();
        index = -1;

    }

    public float getCDegree() {
        return (float) (getCurrentIndex() - 1) / getTargetIndex();
    }

    public int getCurrentIndex() {
        return index;
    }

    public int getTargetIndex() {
        return groupEntity.getWaypoints().size();
    }

    public int getCount() {
        return count;
    }

    public boolean isOver() {
        return over;
    }


    @Override
    public void run() {
        while (true) {
            if (index == -1) {
                index = 0;
                break;
            }
            if (!BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                ArrayList<WaypointEntity> wps = this.groupEntity.getWaypoints();
                if (wps.size() - 1 < index) {
                    index = 0;
                    if (!circle) {
                        count = 0;
                        break;
                    }
                    continue;

                }

                WaypointEntity wp = wps.get(index);
                BaritoneAPI.getSettings().allowSprint.value = true;
                BaritoneAPI.getSettings().avoidance.value = false;
                BaritoneAPI.getSettings().allowWalkOnBottomSlab.value = true;
                BaritoneAPI.getSettings().antiCheatCompatibility.value = true;
                BaritoneAPI.getSettings().allowParkour.value = true;
                BaritoneAPI.getSettings().allowBreak.value = false;
                BaritoneAPI.getSettings().allowParkourAscend.value = true;
                BaritoneAPI.getSettings().renderGoal.value = false;
                BaritoneAPI.getSettings().renderPath.value = false;
                BaritoneAPI.getSettings().assumeSafeWalk.value = false;
                BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalBlock(wp.getX(), wp.getY(), wp.getZ()));
                index++;
            } else {
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        over = true;
    }


}
