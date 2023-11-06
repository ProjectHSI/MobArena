package io.github.projecthsi.mobarena.commands.mobarenaadmin;

import io.github.projecthsi.mobarena.commands.CommandInteractions;
import io.github.projecthsi.mobarena.FillArea;
import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.containers.Container.Containers.arenaContainer;
import io.github.projecthsi.mobarena.arena.SpawnPoint;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnPointManagement {
    static boolean addSpawnPoint(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 8 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must have 8 arguments.");
            CommandInteractions.displayUsage(sender, commandLabel + " addSpawnPoint <arena> <spawnPoint> <x1> <y1> <z1> <x2> <y2> <z2>");

            return false;
        }

        Player player = (Player) sender;

        String arenaName = args[1];
        String spawnPointName = args[2];

        float x1, y1, z1, x2, y2, z2;

        try {
            x1 = Float.parseFloat(args[3]);
            y1 = Float.parseFloat(args[4]);
            z1 = Float.parseFloat(args[5]);
            x2 = Float.parseFloat(args[6]);
            y2 = Float.parseFloat(args[7]);
            z2 = Float.parseFloat(args[8]);
        } catch (Exception e) {
            CommandInteractions.displayError(sender, "x1, y1, z1, x2, y2, and z2 must all be numbers.");
            return false;
        }

        Arena arenaInstance;

        try {
            arenaInstance = Container.Containers.arenaContainer.getInstance().getArena(arenaName);
        } catch (Exception e) {
            CommandInteractions.displayError(sender, "That arena does not exist.");

            return false;
        }

        if (arenaInstance.getSpawnPoints().containsKey(spawnPointName)) {
            CommandInteractions.displayError(sender, "That spawn point already exists.");
            return false;
        }

        Location location1 = new Location(player.getWorld(), x1, y1, z1);
        Location location2 = new Location(player.getWorld(), x2, y2, z2);
        FillArea fillArea = new FillArea(location1, location2, player.getWorld());
        SpawnPoint spawnPoint = new SpawnPoint(spawnPointName, fillArea);

        arenaInstance.getSpawnPoints().put(spawnPointName, spawnPoint);

        sender.sendMessage(MiniMessage.miniMessage().deserialize(String.format("Added spawn point %s to arena %s with coordinates of <yellow>X1 %f, Y1 %f, Z1 %f</yellow> | <cyan>X2 %f, Y2 %f, Z2 %f</cyan>", arenaName, spawnPointName, x1, y1, z1, x2, y2, z2)));

        return true;
    }

    static boolean removeSpawnPoint(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 2 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must have 2 arguments.");
            CommandInteractions.displayUsage(sender, commandLabel + " removeSpawnPoint <arena> <spawnPoint>");

            return false;
        }

        String arenaName = args[1];
        String spawnPointName = args[2];

        Arena arenaInstance;

        try {
            arenaInstance = Container.Containers.arenaContainer.getInstance().getArena(arenaName);
        } catch (Exception e) {
            CommandInteractions.displayError(sender, "That arena does not exist.");

            return false;
        }

        if (!arenaInstance.getSpawnPoints().containsKey(spawnPointName)) {
            CommandInteractions.displayError(sender, "That spawn point does not exist.");

            return false;
        }

        CommandInteractions.displayError(sender, "Removed spawn point " + spawnPointName + " from arena " + arenaName + ".");
        return true;
    }
}
