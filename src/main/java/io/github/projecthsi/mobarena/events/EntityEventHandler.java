package io.github.projecthsi.mobarena.events;

import io.github.projecthsi.mobarena.containers.EntityContainer;
import io.github.projecthsi.mobarena.containers.PlayerContainer;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static io.github.projecthsi.mobarena.plugin.MobArena.getInstance;

public class EntityEventHandler implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Mob mob = ( Mob ) event.getEntity();

        if (mob == null) {
            return;
        }

        getInstance().getLogger().info(String.format("Entity death; %s", event.getEntity().getName()));

        if (!PlayerContainer.getTrackedPlayers().containsKey(mob)) {
            return;
        }

        EntityContainer.getTrackedMobs().get(mob).entityDeath(mob);
    }
}
