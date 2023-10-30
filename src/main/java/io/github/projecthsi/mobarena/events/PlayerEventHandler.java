package io.github.projecthsi.mobarena.events;

import io.github.projecthsi.mobarena.containers.PlayerContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static io.github.projecthsi.mobarena.plugin.MobArena.getInstance;

public class PlayerEventHandler implements Listener
{
    public void onPlayerDeath(PlayerDeathEvent event) {
        getInstance().getLogger().info(String.format("Player death; %s", event.getPlayer().getName()));

        if (!PlayerContainer.getTrackedPlayers().containsKey(event.getPlayer())) {
            return;
        }

        PlayerContainer.getTrackedPlayers().get(event.getPlayer()).playerDeath(event.getPlayer());
    }
}
