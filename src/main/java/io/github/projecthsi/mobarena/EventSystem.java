package io.github.projecthsi.mobarena;

import io.github.projecthsi.mobarena.plugin.MobArena;

import static org.bukkit.Bukkit.getServer;

public class EventSystem {
    public static void registerEvents() {
        getServer().getPluginManager().registerEvents(new io.github.projecthsi.mobarena.events.EntityEventHandler(), MobArena.getInstance());
    }
}
