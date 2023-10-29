package io.github.projecthsi.mobarena.containers;

import io.github.projecthsi.mobarena.arena.Arena;
import org.bukkit.entity.Mob;

import java.util.HashMap;

public class EntityContainer {
    private static HashMap<Mob, Arena> trackedMobs = new HashMap<>();

    public static void setTrackedMobs(HashMap<Mob, Arena> newTrackedMobs) {
        trackedMobs = newTrackedMobs;
    }

    public static HashMap<Mob, Arena> getTrackedMobs() {
        return trackedMobs;
    }
}
