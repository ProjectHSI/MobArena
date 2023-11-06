package io.github.projecthsi.mobarena.arena;

import io.github.projecthsi.mobarena.plugin.MobArena;
import org.bukkit.entity.EntityType;

public record MobSpawnEntry(EntityType mob, int amount, int spawnRound, int modulo, String spawnPoint) {

    public boolean shouldSpawn(int round) {
        MobArena.getInstance().getLogger().info("ShouldSpawn: " + (round >= spawnRound));
        return round < spawnRound;
    }

    public int getSpawnCount(int round) {
        MobArena.getInstance().getLogger().info("Calculations started for MobSpawnEntry.");

        if (shouldSpawn(round)) {
            MobArena.getInstance().getLogger().info("Aborting. Should not spawn.");
            return 0;
        }

        MobArena.getInstance().getLogger().info("We're OK to spawn.");

        int roundForMob = round - (spawnRound - 1);

        MobArena.getInstance().getLogger().info("roundForMob: " + roundForMob + ".");

        int moduloNumber = (int) Math.floor((double) roundForMob / modulo);

        MobArena.getInstance().getLogger().info("Modulo: " + moduloNumber + ".");

        return moduloNumber;
    }
}
