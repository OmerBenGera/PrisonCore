package com.ome_r.shops;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import com.ome_r.mines.Mine;

public class Shops {

	private List<Shop> shops;
	
	public Shops(){
		this.shops = new ArrayList<>();
	}
	
	public Shop create(String name){
		Shop shop = new Shop(name);
		shops.add(shop);
		
		return shop;
	}
	
	public void delete(Shop s){
		if(shops.contains(s))
			shops.remove(s);
	}
	
	public void load(String name, Mine mine, Map<ItemStack, Integer> prices){
		Shop shop = new Shop(name, mine, prices);
		shops.add(shop);
	}
	
	public boolean contains(String name){
		for(Shop shop : shops)
			if(shop.getName().equalsIgnoreCase(name)) return true;
		
		return false;
	}
	
	public boolean contains(Shop shop){
		return shops.contains(shop);
	}
	
	public Shop get(String name){
		for(Shop shop : shops)
			if(shop.getName().equalsIgnoreCase(name)) return shop;
		
		return null;
	}
	
	public Shop get(Mine m){
		for(Shop shop : shops)
			if(shop.getMine().equals(m)) return shop;
		
		return null;
	}
	
	public List<Shop> shops(){
		return shops;
	}
	
}
