package com.ome_r.shops.multipliers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.ome_r.PrisonCore;

public class Multipliers {

	private Map<UUID, Integer> multipliers;
	private Map<String, Integer> groupMultipliers;
	
	public Multipliers() {
		multipliers = new HashMap<>();
		groupMultipliers = new HashMap<>();
	}
	
	public int get(UUID uuid){
		int multi = 1;
		
		if(multipliers.containsKey(uuid))
			multi = multipliers.get(uuid);
		
		for(String group : PrisonCore.getPermission().getPlayerGroups(Bukkit.getPlayer(uuid)))
			if(groupMultipliers.containsKey(group) && groupMultipliers.get(group) > multi)
				multi = groupMultipliers.get(group);
		
		return multi;
	}
	
	public void set(UUID uuid, int multiplier){
		multipliers.put(uuid, multiplier);
	}
	
	public void set(String group, int multiplier){
		groupMultipliers.put(group, multiplier);
	}
	
}
