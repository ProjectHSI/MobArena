package io.github.projecthsi.mobarena.events;

import io.github.projecthsi.mobarena.containers.EntityContainer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import static io.github.projecthsi.mobarena.plugin.MobArena.getInstance;

public class EntityEventHandler implements Listener {
    private void mobDeathEvent(Entity entity) {
        Mob mob = ( Mob ) entity;

        if (mob == null) {
            getInstance().getLogger().info(String.format("Non-mob Entity death; %s", entity.getName()));
            return;
        }

        getInstance().getLogger().info(String.format("Entity death; %s", entity.getName()));

        if (!EntityContainer.containsTrackedMob(mob)) {
            return;
        }

        getInstance().getLogger().info(String.format("Tracked mob; %s", entity.getName()));

        EntityContainer.getTrackedMobArena(mob).entityDeath(mob);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        mobDeathEvent(event.getEntity());
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        mobDeathEvent(event.getEntity());
    }
}
