package io.github.projecthsi.mobarena.arena;

import io.github.projecthsi.mobarena.FillArea;
import io.papermc.paper.math.Position;
import org.bukkit.Location;
import org.bukkit.World;

public class SpawnPoint {
    private String name;
    private FillArea fillArea;

    public SpawnPoint(String name, FillArea fillArea) {
        this.name = name;
        this.fillArea = fillArea;
    }

    public SpawnPoint(String name, Location pos1, Location pos2, World world) {
        this.name = name;
        this.fillArea = new FillArea(pos1, pos2, world);
    }

    public String getName() {
        return name;
    }

    public FillArea getFillArea() {
        return fillArea;
    }
}
