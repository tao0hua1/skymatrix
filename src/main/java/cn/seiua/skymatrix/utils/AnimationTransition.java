package cn.seiua.skymatrix.utils;

public class AnimationTransition {

    private long startTime;


    public AnimationTransition() {
        startTime = System.currentTimeMillis();
    }


    public double getPercentage(long currentTime) {

        long elapsedTime = currentTime - startTime;

        if (elapsedTime >= 1000) {
            return 1;
        } else {
            return (double) elapsedTime / 1000.0;
        }
    }
}