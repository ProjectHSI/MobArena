package io.github.projecthsi.mobarena;

import io.github.projecthsi.mobarena.plugin.MobArena;

import java.util.Random;

public class MathExtensions {
    private static final Random random = new Random();

    public static double getRandomNumberWithinRange(double min, double max) {
        MobArena.getInstance().getLogger().info("min: " + min);
        MobArena.getInstance().getLogger().info("max: " + max);

        if (min < max) {
            MobArena.getInstance().getLogger().info("inverting numbers");

            // code may be confusing, IntelliJ recommended to inline the old oldMax variable.
            double oldMin = min;
            min = max;
            max = oldMin;

            MobArena.getInstance().getLogger().info("new min: " + min);
            MobArena.getInstance().getLogger().info("new max: " + max);
        }

        double number = min + (max - min) * random.nextDouble();

        MobArena.getInstance().getLogger().info("rng: " + number);

        return number;
    }

    public static Random getRandom() {
        return random;
    }
}
