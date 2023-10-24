package io.github.projecthsi.mobarena.arena;

import org.bukkit.entity.Mob;

public class MobSpawnEntry {
    private Mob mob;

    private int amount;
    private int spawnRound;
    private int modulo;
    private String spawnPoint;

    public MobSpawnEntry(Mob mob, int amount, int spawnRound, int modulo, String spawnPoint) {
        this.mob = mob;
        this.amount = amount;
        this.spawnRound = spawnRound;
        this.modulo = modulo;
        this.spawnPoint = spawnPoint;
    }

    public Mob getMob() {
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
        return round < spawnRound;
    }

    public int getSpawnCount(int round) {
        if (!shouldSpawn(round)) return 0;

        int roundForMob = round - spawnRound;

        return roundForMob / modulo;
    }
}
