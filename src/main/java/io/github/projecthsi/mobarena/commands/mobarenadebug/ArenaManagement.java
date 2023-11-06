package io.github.projecthsi.mobarena.commands.mobarenadebug;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.arena.MobSpawnEntry;
import io.github.projecthsi.mobarena.arena.SpawnPoint;
import io.github.projecthsi.mobarena.containers.Container;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ArenaManagement {
    static boolean forceStartArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            sender.sendMessage("mad error; missing arguments");
            return false;
        }

        if (Container.Containers.arenaContainer.containsTracked(args[1])) {
            sender.sendMessage("arena doesn't exist.");
            return false;
        }

        //try {
            Container.Containers.arenaContainer.getTracked(args[1]).startGame();
            return true;
        //} catch (Exception e) {
        //    sender.sendMessage("mad error; invalid arena");
        //    return false;
        //}
    }

    static boolean createDebugArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage("mad error; must have no arguments");
            return false;
        }

        Arena arena = new Arena();

        // mob spawn entries
        MobSpawnEntry[] mobSpawnEntries = getMobSpawnEntries();

        // spawn points

        // arena
        // pos1: 20 -60 -20
        // pos2: -20 -60 20
        SpawnPoint arenaSpawnPoint = new SpawnPoint(
                "arena",
                new Location(
                        Bukkit.getServer().getWorld("mob_arena"),
                        20, -60, -20
                ),
                new Location(
                        Bukkit.getServer().getWorld("mob_arena"),
                        -20, -60, 20
                ),
                Bukkit.getServer().getWorld("mob_arena")
        );

        // tower
        // pos1: 4 -53 -4
        // pos2: -4 -53 4
        SpawnPoint towerSpawnPoint = new SpawnPoint(
                "tower",
                new Location(
                        Bukkit.getServer().getWorld("mob_arena"),
                        4, -53, -4
                ),
                new Location(
                        Bukkit.getServer().getWorld("mob_arena"),
                        -4, -53, 4
                ),
                Bukkit.getServer().getWorld("mob_arena")
        );

        HashMap<String, SpawnPoint> spawnPoints = new HashMap<>();
        spawnPoints.put("arena", arenaSpawnPoint);
        spawnPoints.put("tower", towerSpawnPoint);

        arena.setMobSpawnEntries(mobSpawnEntries);
        arena.setSpawnPoints(spawnPoints);
        arena.setLobbySpawnLocation(new Location(
                Bukkit.getServer().getWorld("mob_arena"),
                0, -49, -24

        ));

        try {
            Container.Containers.arenaContainer.addTracked("test_arena", arena);
        } catch (Exception e) {
            sender.sendMessage("failed to create test arena for some reason.");
            return false;
        }

        if (Container.Containers.arenaContainer.containsTracked("test_arena")) {
            sender.sendMessage("failed to create test arena for some reason.");
            return false;
        }

        sender.sendMessage("created test arena.");
        return true;
    }

    @NotNull
    private static MobSpawnEntry[] getMobSpawnEntries() {
        MobSpawnEntry zombie = new MobSpawnEntry(EntityType.ZOMBIE, 1, 1, 1, "arena");
        MobSpawnEntry creeper = new MobSpawnEntry(EntityType.CREEPER, 1, 5, 1, "arena");
        MobSpawnEntry towerSkeleton = new MobSpawnEntry(EntityType.SKELETON, 1, 8, 2, "tower");
        MobSpawnEntry arenaSkeleton = new MobSpawnEntry(EntityType.SKELETON, 1, 10, 5, "arena");
        MobSpawnEntry blaze = new MobSpawnEntry(EntityType.BLAZE, 1, 15, 1, "arena");
        MobSpawnEntry warden = new MobSpawnEntry(EntityType.WARDEN, 1, 50, 5, "arena");
        MobSpawnEntry wither = new MobSpawnEntry(EntityType.WITHER, 1, 100, 1, "arena");

        return new MobSpawnEntry[]{ zombie, creeper, towerSkeleton, arenaSkeleton, blaze, warden, wither };
    }
}
