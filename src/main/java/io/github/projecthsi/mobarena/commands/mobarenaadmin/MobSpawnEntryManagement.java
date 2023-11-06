package io.github.projecthsi.mobarena.commands.mobarenaadmin;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.commands.CommandInteractions;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MobSpawnEntryManagement {
    static boolean createSpawnPoint(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 6 + 1) {
            CommandInteractions.displayError(sender, "This subcommand must have 3 arguments.");
            //noinspection SpellCheckingInspection
            CommandInteractions.displayUsage(sender, commandLabel + " createMobSpawnEntry <arena> <spawnpoint> <mobspawnentryname> <mob> <spawnwave> <spawnmodulo>");

            return false;
        }

        String arenaName = args[1];
        String spawnPointName = args[2];

        Arena arenaInstance = null;

        try {
            arenaInstance = Container.Containers.arenaContainer.getInstance().getArena(arenaName);
        } catch (Exception e) {
            CommandInteractions.displayError(sender, "That arena does not exist.");

            return false;
        }

        return true;
    }
}
