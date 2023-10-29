package io.github.projecthsi.mobarena.plugin;

import io.github.projecthsi.mobarena.EventSystem;
import io.github.projecthsi.mobarena.commands.MiniMessageTest;
import io.github.projecthsi.mobarena.commands.mobarenaadmin.MobArenaAdmin;
import io.github.projecthsi.mobarena.commands.mobarenadebug.MobArenaDebug;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobArena extends JavaPlugin {
    private static MobArena Instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getLogger().info("Test");
        Instance = this;

        getServer().getCommandMap().register("MiniMessageTest", ":", new MiniMessageTest());
        getServer().getCommandMap().register("MobArena", ":", new io.github.projecthsi.mobarena.commands.mobarena.MobArena());
        getServer().getCommandMap().register("MobArenaAdmin", ":", new MobArenaAdmin());
        getServer().getCommandMap().register("MobArenaDebug", ":", new MobArenaDebug());
        EventSystem.registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MobArena getInstance() {
        return Instance;
    }
}
