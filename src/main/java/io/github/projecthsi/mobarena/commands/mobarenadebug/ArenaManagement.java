package io.github.projecthsi.mobarena.commands.mobarenadebug;

import io.github.projecthsi.mobarena.containers.ArenaContainer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ArenaManagement {
    static boolean forceStartArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 1 + 1) {
            sender.sendMessage("mad error; missing arguments");
            return false;
        }

        try {
            ArenaContainer.getInstance().getArena(args[0]).game();
            return true;
        } catch (Exception e) {
            sender.sendMessage("mad error; invalid arena");
            return false;
        }
    }
}
