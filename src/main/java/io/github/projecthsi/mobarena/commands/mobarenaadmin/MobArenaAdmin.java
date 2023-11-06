package io.github.projecthsi.mobarena.commands.mobarenaadmin;

import io.github.projecthsi.mobarena.commands.CommandInteractions;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static io.github.projecthsi.mobarena.commands.mobarenaadmin.ArenaManagement.createArena;
import static io.github.projecthsi.mobarena.commands.mobarenaadmin.ArenaManagement.removeArena;
import static io.github.projecthsi.mobarena.commands.mobarenaadmin.SpawnPointManagement.addSpawnPoint;
import static io.github.projecthsi.mobarena.commands.mobarenaadmin.SpawnPointManagement.removeSpawnPoint;

public class MobArenaAdmin extends Command {
    public MobArenaAdmin() {
        //noinspection SpellCheckingInspection
        super("mobarenaadmin",
                "Mob Arena Admin",
                "mobarenaadmin",
                Arrays.asList("mobarenaa",
                        "maadmin",
                        "maa"));
    }

    private void showHelp(@NotNull CommandSender sender, @NotNull String commandLabel) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize("Help message here.\nI don't really have a help message right now."));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Player player = ( Player ) sender;

        //noinspection ConstantValue
        if (player == null) {
            CommandInteractions.displayError(sender, "This command must be run as a player.");

            return false;
        }

        if (args.length == 0) {
            showHelp(sender, commandLabel);
            return true;
        }

        switch (args[0]) {
            case "help":
                showHelp(sender, commandLabel);
                return true;

            case "createArena":
                return createArena(sender, commandLabel, args);

            case "removeArena":
                return removeArena(sender, commandLabel, args);

            case "addSpawnPoint":
                return addSpawnPoint(sender, commandLabel, args);

            case "removeSpawnPoint":
                return removeSpawnPoint(sender, commandLabel, args);

            default:
                CommandInteractions.displayError(sender, "Invalid subcommand.");
                showHelp(sender, commandLabel);
                return false;
        }
    }
}
