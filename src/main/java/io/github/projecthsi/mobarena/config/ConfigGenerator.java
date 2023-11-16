package io.github.projecthsi.mobarena.config;

import io.github.projecthsi.mobarena.FillArea;
import io.github.projecthsi.mobarena.arena.Arena;
import io.github.projecthsi.mobarena.arena.MobSpawnEntry;
import io.github.projecthsi.mobarena.arena.SpawnPoint;
import io.github.projecthsi.mobarena.containers.Container;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ConfigGenerator {
    private static class ProtobufGenerator {

    }

    private static class EncapsulationGenerator {
        // Defined here, as there really isn't an existing encapsulated "Configuration" object.
        private class EncapsulatedConfigurationObjects {
            // mc.proto
            private enum EntityType {
                ALLAY,
                AREA_EFFECT_CLOUD,
                ARMOR_STAND,
                ARROW,
                AXOLOTL,
                BAT,
                BEE,
                BLAZE,
                BLOCK_DISPLAY,
                BOAT,
                CAMEL,
                CAT,
                CAVE_SPIDER,
                CHEST_BOAT,
                CHICKEN,
                COD,
                COW,
                CREEPER,
                DOLPHIN,
                DONKEY,
                DRAGON_FIREBALL,
                DROPPED_ITEM,
                DROWNED,
                EGG,
                ELDER_GUARDIAN,
                ENDER_CRYSTAL,
                ENDER_DRAGON,
                ENDER_PEARL,
                ENDER_SIGNAL,
                ENDERMAN,
                ENDERMITE,
                EVOKER,
                EVOKER_FANGS,
                EXPERIENCE_ORB,
                FALLING_BLOCK,
                FIREBALL,
                FIREWORK,
                FISHING_HOOK,
                FOX,
                FROG,
                GHAST,
                GIANT,
                GLOW_ITEM_FRAME,
                GLOW_SQUID,
                GOAT,
                GUARDIAN,
                HOGLIN,
                HORSE,
                HUSK,
                ILLUSIONER,
                INTERACTION,
                IRON_GOLEM,
                ITEM_FRAME,
                LEASH_HITCH,
                LIGHTNING,
                LLAMA,
                LLAMA_SPUT,
                MAGMA_CUBE,
                MARKER,
                MINECART,
                MINECART_CHEST,
                MINECRAFT_COMMAND,
                MINECRAFT_FURNACE,
                MINECART_HOPPER,
                MINECART_MOB_SPAWNER,
                MINECART_TNT,
                MULE,
                MUSHROOM_COW,
                OCELOT,
                PAINTING,
                PANDA,
                PARROT,
                PHANTOM,
                PIG,
                PIGLIN,
                PIGLIN_BRUTE,
                PILLAGER,
                PLAYER,
                POLAR_BEAR,
                PRIMED_TNT,
                PUFFERFISH,
                RABBIT,
                RAVAGER,
                SALMON,
                SHEEP,
                SHULKER,
                SHULKER_BULLET,
                SLIVERFISH,
                SKELETON,
                SKELETON_HORSE,
                SLIME,
                SMALL_FIREBALL,
                SNIFFER,
                SNOWBALL,
                SNOWMAN,
                SPECTRAL_ARROW,
                SPIDER,
                SPLASH_POTION,
                SQUID,
                STRAY,
                STRIDER,
                TADPOLE,
                TEXT_DISPLAY,
                THROWN_EXP_BOTTLE,
                TRADER_LLAMA,
                TRIDENT,
                TROPICAL_FISH,
                TURTLE,
                UNKNOWN,
                VEX,
                VILLAGER,
                VINDICATOR,
                WANDERING_TRADER,
                WARDEN,
                WITCH,
                WITHER,
                WITHER_SKELETON,
                WITHER_SKULL,
                WOLF,
                ZOGLIN,
                ZOMBIE,
                ZOMBIE_HORSE,
                ZOMBIE_VILLAGER,
                ZOMBIFIED_PIGLIN,
            }

            private record World(String name, String uuid) {}

            private record Location(EncapsulatedConfigurationObjects.World world, double x, double y, double z) {}

            // mobarena.proto
            private record FillArea(EncapsulatedConfigurationObjects.Location startPosition, EncapsulatedConfigurationObjects.Location endPosition, EncapsulatedConfigurationObjects.World world) {}

            private record SpawnPoint(String name, EncapsulatedConfigurationObjects.FillArea fillArea) {}

            private record MobSpawnEntry(EntityType entityType, int amount, int spawnRound, int modulo, String spawnPoint, boolean countTowardsLimit) {}

            // arena.proto
            private record Arena(String name, HashMap<String, EncapsulatedConfigurationObjects.SpawnPoint> spawnPoints, ArrayList<EncapsulatedConfigurationObjects.MobSpawnEntry> mobSpawnEntries) {}

            // config.proto
            private record Config(Map<String, EncapsulatedConfigurationObjects.Arena> arenas) {}
        }

        private static EncapsulatedConfigurationObjects.World generateWorld(World world) {
            return new EncapsulatedConfigurationObjects.World(world.getName(), world.getUID().toString());
        }

        private static EncapsulatedConfigurationObjects.Location generateLocation(Location location) {
            return new EncapsulatedConfigurationObjects.Location(generateWorld(location.getWorld()), location.x(), location.y(), location.z());
        }

        private static EncapsulatedConfigurationObjects.FillArea generateFillArea(FillArea fillArea) {
            return new EncapsulatedConfigurationObjects.FillArea(generateLocation(fillArea.pos1()), generateLocation(fillArea.pos2()), generateWorld(fillArea.world()));
        }

        private static EncapsulatedConfigurationObjects.SpawnPoint generateSpawnPoint(SpawnPoint spawnPoint) {
            return new EncapsulatedConfigurationObjects.SpawnPoint(spawnPoint.getName(), generateFillArea(spawnPoint.getFillArea()));
        }

        private static EncapsulatedConfigurationObjects.MobSpawnEntry generateMobSpawnEntry(MobSpawnEntry mobSpawnEntry) {
            return new EncapsulatedConfigurationObjects.MobSpawnEntry(EncapsulatedConfigurationObjects.EntityType.valueOf(mobSpawnEntry.mob().toString()), mobSpawnEntry.amount(), mobSpawnEntry.spawnRound(), mobSpawnEntry.modulo(), mobSpawnEntry.spawnPoint(), mobSpawnEntry.countTowardsLimit());
        }

        private static EncapsulatedConfigurationObjects.Arena generateArena(Arena arena) {
            HashMap<String, EncapsulatedConfigurationObjects.SpawnPoint> spawnPoints = new HashMap<>();

            for (String spawnPointKey : arena.getSpawnPoints().keySet()) {
                SpawnPoint spawnPointValue = arena.getSpawnPoints().get(spawnPointKey);

                spawnPoints.put(spawnPointKey, generateSpawnPoint(spawnPointValue));
            }

            ArrayList<EncapsulatedConfigurationObjects.MobSpawnEntry> mobSpawnEntries = new ArrayList<>();

            for (MobSpawnEntry mobSpawnEntry : arena.getMobSpawnEntries()) {
                mobSpawnEntries.add(generateMobSpawnEntry(mobSpawnEntry));
            }

            return new EncapsulatedConfigurationObjects.Arena(arena.getName(), spawnPoints, mobSpawnEntries);
        }

        public static EncapsulatedConfigurationObjects.Config generateConfiguration() {
            EncapsulatedConfigurationObjects.Config configRecord;

            HashMap<String, EncapsulatedConfigurationObjects.Arena> arenasHashMap = new HashMap<>();

            for (String arenaKey : Container.Containers.arenaContainer.getTracked().keySet()) {
                Arena arenaValue = Container.Containers.arenaContainer.getTracked(arenaKey);

                arenasHashMap.put(arenaKey, generateArena(arenaValue));
            }

            configRecord = new EncapsulatedConfigurationObjects.Config(arenasHashMap);
        }
    }
}
