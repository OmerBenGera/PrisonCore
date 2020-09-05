package com.ome_r.shops.commands;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.shops.Shop;

@Command(name = "set", aliases = "", args = "<name> <material> <price> ", perm = "prisoncore.shops.set",
			desc = "Change a price.", minArgs = 3, maxArgs = 3)
public class ShopsSetCmd extends CommandsHandle{

	// shops set <name> <material> <price>
	@Override
	public void run(Player p, String[] args) {
		Shop shop = getShops().get(args[1]);
		int price;
		ItemStack is = getItemStack(args[2]);
		
		if(!getShops().contains(shop)){
			p.sendMessage(getMessage("INVALID_SHOP", p).replace("{0}", args[1]));
			return;
		}
		
		if(!NumberUtils.isNumber(args[3])){
			p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", args[3]));
			return;
		}
		price = Integer.parseInt(args[3]);
		
		if(is == null){
			p.sendMessage(getMessage("INVALID_MATERIAL", p).replace("{0}", args[2]));
			return;
		}
		
		shop.addPrice(is.getType(), is.getDurability(), price);
		p.sendMessage(getMessage("ADD_NEW_PRICE", p).replace("{0}", args[2])
				.replace("{1}", price + "").replace("{2}", shop.getName()));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack getItemStack(String s){
		Material type;
		short damage = 0;
		
		if(NumberUtils.isNumber(s)){
			type = Material.getMaterial(Integer.parseInt(s));
		}
		
		else{
			if(s.contains(":")){
				type = Material.getMaterial(s.toUpperCase().split(":")[0]);
				damage = Short.parseShort(s.split(":")[1]);
			}else type = Material.getMaterial(s.toUpperCase());
		}
		
		return type == null ? null : new ItemStack(type, 1, damage);
	}
	
}
