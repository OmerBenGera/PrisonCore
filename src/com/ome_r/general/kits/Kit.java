package com.ome_r.general.kits;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

public class Kit {

	private String name;
	private List<ItemStack> items;
	private Map<UUID, Long> times;
	private long delay;
	
	public Kit(String name, List<ItemStack> items) {
		this.times = new HashMap<>();
		this.name = name;
		this.items = items;
		this.delay = 0;
	}
	
	public Kit(String name, List<ItemStack> items, long delay) {
		this.times = new HashMap<>();
		this.name = name;
		this.items = items;
		this.delay = delay;
	}
	
	public String getName(){
		return name;
	}
	
	public List<ItemStack> getItems(){
		return items;
	}
	
	public long getDelay(){
		return delay;
	}
	
	public void addPlayer(UUID uuid){
		times.put(uuid, ((System.currentTimeMillis() / 1000) + delay));
	}
	
	public void removePlayer(UUID uuid){
		if(times.containsKey(uuid))
			times.remove(uuid);
	}
	
	public long getTime(UUID uuid){
		return times.containsKey(uuid) ? times.get(uuid) : (System.currentTimeMillis() / 1000);
	}
	
}
