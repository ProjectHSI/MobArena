package io.github.projecthsi.mobarena.events;

import io.github.projecthsi.mobarena.containers.Container;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static io.github.projecthsi.mobarena.plugin.MobArena.getInstance;

public class PlayerEventHandler implements Listener
{
    // we have a low priority here. this is to allow other plugins to have non-absolute god mode.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        // same reason here.
        if (!event.isCancelled()) return;

        getInstance().getLogger().info(String.format("Player death; %s", event.getPlayer().getName()));

        if (!Container.Containers.playerContainer.containsTracked(event.getPlayer())) {
            return;
        }

        Container.Containers.playerContainer.getTracked(event.getPlayer()).playerDeath(event.getPlayer());

        // we don't want to kill the player, so we cancel it here.
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (!Container.Containers.playerContainer.containsTracked(event.getPlayer())) {
            return;
        }

        Container.Containers.playerContainer.getTracked(event.getPlayer()).playerQuit(event.getPlayer());
    }

    // Nrgh... Cheating is bad, don't do it.
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (!Container.Containers.playerContainer.containsTracked(event.getPlayer()) || !Container.Containers.playerContainer.getTracked(event.getPlayer()).isGameInProgress()) {
            return;
        }

        event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<red>That command has been disabled.</red>"));

        event.setCancelled(true);
    }
}
