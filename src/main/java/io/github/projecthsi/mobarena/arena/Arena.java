package io.github.projecthsi.mobarena.arena;

import io.github.projecthsi.mobarena.FillArea;
import io.github.projecthsi.mobarena.MathExtensions;
import io.github.projecthsi.mobarena.containers.Container;
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
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Arena {
    private String name = "";
    private MobSpawnEntry[] mobSpawnEntries;
    private HashMap<String, SpawnPoint> spawnPoints;
    private ArrayList<Player> trackedPlayers = new ArrayList<>();
    private final ArrayList<Mob> trackedMobs = new ArrayList<>();
    private Location lobbySpawnLocation;

    int maxPlayers;
    int players;

    int maxMobs;
    int mobs;

    private boolean gameInProgress = false;

    //<editor-fold desc="Game Controllers">
    private Location getRandomPositionFromFillArea(FillArea fillArea) {
        return new Location(
                fillArea.world(),
                MathExtensions.getRandomNumberWithinRange(fillArea.pos1().x(), fillArea.pos2().x()),
                MathExtensions.getRandomNumberWithinRange(fillArea.pos1().y(), fillArea.pos2().y()),
                MathExtensions.getRandomNumberWithinRange(fillArea.pos1().z(), fillArea.pos2().z())
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

        currentRound = 0;

        maxPlayers = trackedPlayers.size();

        teleportPlayers();

        gameManager();
    }

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

        Component playerComponentPlaceholder = MiniMessage.miniMessage().deserialize(basePlayersPlaceholder,
                playersNumberPlaceholder,
                maxPlayersNumberPlaceholder,
                playerColoursPlaceholder);

        TagResolver playerPlaceholder = Placeholder.component("players", playerComponentPlaceholder);



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



        final String baseMiniMessage = "<yellow>Wave:</yellow> <wave>" + " - " +
                "<green>Players:</green> <players>" + " - " +
                "<red>Mobs:</red> <mobs>";

        return MiniMessage.miniMessage().deserialize(baseMiniMessage, wavePlaceholder, playerPlaceholder, mobsPlaceholder);
    }

    private void gameOver() {
        chatToAllPlayers(MiniMessage.miniMessage().deserialize("Hello, err, guys don't do what I did - Don't~ Never charge your DS at Freddy Fazbear's Pizza! <i>knocking</i> Oh, guys I messed up, guys don't do what I did, don't do what I did. Don't do the trick! Don't do the Mario trick guys! <i>knocking</i> <b>DON'T DO~!</b>"));
    }

    private void gameLoop(ScheduledTask scheduledTask) {
        actionBarToAllPlayers(generateActionBarText());

        for (Player player : trackedPlayers) {
            player.getScheduler().execute(MobArena.getInstance(), () -> {
                player.setFoodLevel(20);
                player.setSaturation(20);
            }, null, 0);
        }

        // we check for the size of trackedMobs as well.
        // prevents soft locking if we kill entities fast enough.
        if (mobs == 0 || trackedMobs.isEmpty()) {
            wave();
        }

        if (!continueGame) {
            gameOver();
            scheduledTask.cancel();
        }
    }

    private void gameManager() {
        MobArena.getInstance().getServer().getGlobalRegionScheduler().runAtFixedRate(MobArena.getInstance(), this::gameLoop, 1, 1);
    }

    private void setupKitForPlayer(Player player) {
        PlayerInventory playerInventory = player.getInventory();

        playerInventory.clear();

        playerInventory.setItem(0, new ItemStack(Material.IRON_SWORD));

        ItemStack crossbow = new ItemStack(Material.BOW);
        crossbow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        playerInventory.setItem(1, crossbow);
        playerInventory.setItem(1 /* Target Inventory Slot */ + (9 /* Columns/Row */ * (3 /* Target Inventory Row */ - 1)), new ItemStack(Material.ARROW));
        playerInventory.setHeldItemSlot(0);

        playerInventory.setHelmet(new ItemStack(Material.IRON_HELMET));
        playerInventory.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        playerInventory.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        playerInventory.setBoots(new ItemStack(Material.IRON_BOOTS));

        playerInventory.setItemInOffHand(new ItemStack(Material.SHIELD));
    }

    private Component generateWaveTitle() {
        final String baseWaveString = "<yellow>Wave: <wave></yellow>";

        TagResolver wavePlaceholder = Formatter.number("wave", currentRound);

        return MiniMessage.miniMessage().deserialize(baseWaveString, wavePlaceholder);
    }

    private Component generateWaveSubtitle() {
        final String baseWaveDataString = "<red>Mobs: <mobs></red>";

        TagResolver mobsPlaceholder = Formatter.number("mobs", maxMobs);

        return MiniMessage.miniMessage().deserialize(baseWaveDataString, mobsPlaceholder);
    }

    private void wave() {
        currentRound++;

        spawnMobs(currentRound);

        for (Player player : trackedPlayers) {
            player.getScheduler().execute(MobArena.getInstance(), () -> {
                player.showTitle(Title.title(generateWaveTitle(), generateWaveSubtitle()));
                player.sendMessage(generateWaveSubtitle());

                player.setHealth(20);

                setupKitForPlayer(player);
            }, null, 0);
        }
    }

    private void spawnMobs(int wave) {
        mobs = 0;

        for (MobSpawnEntry mobSpawnEntry : mobSpawnEntries) {
            MobArena.getInstance().getLogger().info("spawn entry yippee");

            if (mobSpawnEntry.shouldSpawn(wave)) continue;
            MobArena.getInstance().getLogger().info("we gain invincibility for 1/35th of a second");
            MobArena.getInstance().getLogger().info("Woo-hoo!");

            int spawnCount = mobSpawnEntry.getSpawnCount(wave);

            mobs += spawnCount;

            SpawnPoint spawnPoint = spawnPoints.get(mobSpawnEntry.spawnPoint());
            World spawnWorld = spawnPoint.getFillArea().world();

            for (int i = 0; i < spawnCount; i++) {
                Location spawnLocation = new Location(
                        spawnWorld,
                        MathExtensions.getRandomNumberWithinRange(spawnPoint.getFillArea().pos1().x(), spawnPoint.getFillArea().pos2().x()),
                        MathExtensions.getRandomNumberWithinRange(spawnPoint.getFillArea().pos1().y(), spawnPoint.getFillArea().pos2().y()),
                        MathExtensions.getRandomNumberWithinRange(spawnPoint.getFillArea().pos1().z(), spawnPoint.getFillArea().pos2().z())
                );

                // needed for Folia support.
                MobArena.getInstance().getServer().getRegionScheduler().execute(MobArena.getInstance(), spawnLocation, () -> {
                    Entity newEntity = spawnWorld.spawnEntity(getRandomPositionFromFillArea(spawnPoint.getFillArea()), mobSpawnEntry.mob());

                    // same here.
                    newEntity.getScheduler().execute(MobArena.getInstance(), () -> newEntity.teleport(spawnLocation), null, 0);

                    // we don't need to run these in the entity scheduler, as we're not performing tasks on an entity.
                    // we're doing casting and adding to an array, not entity tasks.
                    trackedMobs.add((Mob) newEntity);

                    Container.Containers.mobContainer.addTracked((Mob) newEntity, this);
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
                if (Objects.equals(spawnPoint, mobSpawnEntry.spawnPoint())) {
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
        Container.Containers.playerContainer.removeTracked(player);

        player.setHealth(20);
        player.teleport(lobbySpawnLocation);

        continueGame = !(trackedPlayers.size() <= 1);
    }

    public void playerQuit(Player player) {
        Container.Containers.playerContainer.removeTracked(player);

        continueGame = !(trackedPlayers.size() <= 1);
    }

    public void entityDeath(Mob mob) {
        trackedMobs.remove(mob);

        Container.Containers.mobContainer.removeTracked(mob);

        // fixes a bug where the game thinks there are more mobs/players then there actually are.
        mobs = trackedMobs.size();
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
