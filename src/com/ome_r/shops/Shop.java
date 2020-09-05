package com.ome_r.shops;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.ome_r.mines.Mine;

public class Shop {

	private Map<ItemStack, Integer> prices;
	private Mine mine;
	private String name;
	
	//----------------------------------------//
	//             Create a Shop              //
	//----------------------------------------//
	
	public Shop(String name){
		this.prices = new HashMap<>();
		this.mine = null;
		this.name = name;
	}
	
	//----------------------------------------//
	//              Load a Shop               //
	//----------------------------------------//
	
	public Shop(String name, Mine mine, Map<ItemStack, Integer> prices){
		this.prices = prices;
		this.mine = mine;
		this.name = name;
	}
	
	//----------------------------------------//
	//            Getter Methods              //
	//----------------------------------------//
	
	public String getName(){
		return name;
	}
	
	//----------------------------------------//
	//             Prices Section             //
	//----------------------------------------//
	
	public int getPrice(Material type, short damage){
		ItemStack is = new ItemStack(type, 1, damage);
		if(prices.containsKey(is))
			return prices.get(is);
		
		return 0;
	}
	
	public void addPrice(Material type, short damage, int price){
		prices.put(new ItemStack(type, 1, damage), price);
	}
	
	public boolean hasPrice(Material type, short damage){
		return prices.containsKey(new ItemStack(type, 1, damage));
	}
	
	public Map<ItemStack, Integer> getPrices(){
		return prices;
	}
	
	//----------------------------------------//
	//             Mines Section              //
	//----------------------------------------//
	
	public void setMine(Mine mine){
		this.mine = mine;
	}
	
	public Mine getMine(){
		return mine;
	}
	
	public boolean isShopMine(){
		return mine != null;
	}
	
}
