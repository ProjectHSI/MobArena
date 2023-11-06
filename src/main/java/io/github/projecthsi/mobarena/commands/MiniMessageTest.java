package io.github.projecthsi.mobarena.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MiniMessageTest extends Command {
    public MiniMessageTest() {
        //noinspection SpellCheckingInspection
        super("minimessagetest", "Mini Message Test", "minimessagetest", new ArrayList<>());
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<b>Hello!</b> " +
                "<i><gradient:#ff0000:#00ff00>This is a test</gradient> <gradient:#00ff00:#0000ff>of the MiniMessage format.</gradient></i> " +
                "<rainbow>Rainbow text is included <obf>here</obf>.</rainbow>"));

        return true;
    }
}
