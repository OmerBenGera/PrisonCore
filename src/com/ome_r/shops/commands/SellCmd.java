package com.ome_r.shops.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ome_r.PrisonCore;
import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.shops.Shop;
import com.ome_r.shops.multipliers.Multipliers;

@Command(name = "sell", aliases = "", args = "", perm = "",
			desc = "Sells the item in your hand.", minArgs = 1, maxArgs = 1)
public class SellCmd extends CommandsHandle{
	
	Multipliers m = PrisonCore.getMultipliers();
	
	@Override
	public void run(Player p, String[] args) {
		Shop shop = PrisonCore.getShops().get(args[0]);
		ItemStack is = p.getItemInHand();
		
		if(!PrisonCore.getShops().contains(args[0])){
			p.sendMessage(getMessage("INVALID_SHOP", p).replace("{0}", args[1]));
			return;
		}
		
		if(!shop.hasPrice(is.getType(), is.getDurability())){
			p.sendMessage(getMessage("CANNOT_SELL_ITEM", p));
			return;
		}
		
		if(!PrisonCore.getEconomy().hasAccount(p))
			PrisonCore.getEconomy().createPlayerAccount(p);
			
		
		int multiplier = 1, price = shop.getPrice(is.getType(), is.getDurability()) * is.getAmount();
		
		if(m.get(p.getUniqueId()) > multiplier)
			multiplier = m.get(p.getUniqueId());
		
		PrisonCore.getEconomy().depositPlayer(p, price * multiplier);
		
		p.setItemInHand(new ItemStack(Material.AIR));
		
		p.sendMessage(getMessage("SOLD_ITEM", p).replace("{0}", is.getAmount() + "")
				.replace("{1}", getType(is.getType())).replace("{2}", (price * multiplier) + ""));
		
		return;
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
