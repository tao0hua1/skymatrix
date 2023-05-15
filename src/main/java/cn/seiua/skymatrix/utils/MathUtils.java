package cn.seiua.skymatrix.utils;

public class MathUtils {

    public static double findClosest(double minValue, double maxValue, double step, double currentValue) {
        double closestValue = minValue;
        double minDistance = Math.abs(currentValue - closestValue);
        double currentValueMod = currentValue % step;


        for (double value = minValue; value <= maxValue; value += step) {
            double distance = Math.abs(currentValue - value);
            if (distance < minDistance) {
                closestValue = value;
                minDistance = distance;
            } else if (distance == minDistance) {
                double valueMod = value % step;
                if (valueMod < currentValueMod) {
                    closestValue = value;
                    currentValueMod = valueMod;
                }
            }
        }

        return closestValue;
    }

}
