package io.github.projecthsi.mobarena;

import org.bukkit.Location;
import org.bukkit.World;

public class FillArea {
    private final Location pos1;
    private final Location pos2;
    private final World world;

    public FillArea(Location pos1, Location pos2, World world) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
    }

    public Location getPos1() { return pos1; }
    public Location getPos2() { return pos2; }
    public World getWorld() { return world; }
}
