package io.github.projecthsi.mobarena.commands.mobarenadebug;

import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.arena.MobSpawnEntry;
import io.github.projecthsi.mobarena.containers.ArenaContainer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
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

    static boolean createDebugArena(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length != 0 + 1) {
            sender.sendMessage("mad error; must have no arguments");
            return false;
        }

        Arena arena = new Arena();

        // mob spawn entries
        MobSpawnEntry zombie = new MobSpawnEntry(EntityType.ZOMBIE, 1, 1, 1, "arena");

        return true;
    }
}
