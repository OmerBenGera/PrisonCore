package com.ome_r.plots.commands;

import java.util.List;

import com.ome_r.PrisonCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@Command(name = "info", aliases = "", args = "", perm = "",
        desc = "Shows information about the plot you are standing in.", minArgs = 0, maxArgs = 0)
public class PlotsInfo extends CommandsHandle{

    @Override
    public void run(Player p, String[] args) {
        Location loc = p.getLocation();

        int plotX = (loc.getBlockX() / 36), plotZ = (loc.getBlockZ() / 36);

        if(loc.getX() > 0) plotX++;
        if(loc.getZ() > 0) plotZ++;

        if(PrisonCore.getPlots().isInsidePlot(loc, plotX, plotZ))
            p.sendMessage("Plot Info:\nX: " + plotX + "\nZ: " + plotZ);

        else if(PrisonCore.getPlots().isInsidePlot(loc, (plotX - 1), (plotZ - 1)))
            p.sendMessage("Plot Info:\nX: " + (plotX - 1) + "\nZ: " + (plotZ - 1));

        else if(PrisonCore.getPlots().isInsidePlot(loc, (plotX + 1), (plotZ + 1)))
            p.sendMessage("Plot Info:\nX: " + (plotX + 1) + "\nZ: " + (plotZ + 1));

        else p.sendMessage("No plot found.");

        return;
    }

    @Override
    public List<String> getTabComplete(Player p, String[] args) {
        return null;
    }

}
