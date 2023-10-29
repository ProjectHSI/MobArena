package io.github.projecthsi.mobarena;

import io.github.projecthsi.mobarena.plugin.MobArena;

import static org.bukkit.Bukkit.getServer;

public class EventSystem implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getInstance().getLogger().info("player joined! " + event.getPlayer().getName());
public class EventSystem {
    public static void registerEvents() {
    }
}
