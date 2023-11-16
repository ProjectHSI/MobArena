package io.github.projecthsi.mobarena.commands.mobarenadebug;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.arena.MobSpawnEntry;
import io.github.projecthsi.mobarena.arena.SpawnPoint;
import io.github.projecthsi.mobarena.containers.Container;
import io.github.projecthsi.mobarena.plugin.MobArena;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class ArenaManagement {
    static boolean forceStartArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            sender.sendMessage("mad error; missing arguments");
            return false;
        }

        if (!Container.Containers.arenaContainer.containsTracked(args[1])) {
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
            sender.sendMessage("failed to create test arena for some reason (exception).");
            e.printStackTrace();
            return false;
        }

        if (!Container.Containers.arenaContainer.containsTracked("test_arena")) {
            sender.sendMessage("failed to create test arena for some reason (not exist).");
            return false;
        } else {
            sender.sendMessage("arena exists.");
        }

        sender.sendMessage("created test arena.");
        return true;
    }

    @NotNull
    private static MobSpawnEntry[] getMobSpawnEntries() {
        MobSpawnEntry zombie = new MobSpawnEntry(EntityType.ZOMBIE, 1, 1, 1, "arena", true);
        MobSpawnEntry creeper = new MobSpawnEntry(EntityType.CREEPER, 1, 5, 1, "arena", true);
        MobSpawnEntry towerSkeleton = new MobSpawnEntry(EntityType.SKELETON, 1, 8, 2, "tower", true);
        MobSpawnEntry arenaSkeleton = new MobSpawnEntry(EntityType.SKELETON, 1, 10, 5, "arena", true);
        MobSpawnEntry blaze = new MobSpawnEntry(EntityType.BLAZE, 1, 15, 4, "arena", true);
        MobSpawnEntry warden = new MobSpawnEntry(EntityType.WARDEN, 1, 50, 5, "arena", true);
        MobSpawnEntry wither = new MobSpawnEntry(EntityType.WITHER, 100, 100, 1, "arena", true);
        MobSpawnEntry iron_golem = new MobSpawnEntry(EntityType.IRON_GOLEM, 10, 20, 10, "arena", false);

        return new MobSpawnEntry[]{ zombie, creeper, towerSkeleton, arenaSkeleton, blaze, warden, wither/*, iron_golem*/ };
    }

    static boolean probeMobs(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            sender.sendMessage("mad error; must have 1 argument");
            return false;
        }

        if (!Container.Containers.arenaContainer.containsTracked(args[1])) {
            sender.sendMessage("that arena doesn't exist.");
            return false;
        }

        ArrayList<Mob> mobArray = Container.Containers.arenaContainer.getTracked(args[1]).getTrackedMobs();

        Logger logger = MobArena.getInstance().getLogger();

        logger.info(ANSIComponentSerializer.ansi().serialize(MiniMessage.miniMessage().deserialize("<aqua>--- <yellow>MobArena</yellow> Arena Mobs Diagnosis ---</aqua>")));

        for (int mobIndex = 0; mobIndex < mobArray.size(); mobIndex++) {
            Mob mob = mobArray.get(mobIndex);

            logger.info(ANSIComponentSerializer.ansi().serialize(MiniMessage.miniMessage().deserialize(
                    "<red>-- <yellow><mob-index></yellow> Arena Mobs Diagnosis --</red>",
                    Formatter.number("mob-index", mobIndex))));

            printKV("Name", mob.getName(), 0);
            printKV("Type", mob.getType().name(), 0);
            printKV("isDead", String.valueOf(mob.isDead()), 0);

            Location location = mob.getLocation();

            printKV("Location", "", 0);
            printKV("World", location.getWorld().getName(), 1);
            printKV("X", String.valueOf(location.getX()), 1);
            printKV("Y", String.valueOf(location.getY()), 1);
            printKV("Z", String.valueOf(location.getZ()), 1);
            printKV("Yaw", String.valueOf(location.getYaw()), 1);

            logger.info(ANSIComponentSerializer.ansi().serialize(MiniMessage.miniMessage().deserialize(
                    "<red>-- <yellow><mob-index></yellow> Arena Mobs Diagnosis --</red>",
                    Formatter.number("mob-index", mobIndex))));
        }

        logger.info(ANSIComponentSerializer.ansi().serialize(MiniMessage.miniMessage().deserialize("<aqua>--- <yellow>MobArena</yellow> Arena Mobs Diagnosis ---</aqua>")));

        return true;
    }

    private static void printKV(String key, String value, int tabs) {
        String tabsString = "\t".repeat(tabs);

        MobArena.getInstance().getLogger().info(ANSIComponentSerializer.ansi().serialize(MiniMessage.miniMessage().deserialize(
                "<tabs><red>- <yellow><key>:</yellow> <bold><green><value></green></bold> --</red>",
                Placeholder.unparsed("key", key),
                Placeholder.unparsed("value", value),
                Placeholder.unparsed("tabs", tabsString))));
    }
}
