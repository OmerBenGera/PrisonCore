package com.ome_r.mines.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LocationUtils {

	public static boolean betweenPoints(Location loc, Location pointA, Location pointB){
		return (loc.getX() >= pointA.getX() && loc.getX() <= pointB.getX() && 
				loc.getY() >= pointA.getY() && loc.getY() <= pointB.getY() && 
				loc.getZ() >= pointA.getZ() && loc.getZ() <= pointB.getZ());
	}
	
	public static void teleport(Player p){
		Location loc = p.getLocation();
		int y = loc.getBlockY();
		Block b = loc.getWorld().getBlockAt(loc);
		
		while(b != null && !b.getType().equals(Material.AIR))
			b = loc.getWorld().getBlockAt(loc.getBlockX(), y++, loc.getBlockZ());
		
		p.teleport(new Location(loc.getWorld(), loc.getX(), y, loc.getZ()));
	}
	
}
