package io.github.projecthsi.mobarena;

import io.github.projecthsi.mobarena.plugin.MobArena;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigSystem {
    private final FileConfiguration config = MobArena.getInstance().getConfig();
}
