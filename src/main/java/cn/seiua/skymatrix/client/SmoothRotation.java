package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Use;

@Component
@Event(register = true)
public class SmoothRotation {
    public float yawDifference;
    public boolean running = false;
    private float pitchDifference;
    private int ticks = -1;
    private int tickCounter = 0;

    public RotatioTask task;

    public boolean isClient() {
        if (task == null || task.isClient()) {
            return true;
        }
        return false;
    }

    @Use
    public RotationFaker rotationFaker;

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

    public void smoothLook(Rotation rotation, float ticks, Run callback, boolean client) {
        if (this.ticks == 0) {
            look(rotation);
            if (callback != null)
                callback.run();
            this.running = false;
            return;
        }

        this.task = new RotatioTask(callback, client);

        pitchDifference = wrapAngleTo180(rotation.getPitch() - rotationFaker.getServerPitch());
        yawDifference = wrapAngleTo180(rotation.getYaw() - rotationFaker.getServerYaw());

        this.ticks = (int) (ticks * 20);
        tickCounter = 0;

    }

    public void look(Rotation rotation) {

//        SkyMatrix.mc.player.setPitch(rotationFaker.getServerPitch());
//        SkyMatrix.mc.player.setYaw(rotationFaker.getServerYaw());
    }

    public void reset() {
        this.tickCounter = 0;
        ticks = 0;
        task = null;
        running = false;
    }

    public void onTick() {
        if (!enable) return;
        if (SkyMatrix.mc.player == null) return;
        if (task == null) return;
        if (tickCounter < ticks) {
            running = true;
            if (task.client) {


                rotationFaker.faceVectorClient(new Rotation(rotationFaker.getServerYaw() + yawDifference / ticks, rotationFaker.getServerPitch() + pitchDifference / ticks));

            } else
                rotationFaker.faceVectorPacket(new Rotation(rotationFaker.getServerYaw() + yawDifference / ticks, rotationFaker.getServerPitch() + pitchDifference / ticks));
            tickCounter++;
        } else if (task.getCallback() != null) {
            running = false;
            task.getCallback().run();
            if (!this.task.client) {
                rotationFaker.setServerPitch(SkyMatrix.mc.player.getPitch());
                rotationFaker.setServerYaw(SkyMatrix.mc.player.getYaw());
            }
            task = null;

        } else {
            if (!this.task.client) {
                rotationFaker.setServerPitch(SkyMatrix.mc.player.getPitch());
                rotationFaker.setServerYaw(SkyMatrix.mc.player.getYaw());
            }
            task = null;
            running = false;
        }
    }

    private double wrapAngleTo180(double angle) {
        return angle - Math.floor(angle / 360 + 0.5) * 360;
    }

    private float wrapAngleTo180(float angle) {
        return (float) (angle - Math.floor(angle / 360 + 0.5) * 360);
    }

    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    public void enable() {
        enable = true;
    }

    public void disable() {
        enable = false;
    }

    class RotatioTask {
        private Run callback = null;

        private boolean client;

        public RotatioTask(Run callback, boolean client) {
            this.callback = callback;
            this.client = client;
        }

        public Run getCallback() {
            return callback;
        }

        public void setCallback(Run callback) {
            this.callback = callback;
        }

        public boolean isClient() {
            return client;
        }

        public void setClient(boolean client) {
            this.client = client;
        }
    }
}
