package io.github.projecthsi.mobarena;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static io.github.projecthsi.mobarena.plugin.MobArena.getInstance;

public class EventSystem implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getInstance().getLogger().info("player joined! " + event.getPlayer().getName());
    }
}
