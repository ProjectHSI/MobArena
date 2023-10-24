package io.github.projecthsi.mobarena.commands.mobarenaadmin;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.containers.ArenaContainer;
import io.github.projecthsi.mobarena.commands.CommandInteractions;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ArenaManagement {
    static boolean createArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must have 1 argument.");
            CommandInteractions.displayUsage(sender, commandLabel + " createArena <arena>");

            return false;
        }

        String arenaName = args[1];

        try {
            ArenaContainer.getInstance().addArena(arenaName, new Arena());
        } catch (Exception e) {
            CommandInteractions.displayError(sender, "That arena already exists.");
            return false;
        }

        sender.sendMessage(MiniMessage.miniMessage().deserialize(String.format("Created arena %s.", arenaName)));

        return true;
    }

    static boolean removeArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must have 1 argument.");
            CommandInteractions.displayUsage(sender, commandLabel + " removeArena <arena>");

            return false;
        }

        String arenaName = args[1];

        try {
            ArenaContainer.getInstance().removeArena(arenaName);
        } catch (Exception e) {
            CommandInteractions.displayError(sender, "That arena does not exist.");

            return false;
        }

        CommandInteractions.displayError(sender, "Removed arena " + arenaName + ".");
        return true;
    }
}
