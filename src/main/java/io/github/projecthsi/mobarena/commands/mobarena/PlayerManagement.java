package io.github.projecthsi.mobarena.commands.mobarena;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.commands.CommandInteractions;
import io.github.projecthsi.mobarena.containers.ArenaContainer;
import io.github.projecthsi.mobarena.containers.PlayerContainer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerManagement {
    static boolean joinPlayer(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must have 1 argument.");
            CommandInteractions.displayUsage(sender, commandLabel + " joinArena <arena>");

            return false;
        }

        Player player = ( Player ) sender;

        if (player == null) {
            CommandInteractions.displayError(sender, "This command must be run as a player.");

            return false;
        }

        if (PlayerContainer.getTrackedPlayers().containsKey(player)) {
            CommandInteractions.displayError(sender, "You are already in an arena.");

            return false;
        }

        if (!ArenaContainer.getInstance().arenaExists(args[1])) {
            CommandInteractions.displayError(sender, "That arena does not exist.");

            return false;
        }

        Arena arena = ArenaContainer.getInstance().getArena(args[1]);
        ArrayList<Player> arenaPlayers = arena.getPlayers();

        arenaPlayers.add(player);

        arena.setPlayers(arenaPlayers);

        return true;
    }

    static boolean leavePlayer(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 0 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must be run with 0 arguments.");
            CommandInteractions.displayUsage(sender, commandLabel + " leaveArena");

            return false;
        }

        Player player = ( Player ) sender;

        if (player == null) {
            CommandInteractions.displayError(sender, "This command must be run as a player.");

            return false;
        }

        HashMap<Player, Arena> playersInContainer = PlayerContainer.getTrackedPlayers();

        if (!playersInContainer.containsKey(player)) {
            CommandInteractions.displayError(sender, "You are not in an arena.");
        }

        Arena arena = playersInContainer.get(player);

        ArrayList<Player> arenaPlayers = arena.getPlayers();
        arenaPlayers.remove(player);

        arena.setPlayers(arenaPlayers);

        return true;
    }
}
