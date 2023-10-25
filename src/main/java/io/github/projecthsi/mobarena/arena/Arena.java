package io.github.projecthsi.mobarena.arena;

import io.github.projecthsi.mobarena.FillArea;
import io.github.projecthsi.mobarena.MathExtensions;
import io.github.projecthsi.mobarena.containers.EntityContainer;
import io.github.projecthsi.mobarena.containers.PlayerContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Arena {
    private String name = "";
    private MobSpawnEntry[] mobSpawnEntries;
    private HashMap<String, SpawnPoint> spawnPoints;
    private ArrayList<Player> trackedPlayers = new ArrayList<>();
    private ArrayList<Mob> trackedMobs = new ArrayList<>();

    private boolean gameInProgress = false;

    //<editor-fold desc="Game Controllers">
    private Location getRandomPositionFromFillArea(FillArea fillArea) {
        return new Location(
                fillArea.getWorld(),
                MathExtensions.getRandomNumberWithinRange(fillArea.getPos1().x(), fillArea.getPos2().x()),
                MathExtensions.getRandomNumberWithinRange(fillArea.getPos1().y(), fillArea.getPos2().y()),
                MathExtensions.getRandomNumberWithinRange(fillArea.getPos1().z(), fillArea.getPos2().z())
        );
    }

    private void chatToAllPlayers(Component component) {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.sendMessage(component);
        }
    }

    private void actionBarToAllPlayers(Component component) {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.sendActionBar(component);
        }
    }

    private void titleToAllPlayers(Component titleComponent, Component subtitleComponent) {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.showTitle(Title.title(titleComponent, subtitleComponent));
        }
    }

    private boolean continueGame;
    private int currentRound;

    public void game() {
        gameInProgress = true;
        continueGame = true;
        currentRound = 1;
        teleportPlayers();
        gameInProgress = false;
    }

    private void wave() {
        spawnMobs(currentRound);
    }

    private void spawnMobs(int wave) {
        for (MobSpawnEntry mobSpawnEntry : mobSpawnEntries) {
            if (!mobSpawnEntry.shouldSpawn(wave)) continue;

            SpawnPoint spawnPoint = spawnPoints.get(mobSpawnEntry.getSpawnPoint());
            World spawnWorld = spawnPoint.getFillArea().getWorld();

            for (int i = 0; i < mobSpawnEntry.getSpawnCount(wave); i++) {
                Entity newEntity = spawnWorld.spawnEntity(getRandomPositionFromFillArea(spawnPoint.getFillArea()), mobSpawnEntry.getMob());
                trackedMobs.add((Mob) newEntity);
                EntityContainer.getTrackedMobs().add((Mob) newEntity);
            }
        }
    }

    private void teleportPlayers() {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.teleport(getRandomPositionFromFillArea(spawnPoints.get("arena").getFillArea()));
            PlayerContainer.getTrackedPlayers().add(trackedPlayer);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Checks">
    public void mobSpawnEntryCheck() throws Exception {
        for (MobSpawnEntry mobSpawnEntry : mobSpawnEntries) {
            boolean mobSpawnEntryIsOk = false;

            for (String spawnPoint : spawnPoints.keySet()) {
                if (Objects.equals(spawnPoint, mobSpawnEntry.getSpawnPoint())) {
                    mobSpawnEntryIsOk = true;
                    break;
                }
            }

            if (!mobSpawnEntryIsOk) throw new Exception("All mob spawn entries must have a valid spawn point.");
        }
    }

    public void spawnPointsCheck() throws Exception {
        if (spawnPoints.containsKey("arena")) {
            throw new Exception("Arena must contain the 'arena' spawn point.");
        }
        // no checks (yet)
    }

    public void initialCheck() {
        try {
            mobSpawnEntryCheck();
            spawnPointsCheck();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //</editor-fold>

    //<editor-fold>
    public String getName() {
        return name;
    }

    public MobSpawnEntry[] getMobSpawnEntries() {
        return mobSpawnEntries;
    }

    public HashMap<String, SpawnPoint> getSpawnPoints() {
        return spawnPoints;
    }

    public ArrayList<Player> getPlayers() {
        return trackedPlayers;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobSpawnEntries(MobSpawnEntry[] mobSpawnEntries) {
        this.mobSpawnEntries = mobSpawnEntries;
    }

    public void setSpawnPoints(HashMap<String, SpawnPoint> spawnPoints) {
        this.spawnPoints = spawnPoints;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.trackedPlayers = players;
    }
    //</editor-fold>
}
