package com.ome_r.mines.autopickup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutoBlock {
	
	public static Map<Material, Material> convertToBlock = new HashMap<>();
	public static boolean autoBlock = true;
	
	public static void run(Player p){
		if(autoBlock){
			Map<ItemStack, Integer> map = new HashMap<>();

			for(ItemStack is : p.getInventory().getContents())
				if(is != null && is.getType() != Material.AIR){
					List<Material> items = Arrays.asList(Material.INK_SACK, Material.GOLD_INGOT, Material.IRON_INGOT,
							Material.DIAMOND, Material.EMERALD, Material.REDSTONE, Material.QUARTZ);
					if(items.contains(is.getType())){
						ItemStack itemStack = new ItemStack(is.getType(), 1, is.getDurability());
						if(map.containsKey(itemStack))
							map.put(itemStack, map.get(itemStack) + is.getAmount());
						else map.put(itemStack, is.getAmount());
						p.getInventory().remove(is);
					}
				}

			for(ItemStack is : map.keySet()){
				Material convertedMat = convertToBlock.get(is.getType());
				if(map.get(is) / 9 != 0)
					p.getInventory().addItem(new ItemStack(convertedMat, (map.get(is) / 9)));
				if(map.get(is) % 9 != 0) 
					p.getInventory().addItem(new ItemStack(is.getType(), (map.get(is) % 9), is.getDurability()));
			}
			
		}
	}
	
	static{
		convertToBlock.put(Material.INK_SACK, Material.LAPIS_BLOCK);
		convertToBlock.put(Material.GOLD_INGOT, Material.GOLD_BLOCK);
		convertToBlock.put(Material.IRON_INGOT, Material.IRON_BLOCK);
		convertToBlock.put(Material.DIAMOND, Material.DIAMOND_BLOCK);
		convertToBlock.put(Material.EMERALD, Material.EMERALD_BLOCK);
		convertToBlock.put(Material.REDSTONE, Material.REDSTONE_BLOCK);
		convertToBlock.put(Material.QUARTZ, Material.QUARTZ_BLOCK);
	}
	
}
