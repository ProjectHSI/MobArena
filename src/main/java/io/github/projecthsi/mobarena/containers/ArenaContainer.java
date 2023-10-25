package io.github.projecthsi.mobarena.containers;

import io.github.projecthsi.mobarena.arena.Arena;
import org.jetbrains.annotations.NotNull;

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
        if (arenas.containsKey(arenaName)) {
            throw new Exception("Arena already exists.");
        }
        arenas.put(arenaName, arenaInstance);
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
