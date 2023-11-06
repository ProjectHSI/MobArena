package io.github.projecthsi.mobarena.containers;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.plugin.MobArena;
import org.bukkit.entity.Mob;

import java.util.HashMap;

@Deprecated
public class EntityContainer {
    private static HashMap<Mob, Arena> trackedMobs = new HashMap<>();

    /**
     * @deprecated Usage of this method is not recommended due to thread safety reasons. Use the indirect methods.
     */
    @Deprecated
    public static void setTrackedMobs(HashMap<Mob, Arena> newTrackedMobs) {
        trackedMobs = newTrackedMobs;
    }

    public static void addTrackedMob(Mob mob, Arena arena) {
        MobArena.getInstance().getServer().getGlobalRegionScheduler().execute(MobArena.getInstance(), () -> trackedMobs.put(mob, arena));
    }

    public static void removeTrackedMob(Mob mob) {
        MobArena.getInstance().getServer().getGlobalRegionScheduler().execute(MobArena.getInstance(), () -> trackedMobs.remove(mob));
    }

    public static Arena getTrackedMobArena(Mob mob) {
        return trackedMobs.get(mob);
    }

    public static boolean containsTrackedMob(Mob mob) {
        return trackedMobs.containsKey(mob);
    }

    /**
     * @deprecated Usage of this method is not recommended due to thread safety reasons. Use the indirect methods.
     */
    @Deprecated
    public static HashMap<Mob, Arena> getTrackedMobs() {
        return trackedMobs;
    }
}
