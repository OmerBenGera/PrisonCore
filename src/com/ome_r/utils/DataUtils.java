package com.ome_r.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class DataUtils {

	public static String itemsToString (List<ItemStack> itemStacks){
		String serialization = "";
		for (ItemStack is : itemStacks)
			if (is != null){
				String serializedItemStack = new String();
				
				String isType = String.valueOf(is.getType().getId());
				serializedItemStack += "t@" + isType;

				if (is.getDurability() != 0){
					String isDurability = String.valueOf(is.getDurability());
					serializedItemStack += ":d@" + isDurability;
				}

				if (is.getAmount() != 1){
					String isAmount = String.valueOf(is.getAmount());
					serializedItemStack += ":a@" + isAmount;
				}
				
				Map<Enchantment,Integer> isEnch = new HashMap<>(is.getItemMeta().getEnchants());
				if (isEnch.size() > 0)
					for (Enchantment ench : isEnch.keySet())
						serializedItemStack += ":e@" + ench.getId() + "@" + isEnch.get(ench);

				if(is.getItemMeta().hasDisplayName())
					serializedItemStack += ":n@" + is.getItemMeta().getDisplayName().replaceAll("&", "~").replaceAll("§", "&");

				if(is.getItemMeta().hasLore()){
					String isLore = "";
					for(String s : is.getItemMeta().getLore())
						isLore += s + "::";
					serializedItemStack += ":l@" + isLore.replaceAll("&", "~").replaceAll("§", "&");
				}

				serialization += serializedItemStack + ";";
			}

		return serialization;
	}


	public static List<ItemStack> itemsFromString (String items){
		String[] serializedBlocks = items.split(";");
		List<ItemStack> deserializedItems = new ArrayList<>();

		for (int i = 0; i < serializedBlocks.length; i++){
			ItemStack is = null;
			ItemMeta meta = null;
			Boolean createdItemStack = false;

			String[] serializedItemStack = serializedBlocks[i].split(":");
			for (String itemInfo : serializedItemStack) {
				String[] itemAttribute = itemInfo.split("@");
				if (itemAttribute[0].equals("t")){
					is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1])));
					meta = is.getItemMeta();
					createdItemStack = true;
				}

				else if (itemAttribute[0].equals("d") && createdItemStack)
					is.setDurability(Short.valueOf(itemAttribute[1]));

				else if (itemAttribute[0].equals("a") && createdItemStack)
					is.setAmount(Integer.valueOf(itemAttribute[1]));

				else if (itemAttribute[0].equals("e") && createdItemStack)
					meta.addEnchant(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]), true);

				else if(itemAttribute[0].equals("n") && createdItemStack)
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemAttribute[1]).replaceAll("~", "&"));

				else if(itemAttribute[0].equals("l") && createdItemStack)
					meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', itemAttribute[1]).replaceAll("~", "&").split("::")));

				is.setItemMeta(meta);

			}
			deserializedItems.add(is);
		}

		return deserializedItems;
	}

}
