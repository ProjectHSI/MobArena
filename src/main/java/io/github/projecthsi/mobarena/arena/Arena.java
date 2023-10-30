package io.github.projecthsi.mobarena.arena;

import io.github.projecthsi.mobarena.FillArea;
import io.github.projecthsi.mobarena.MathExtensions;
import io.github.projecthsi.mobarena.containers.EntityContainer;
import io.github.projecthsi.mobarena.containers.PlayerContainer;
import io.github.projecthsi.mobarena.plugin.MobArena;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Arena {
    private String name = "";
    private MobSpawnEntry[] mobSpawnEntries;
    private HashMap<String, SpawnPoint> spawnPoints;
    private ArrayList<Player> trackedPlayers = new ArrayList<>();
    private ArrayList<Mob> trackedMobs = new ArrayList<>();
    private Location lobbySpawnLocation;

    int maxPlayers;
    int players;

    int maxMobs;
    int mobs;

    private boolean gameInProgress = false;

    //<editor-fold desc="Game Controllers">
    private Location getRandomPositionFromFillArea(FillArea fillArea) {
        return new Location(
                fillArea.getWorld(),
                MathExtensions.getRandomNumberWithinRange(fillArea.getPos1().x(), fillArea.getPos2().x()),
                MathExtensions.getRandomNumberWithinRange(fillArea.getPos1().y(), fillArea.getPos2().y()),
                MathExtensions.getRandomNumberWithinRange(fillArea.getPos1().z(), fillArea.getPos2().z())
        );
    }

    private void chatToAllPlayers(Component component) {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.sendMessage(component);
        }
    }

    private void actionBarToAllPlayers(Component component) {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.sendActionBar(component);
        }
    }

    private void titleToAllPlayers(Component titleComponent, Component subtitleComponent) {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.showTitle(Title.title(titleComponent, subtitleComponent));
        }
    }

    private boolean continueGame;
    private int currentRound;

    public void startGame() {
        gameInProgress = true;

        continueGame = true;

        currentRound = 1;

        maxPlayers = trackedPlayers.size();

        teleportPlayers();

    private NamedTextColor generateCurrentPlayerTextColour(int players, int maxPlayers) {
        int _1Third = maxPlayers / 3;
        int _2Thirds = maxPlayers / 3 * 2;

        if (players <= _2Thirds) {
            if (players <= _1Third) {
                return NamedTextColor.RED;
            } else {
                return NamedTextColor.YELLOW;
            }
        } else {
            return NamedTextColor.GREEN;
        }
    }

    private NamedTextColor generateCurrentMobTextColour(int mobs, int maxMobs) {
        int _1Third = maxMobs / 3;
        int _2Thirds = maxMobs / 3 * 2;

        if (mobs <= _2Thirds) {
            if (mobs <= _1Third) {
                return NamedTextColor.GREEN;
            } else {
                return NamedTextColor.YELLOW;
            }
        } else {
            return NamedTextColor.RED;
        }
    }

    private Component generateActionBarText() {
        final String baseWavePlaceholder = "<yellow><wave></yellow>";

        TagResolver waveNumberPlaceholder = Formatter.number("wave", currentRound);

        Component waveComponentPlaceholder = MiniMessage.miniMessage().deserialize(baseWavePlaceholder, waveNumberPlaceholder);

        TagResolver wavePlaceholder = Placeholder.component("wave", waveComponentPlaceholder);



        final String basePlayersPlaceholder = "<player_colour><players></player_colour>/<green><max_players></green>";

        NamedTextColor playersTextColor = generateCurrentPlayerTextColour(players, maxPlayers);

        TagResolver playersNumberPlaceholder = Formatter.number("players", players);
        TagResolver maxPlayersNumberPlaceholder = Formatter.number("max_players", maxPlayers);
        TagResolver playerColoursPlaceholder = Placeholder.styling("player_colour", playersTextColor);

        Component playersComponentPlaceholder = MiniMessage.miniMessage().deserialize(basePlayersPlaceholder,
                playersNumberPlaceholder,
                maxPlayersNumberPlaceholder,
                playerColoursPlaceholder);

        TagResolver playersPlaceholder = Placeholder.component("players", playersComponentPlaceholder);



        final String baseMobsPlaceholder = "<mob_colour><mobs></mob_colour>/<red><max_mobs></red>";

        NamedTextColor mobsTextColor = generateCurrentMobTextColour(mobs, maxMobs);

        TagResolver mobsNumberPlaceholder = Formatter.number("mobs", mobs);
        TagResolver maxMobsNumberPlaceholder = Formatter.number("max_mobs", maxMobs);
        TagResolver mobColoursPlaceholder = Placeholder.styling("mob_colour", mobsTextColor);

        Component mobsComponentPlaceholder = MiniMessage.miniMessage().deserialize(baseMobsPlaceholder,
                mobsNumberPlaceholder,
                maxMobsNumberPlaceholder,
                mobColoursPlaceholder);

        TagResolver mobsPlaceholder = Placeholder.component("mobs", mobsComponentPlaceholder);



        final String baseMiniMessage = "<yellow>Wave:</yellow> <wave>" + "<br>" +
                "<green>Players:</green> <players>" + "<br>" +
                "<red>Mobs:</red> <mobs>";

        Component actionBarComponent = MiniMessage.miniMessage().deserialize(baseMiniMessage, wavePlaceholder, playersPlaceholder, mobsPlaceholder);

        return actionBarComponent;
    }

    }

    private void wave() {
        currentRound++;
        spawnMobs(currentRound);
    }

    private void spawnMobs(int wave) {
        mobs = 0;

        for (MobSpawnEntry mobSpawnEntry : mobSpawnEntries) {
            MobArena.getInstance().getLogger().info("spawn entry yippee");

            if (!mobSpawnEntry.shouldSpawn(wave)) continue;
            MobArena.getInstance().getLogger().info("we gain invincibility for 1/35th of a second");
            MobArena.getInstance().getLogger().info("Woohoo!");

            int spawnCount = mobSpawnEntry.getSpawnCount(wave);

            mobs += spawnCount;

            SpawnPoint spawnPoint = spawnPoints.get(mobSpawnEntry.getSpawnPoint());
            World spawnWorld = spawnPoint.getFillArea().getWorld();

            for (int i = 0; i < spawnCount; i++) {
                Location spawnLocation = new Location(
                        spawnWorld,
                        MathExtensions.getRandomNumberWithinRange(spawnPoint.getFillArea().getPos1().x(), spawnPoint.getFillArea().getPos2().x()),
                        MathExtensions.getRandomNumberWithinRange(spawnPoint.getFillArea().getPos1().y(), spawnPoint.getFillArea().getPos2().y()),
                        MathExtensions.getRandomNumberWithinRange(spawnPoint.getFillArea().getPos1().z(), spawnPoint.getFillArea().getPos2().z())
                );

                // needed for Folia support.
                MobArena.getInstance().getServer().getRegionScheduler().execute(MobArena.getInstance(), spawnLocation, () -> {
                    Entity newEntity = spawnWorld.spawnEntity(getRandomPositionFromFillArea(spawnPoint.getFillArea()), mobSpawnEntry.getMob());

                    // same here.
                    newEntity.getScheduler().execute(MobArena.getInstance(), () -> {
                        newEntity.teleport(spawnLocation);
                    }, null, 0);

                    // we don't need to run these in the entity scheduler, as we're not performing tasks on an entity.
                    // we're doing casting and adding to an array, not entity tasks.
                    trackedMobs.add((Mob) newEntity);

                    EntityContainer.getTrackedMobs().put((Mob) newEntity, this);
                });
            }
        }

        maxMobs = mobs;
    }

    private void teleportPlayers() {
        for (Player trackedPlayer : trackedPlayers) {
            trackedPlayer.teleport(getRandomPositionFromFillArea(spawnPoints.get("arena").getFillArea()));
            // I think this is done in joinArena().
            // PlayerContainer.getTrackedPlayers().put(trackedPlayer);
        }
    }
    //</editor-fold>

    //<editor-fold desc="Checks">
    private void mobSpawnEntryCheck() throws Exception {
        for (MobSpawnEntry mobSpawnEntry : mobSpawnEntries) {
            boolean mobSpawnEntryIsOk = false;

            for (String spawnPoint : spawnPoints.keySet()) {
                if (Objects.equals(spawnPoint, mobSpawnEntry.getSpawnPoint())) {
                    mobSpawnEntryIsOk = true;
                    break;
                }
            }

            if (!mobSpawnEntryIsOk) throw new Exception("All mob spawn entries must have a valid spawn point.");
        }
    }

    private void spawnPointsCheck() throws Exception {
        if (spawnPoints.containsKey("arena")) {
            throw new Exception("Arena must contain the 'arena' spawn point.");
        }
        // no checks (yet)
    }

    public void initialCheck() {
        try {
            mobSpawnEntryCheck();
            spawnPointsCheck();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void playerDeath(Player player) {
        HashMap<Player, Arena> trackedPlayersHashmap = PlayerContainer.getTrackedPlayers();

        trackedPlayersHashmap.remove(player);

        PlayerContainer.setTrackedPlayers(trackedPlayersHashmap);

        player.setHealth(20);
        player.teleport(lobbySpawnLocation);
    }

    public void entityDeath(Mob mob) {
        trackedMobs.remove(mob);

        HashMap<Mob, Arena> trackedMobsHashmap = EntityContainer.getTrackedMobs();

        trackedMobsHashmap.remove(mob);

        EntityContainer.setTrackedMobs(trackedMobsHashmap);

        mobs -= 1;
    }
    //</editor-fold>

    //<editor-fold>
    public String getName() {
        return name;
    }

    public MobSpawnEntry[] getMobSpawnEntries() {
        return mobSpawnEntries;
    }

    public HashMap<String, SpawnPoint> getSpawnPoints() {
        return spawnPoints;
    }

    public ArrayList<Player> getPlayers() {
        return trackedPlayers;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobSpawnEntries(MobSpawnEntry[] mobSpawnEntries) {
        this.mobSpawnEntries = mobSpawnEntries;
    }

    public void setSpawnPoints(HashMap<String, SpawnPoint> spawnPoints) {
        this.spawnPoints = spawnPoints;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.trackedPlayers = players;
    }

    public Location getLobbySpawnLocation() {
        return lobbySpawnLocation;
    }

    public void setLobbySpawnLocation(Location lobbySpawnLocation) {
        this.lobbySpawnLocation = lobbySpawnLocation;
    }
    //</editor-fold>
}
