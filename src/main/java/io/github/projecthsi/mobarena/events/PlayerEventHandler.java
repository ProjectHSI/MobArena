package io.github.projecthsi.mobarena.events;

import io.github.projecthsi.mobarena.containers.PlayerContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static io.github.projecthsi.mobarena.plugin.MobArena.getInstance;

public class PlayerEventHandler implements Listener
{
    // we have a low priority here. this is to allow other plugins to have non-absolute god mode.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        getInstance().getLogger().info(String.format("Player death; %s", event.getPlayer().getName()));

        if (!PlayerContainer.getTrackedPlayers().containsKey(event.getPlayer())) {
            return;
        }

        PlayerContainer.getTrackedPlayers().get(event.getPlayer()).playerDeath(event.getPlayer());
    }
}
