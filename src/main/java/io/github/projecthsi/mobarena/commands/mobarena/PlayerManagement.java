package io.github.projecthsi.mobarena.commands.mobarena;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.commands.CommandInteractions;
import io.github.projecthsi.mobarena.containers.Container;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlayerManagement {
    static boolean joinPlayer(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must have 1 argument.");
            CommandInteractions.displayUsage(sender, commandLabel + " joinArena <arena>");

            return false;
        }

        Player player = ( Player ) sender;

        //noinspection ConstantValue
        if (player == null) {
            CommandInteractions.displayError(sender, "This command must be run as a player.");

            return false;
        }

        if (Container.Containers.playerContainer.containsTracked(player)) {
            CommandInteractions.displayError(sender, "You are already in an arena.");

            return false;
        }

        if (!Container.Containers.arenaContainer.containsTracked(args[1])) {
            CommandInteractions.displayError(sender, "That arena does not exist.");

            return false;
        }

        Arena arena = Container.Containers.arenaContainer.getTracked(args[1]);
        ArrayList<Player> arenaPlayers = arena.getPlayers();

        arenaPlayers.add(player);

        arena.setPlayers(arenaPlayers);

        Container.Containers.playerContainer.addTracked(player, arena);

        sender.sendMessage(MiniMessage.miniMessage().deserialize("You have joined <bold>" + arena.getName() + "</bold>"));

        return true;
    }

    static boolean leavePlayer(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1) {
            CommandInteractions.displayError(sender, "This subcommand must be run with 0 arguments.");
            CommandInteractions.displayUsage(sender, commandLabel + " leaveArena");

            return false;
        }

        Player player = ( Player ) sender;

        //noinspection ConstantValue
        if (player == null) {
            CommandInteractions.displayError(sender, "This command must be run as a player.");

            return false;
        }

        if (!Container.Containers.playerContainer.containsTracked(player)) {
            CommandInteractions.displayError(sender, "You are not in an arena.");
        }

        Container.Containers.playerContainer.getTracked(player).playerQuit(player);

        sender.sendMessage(MiniMessage.miniMessage().deserialize("You have left <bold>" + Container.Containers.playerContainer.getTracked(player).getName() + "</bold>"));

        return true;
    }
}
