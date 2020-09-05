package com.ome_r.plots;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Plots {

    public int plotLength, roadLength;
    public World world;
    private List<Plot> plots;
    private Plot lastPlot;

    public Plots() {
        plots = new ArrayList<>();
    }

    public Plot create(UUID uuid, int x, int y){
        Plot plot = new Plot(uuid, x, y);
        plots.add(plot);

        return plot;
    }

    public Plot get(int x, int z){
        for(Plot p : plots)
            if(x == p.getX() && z == p.getZ()) return p;

        return null;
    }

    public boolean isInsidePlot(Location loc, int x, int z){
        int maxX = (x * 36) -3, minX = (x * 36) - 33, maxZ = (z * 36) - 3, minZ = (z * 36) - 33;

        return ((loc.getX() >= minX && loc.getX() <= maxX) && (loc.getZ() >= minZ && loc.getZ() <= maxZ));
    }

    public Plot auto(){
        int x = lastPlot.getX(), z = lastPlot.getZ();
        if(x >= z && x < -z + 1)
            x++;
        else if(x >= -z + 1 && x <= z)
            x--;
        else if(z >= -x + 1 && z < x)
            z++;
        else if(z > x && z <= -x + 1)
            z--;

        this.lastPlot = get(x, z);
        return lastPlot;
    }

}
