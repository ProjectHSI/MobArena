package io.github.projecthsi.mobarena.commands.mobarena;

import io.github.projecthsi.mobarena.commands.CommandInteractions;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static io.github.projecthsi.mobarena.commands.mobarena.PlayerManagement.joinPlayer;
import static io.github.projecthsi.mobarena.commands.mobarena.PlayerManagement.leavePlayer;

public class MobArena extends Command {
    public MobArena() {
        super("mobarena",
                "Mob Arena",
                "mobarena",
                Arrays.asList(new String[] {
                        "mobarena",
                        "ma"
                }));
    }

    private void showHelp(@NotNull CommandSender sender, @NotNull String commandLabel) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize("Help message here.\nI don't really have a help message right now."));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        // sender.sendMessage(MiniMessage.miniMessage().deserialize("<b>Hello!</b> " +
        //         "<i><gradient:#ff0000:#00ff00>This is a test</gradient> <gradient:#00ff00:#0000ff>of the MiniMessage format.</gradient></i> " +
        //         "<rainbow>Rainbow text is included <obf>here</obf>.</rainbow>"));

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

            case "joinArena":
                return joinPlayer(sender, commandLabel, args);

            case "removeArena":
                return leavePlayer(sender, commandLabel, args);

            default:
                CommandInteractions.displayError(sender, "Invalid subcommand.");
                showHelp(sender, commandLabel);
                return false;
        }
    }
}
