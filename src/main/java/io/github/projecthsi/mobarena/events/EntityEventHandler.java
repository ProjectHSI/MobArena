package io.github.projecthsi.mobarena.events;

import io.github.projecthsi.mobarena.containers.Container;
import io.github.projecthsi.mobarena.plugin.MobArena;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static io.github.projecthsi.mobarena.plugin.MobArena.getInstance;

public class EntityEventHandler implements Listener {
    private void mobDeathEvent(@NotNull LivingEntity entity) {
        Mob mob = (Mob) entity;

        getInstance().getLogger().info(String.format("Entity death; %s", entity.getName()));

        if (!Container.Containers.mobContainer.containsTracked(mob)) {
            return;
        }

        getInstance().getLogger().info(String.format("Tracked mob; %s", entity.getName()));

        Container.Containers.mobContainer.getTracked(mob).entityDeath(mob);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        mobDeathEvent(event.getEntity());
    }

    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return; mobDeathEvent((LivingEntity) event.getEntity());
    }

    @EventHandler
    public void onArrowLandEvent(ProjectileHitEvent event) {
        event.getEntity().getScheduler().execute(MobArena.getInstance(), () -> {
            getInstance().getLogger().info(String.format("ProjectileSource Entity Type; %s", Objects.requireNonNull(event.getEntity().getShooter()).getClass().getName()));

            if (event.getEntity().getShooter() instanceof Entity) {
                getInstance().getLogger().info(String.format("ProjectileSource Entity; %s", ((Entity) event.getEntity().getShooter()).getName()));

                if (event.getEntity().getShooter() instanceof Mob) {
                    getInstance().getLogger().info(String.format("ProjectileSource Mob in EntityContainer; %s", Container.Containers.mobContainer.containsTracked((Mob) event.getEntity().getShooter())));
                }
            }

            event.getEntity().remove();
        }, null, 0);
    }
}
