package io.github.projecthsi.mobarena.containers;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerContainer {
    private static ArrayList<Player> trackedPlayers = new ArrayList<>();

    public static void setTrackedPlayers(ArrayList<Player> newTrackedPlayers) {
        trackedPlayers = newTrackedPlayers;
    }

    public static ArrayList<Player> getTrackedPlayers() {
        return trackedPlayers;
    }
}
