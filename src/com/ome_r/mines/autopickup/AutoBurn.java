package com.ome_r.mines.autopickup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class AutoBurn {
	
	public static boolean autoBurn = true;

	public static void run(Block b, List<ItemStack> drops){
		if(autoBurn && (b.getType() == Material.GOLD_ORE || b.getType() == Material.IRON_ORE)){
			List<ItemStack> list = new ArrayList<>(drops);
			for(ItemStack is : list){
				drops.remove(is);
				if(b.getType() == Material.IRON_ORE)
					drops.add(new ItemStack(Material.IRON_INGOT, is.getAmount()));
				else drops.add(new ItemStack(Material.GOLD_INGOT, is.getAmount()));
			}
		}
	}
	
}
