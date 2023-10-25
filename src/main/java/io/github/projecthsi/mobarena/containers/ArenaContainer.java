package io.github.projecthsi.mobarena.containers;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.plugin.MobArena;

import java.util.HashMap;

public class ArenaContainer {
    private final HashMap<String, Arena> arenas = new HashMap<>();

    private static final ArenaContainer Instance = new ArenaContainer();

    public static ArenaContainer getInstance() {
        return Instance;
    }

    public boolean arenaExists(String arenaName) {
        return arenas.containsKey(arenaName);
    }

    public void addArena(String arenaName, Arena arenaInstance) throws Exception {
        MobArena.getInstance().getLogger().info("Checkpoint 1 for AddArena");
        if (arenas.containsKey(arenaName)) {
            throw new Exception("Arena already exists.");
        }
        MobArena.getInstance().getLogger().info("Checkpoint 2 for AddArena");
        arenas.put(arenaName, arenaInstance);
        MobArena.getInstance().getLogger().info("Checkpoint 3 for AddArena");
    }

    public Arena getArena(String arenaName) {
        return arenas.get(arenaName);
    }

    public void removeArena(String arenaName) throws Exception {
        if (arenas.containsKey(arenaName)) {
            throw new Exception("Arena does not exist.");
        }
        arenas.put(arenaName, null);
    }
}
