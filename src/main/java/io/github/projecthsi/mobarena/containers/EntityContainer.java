package io.github.projecthsi.mobarena.containers;

import org.bukkit.entity.Mob;

import java.util.ArrayList;

public class EntityContainer {
    private static ArrayList<Mob> trackedMobs = new ArrayList<>();

    public static void setTrackedMobs(ArrayList<Mob> newTrackedMobs) {
        trackedMobs = newTrackedMobs;
    }

    public static ArrayList<Mob> getTrackedMobs() {
        return trackedMobs;
    }
}
