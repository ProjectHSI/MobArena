package io.github.projecthsi.mobarena.commands.mobarenadebug;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.arena.MobSpawnEntry;
import io.github.projecthsi.mobarena.arena.SpawnPoint;
import io.github.projecthsi.mobarena.containers.ArenaContainer;
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

        if (!ArenaContainer.getInstance().arenaExists(args[1])) {
            sender.sendMessage("arena doesn't exist.");
            return false;
        }

        //try {
            ArenaContainer.getInstance().getArena(args[1]).startGame();
            return true;
        //} catch (Exception e) {
        //    sender.sendMessage("mad error; invalid arena");
        //    return false;
        //}
    }

    static boolean createDebugArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 0 + 1) {
            sender.sendMessage("mad error; must have no arguments");
            return false;
        }

        Arena arena = new Arena();

        // mob spawn entries
        MobSpawnEntry zombie = new MobSpawnEntry(EntityType.ZOMBIE, 1, 1, 1, "arena");
        MobSpawnEntry creeper = new MobSpawnEntry(EntityType.CREEPER, 1, 5, 1, "arena");
        MobSpawnEntry towerSkeleton = new MobSpawnEntry(EntityType.SKELETON, 1, 8, 2, "tower");
        MobSpawnEntry arenaSkeleton = new MobSpawnEntry(EntityType.SKELETON, 1, 10, 5, "arena");

        MobSpawnEntry[] mobSpawnEntries = { zombie, creeper, towerSkeleton, arenaSkeleton };

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
                        -4, -60, 4
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
            ArenaContainer.getInstance().addArena("test_arena", arena);
        } catch (Exception e) {
            sender.sendMessage("failed to create test arena for some reason.");
            return false;
        }

        if (!ArenaContainer.getInstance().arenaExists("test_arena")) {
            sender.sendMessage("failed to create test arena for some reason.");
            return false;
        }

        sender.sendMessage("created test arena.");
        return true;
    }
}
