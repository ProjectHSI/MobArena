package io.github.projecthsi.mobarena.containers;

import io.github.projecthsi.mobarena.arena.Arena;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Deprecated
public class PlayerContainer {
    private static HashMap<Player, Arena> trackedPlayers = new HashMap<>();

    public static void setTrackedPlayers(HashMap<Player, Arena> newTrackedPlayers) {
        trackedPlayers = newTrackedPlayers;
    }

    public static HashMap<Player, Arena> getTrackedPlayers() {
        return trackedPlayers;
    }
}
