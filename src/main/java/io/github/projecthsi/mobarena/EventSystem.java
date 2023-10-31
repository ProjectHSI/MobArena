package io.github.projecthsi.mobarena;

import io.github.projecthsi.mobarena.events.EntityEventHandler;
import io.github.projecthsi.mobarena.events.PlayerEventHandler;
import io.github.projecthsi.mobarena.plugin.MobArena;

import static org.bukkit.Bukkit.getServer;

public class EventSystem {
    public static void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(), MobArena.getInstance());
        getServer().getPluginManager().registerEvents(new EntityEventHandler(), MobArena.getInstance());
    }
}
