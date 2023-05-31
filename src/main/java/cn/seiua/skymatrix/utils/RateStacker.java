package cn.seiua.skymatrix.utils;

import java.util.Random;

public class RateStacker {
    private static final Random RANDOM = new Random();
    int target;

    int current;

    public RateStacker(int target) {
        this.target = target;
        this.current = current;
    }

    public void reset() {
        current = 0;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean tryTrigger() {
        double randomValue = RANDOM.nextDouble();
        System.out.println(current * 1.0d / target);
        System.out.println(randomValue);
        if (randomValue < current * 1.0d / target) {
            return true;
        } else {
            return false;
        }
    }

    public void append() {
        this.current++;
    }
}
