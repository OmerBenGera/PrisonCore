package com.ome_r.mines;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ome_r.PrisonCore;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.utils.LocationUtils;
import com.ome_r.mines.utils.PMaterial;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class Mines {

	private List<Mine> mines;
	
	public Mines(){
		mines = new ArrayList<>();
	}
	
	public Mine get(String name){
		for(Mine m : mines)
			if(m.getName().equalsIgnoreCase(name)) return m;
		
		return null;
	}
	
	public Mine get(Location loc){
		for(Mine m : mines){
			Location max = new Location(m.getLocations()[1].getWorld(), 
							m.getLocations()[1].getX(), 
							m.getLocations()[1].getY() + 2, 
							m.getLocations()[1].getZ());
			if(LocationUtils.betweenPoints(loc, m.getLocations()[0], max))
				return m;
		}
		
		return null;
	}
	
	public Mine create(String name, Selection sel){
		Mine m = new Mine(name, sel.getMaximumPoint(), sel.getMinimumPoint());
		
		if(!mines.contains(m))
			mines.add(m);
		
		return m;
	}
	
	public void load(String name, Location loc1, Location loc2, List<PMaterial> chances, long delay){
		Mine m = new Mine(name, loc1, loc2, chances, delay);
		
		if(!mines.contains(m))
			mines.add(m);
	}
	
	public void delete(Mine m){
		if(mines.contains(m))
			mines.remove(m);
	}
	
	public boolean contains(String name){
		for(Mine m : mines)
			if(m.getName().equalsIgnoreCase(name)) return true;
		
		return false;
	}
	
	public boolean contains(Mine m){
		return mines.contains(m);
	}
	
	public List<Mine> mines(){
		return mines;
	}
	
	@SuppressWarnings("deprecation")
	public void runTask(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PrisonCore.getPlugin(PrisonCore.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Mine m : mines)
					if(m.getResetTime() <= (System.currentTimeMillis() / 1000)){
						m.reset();
						for(Player p : Bukkit.getOnlinePlayers())
							p.sendMessage(CommandsHandle.getMessage("MINE_RESET_AUTOMATICALLY", null)
									.replace("{0}", m.getName()));
					}
			}
		}, 0L, 20L);
	}
	
}
