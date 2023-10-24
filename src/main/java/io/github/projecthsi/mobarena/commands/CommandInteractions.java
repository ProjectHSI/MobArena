package io.github.projecthsi.mobarena.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandInteractions {
    public static void displayError(@NotNull CommandSender sender, @NotNull String errorMessage) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize(String.format("<#ff0000><b>ERROR:</b></#ff0000> <red>%s</red>", errorMessage)));
    }

    public static void displayUsage(@NotNull CommandSender sender, @NotNull String errorMessage) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize(String.format("<#ffff55><b>USAGE:</b></#ffff55> %s", errorMessage)));
    }
}
