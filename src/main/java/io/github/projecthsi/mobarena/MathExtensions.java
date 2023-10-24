package io.github.projecthsi.mobarena;

import java.util.Random;

public class MathExtensions {
    private static Random random = new Random();

    public static double getRandomNumberWithinRange(double min, double max) {
        if (min > max) {
            double oldMin = min;
            double oldMax = max;
            min = oldMax;
            max = oldMin;
        }

        return (random.nextDouble() * max) + min;
    }

    public static Random getRandom() {
        return random;
    }
}
