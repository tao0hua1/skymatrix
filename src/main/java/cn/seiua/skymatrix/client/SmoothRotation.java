package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;

@Component
@Event(register = true)
public class SmoothRotation {
    public float yawDifference;
    public boolean running = false;
    private float pitchDifference;
    private int ticks = -1;
    private int tickCounter = 0;
    private Run callback = null;

    public SmoothRotation() {
        new Thread(this::update).start();
    }

    private void update() {
        while (true) {
            try {
                Thread.sleep(2l);
                onTick();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void smoothLook(Rotation rotation, int ticks, Run callback) {
        if (this.ticks == 0) {
            look(rotation);
            callback.run();
            return;
        }

        this.callback = callback;

        pitchDifference = wrapAngleTo180(rotation.getPitch() - SkyMatrix.mc.player.getPitch());
        yawDifference = wrapAngleTo180(rotation.getYaw() - SkyMatrix.mc.player.getYaw());

        this.ticks = ticks * 20;
        tickCounter = 0;
    }

    public void look(Rotation rotation) {
        SkyMatrix.mc.player.setPitch(rotation.getPitch());
        SkyMatrix.mc.player.setYaw(rotation.getYaw());
    }


    public void onTick() {

        if (SkyMatrix.mc.player == null) return;
        if (tickCounter < ticks) {
            running = true;
            SkyMatrix.mc.player.setPitch(SkyMatrix.mc.player.getPitch() + pitchDifference / ticks);
            SkyMatrix.mc.player.setYaw(SkyMatrix.mc.player.getYaw() + yawDifference / ticks);
            tickCounter++;
        } else if (callback != null) {
            running = false;
            callback.run();
            callback = null;
        }
    }

    private double wrapAngleTo180(double angle) {
        return angle - Math.floor(angle / 360 + 0.5) * 360;
    }

    private float wrapAngleTo180(float angle) {
        return (float) (angle - Math.floor(angle / 360 + 0.5) * 360);
    }
}
