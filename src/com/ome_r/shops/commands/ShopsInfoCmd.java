package com.ome_r.shops.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.shops.Shop;

@Command(name = "info", aliases = "show", args = "<name> ", perm = "prisoncore.shops.info",
			desc = "Shows information about a shop.", minArgs = 1, maxArgs = 1) 
public class ShopsInfoCmd extends CommandsHandle{

	// shops info <name>
	@Override
	public void run(Player p, String[] args) {
		Shop shop = getShops().get(args[1]);
		
		if(!getShops().contains(shop)){
			p.sendMessage(getMessage("INVALID_SHOP", p).replace("{0}", args[1]));
			return;
		}
		
		String mine = "none";
		if(shop.getMine() != null)
			mine = shop.getMine().getName();
		String prices = "none";
		
		for(ItemStack is : shop.getPrices().keySet()){
			String pricesFormat = getMessage("PRICE_FORMAT", p).replace("{0}", getType(is.getType())
					.replace("{1}", is.getDurability() + "").replace("{2}", shop.getPrices().get(is) + ""));
			if(prices.equals("none"))
				prices = pricesFormat;
			else prices += getMessage("PRICE_SPACER", p) + pricesFormat;
		}
		
		p.sendMessage(getMessage("SHOP_INFO", p).replace("{0}", shop.getName())
				.replace("{1}", mine).replace("{2}", prices));
		
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	private String getType(Material type){
		String str = type.name();
		
		if(str.contains("_"))
			for(String s : str.split("_"))
				if(str.equals(type.name())) str = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
				else str += " " + s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		else str = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
		
		return str;
	}

}
