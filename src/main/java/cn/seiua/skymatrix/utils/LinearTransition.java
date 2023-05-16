package cn.seiua.skymatrix.utils;

public class LinearTransition {

    private long startTime;

    private long i;

    public LinearTransition(long i) {
        startTime = System.currentTimeMillis();
        this.i = i;
    }


    public double getPercentage(long currentTime) {

        long elapsedTime = currentTime - startTime;

        if (elapsedTime >= i) {
            return 1;
        } else {
            return (double) elapsedTime / i;
        }
    }
}