package io.github.projecthsi.mobarena.arena;

import io.github.projecthsi.mobarena.plugin.MobArena;
import org.bukkit.entity.EntityType;

public class MobSpawnEntry {
    private final EntityType mob;

    private final int amount;
    private final int spawnRound;
    private final int modulo;
    private final String spawnPoint;

    public MobSpawnEntry(EntityType mob, int amount, int spawnRound, int modulo, String spawnPoint) {
        this.mob = mob;
        this.amount = amount;
        this.spawnRound = spawnRound;
        this.modulo = modulo;
        this.spawnPoint = spawnPoint;
    }

    public EntityType getMob() {
        return mob;
    }

    public int getAmount() {
        return amount;
    }

    public int getSpawnRound() {
        return spawnRound;
    }

    public int getModulo() {
        return modulo;
    }

    public String getSpawnPoint() {
        return spawnPoint;
    }

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

        int modulo = (int) Math.floor((double) roundForMob / modulo);

        MobArena.getInstance().getLogger().info("Modulo: " + roundForMob + ".");

        return modulo;
    }
}
