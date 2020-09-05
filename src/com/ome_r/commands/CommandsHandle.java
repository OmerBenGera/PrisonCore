package com.ome_r.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.ome_r.PrisonCore;
import com.ome_r.mines.Mines;
import com.ome_r.rankups.Rankups;
import com.ome_r.shops.Shops;
import com.ome_r.shops.multipliers.Multipliers;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public abstract class CommandsHandle implements Comparable<CommandsHandle>{

	public abstract void run(Player p, String[] args);
	
	public abstract List<String> getTabComplete(Player p, String[] args);
	
	public static Map<String, String> messages = new HashMap<>();
	
	public PrisonCore getInstance(){
		return PrisonCore.getInstance();
	}
	
	public Mines getMines(){
		return PrisonCore.getMines();
	}
	
	public Shops getShops(){
		return PrisonCore.getShops();
	}
	
	public Rankups getRankups(){
		return PrisonCore.getRankups();
	}
	
	public Multipliers getMultipliers(){
		return PrisonCore.getMultipliers();
	}
	
	public Economy getEconomy(){
		return PrisonCore.getEconomy();
	}
	
	public Permission getPermission(){
		return PrisonCore.getPermission();
	}
	
	public static String getMessage(String message, Player p){
		String playerName = "", displayName = "", msg = message;
		
		if(p != null){
			playerName = p.getName();
			displayName = p.getDisplayName();
		}
		
		if(messages.containsKey(message))
			msg = messages.get(message).replace("%prefix%", messages.get("PREFIX")).replace("%player%", playerName)
					.replace("%displayname%", displayName);
		
		if(PrisonCore.getInstance().placeHolders)
			return PlaceholderAPI.setPlaceholders(p, msg);
		
		return msg;
	}
	
	public WorldEditPlugin getWorldEdit(){
		return PrisonCore.getWorldEdit();
	}
	
	@Override
	public int compareTo(CommandsHandle o) {
		Command oCommand = o.getClass().getAnnotation(Command.class),
				thisCommand = this.getClass().getAnnotation(Command.class);
		return thisCommand.name().compareTo(oCommand.name());
	}
	
}
