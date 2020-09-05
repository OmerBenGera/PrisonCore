package com.ome_r.mines.autopickup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutoPickup {

	public static boolean autoFortune = true;
	
	public static void run(Player p, List<ItemStack> drops){
		if(autoFortune && p.getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) == 0){
			int fortuneLevel = p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
			List<ItemStack> list = new ArrayList<>(drops);
			Random random = new Random();

			drops.clear();
			for(ItemStack is : list){
				Material mat = is.getType();
				short damage = is.getDurability();
				drops.add(new ItemStack(mat, getDropCount(mat, fortuneLevel, random), damage));
			}
		}
	}

	private static int getDropCount(Material mat, int fortuneLevel, Random random){
		if (fortuneLevel > 0){
			int drops = random.nextInt(fortuneLevel + 2) - 1;
			if (drops < 0)
				drops = 0;

			int i = mat == Material.LAPIS_ORE ? 4 + random.nextInt(5) : 1;
			return i * (drops + 1);
		}

		return 1;
	}
	
}
