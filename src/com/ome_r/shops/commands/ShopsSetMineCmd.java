package com.ome_r.shops.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.Mine;
import com.ome_r.shops.Shop;

@Command(name = "setmine", aliases = "mine", args = "<name> <mine> ", perm = "prisoncore.shops.setmine",
			desc = "Change the mine of a shop.", minArgs = 2, maxArgs = 2)
public class ShopsSetMineCmd extends CommandsHandle {

	// shops setmine <name> <mine>
	@Override
	public void run(Player p, String[] args) {
		Shop shop = getShops().get(args[1]);
		Mine mine = getMines().get(args[2]);
		
		if(!getShops().contains(shop)){
			p.sendMessage(getMessage("INVALID_SHOP", p).replace("{0}", args[1]));
			return;
		}
		
		if(!getMines().contains(mine)){
			p.sendMessage(getMessage("INVALID_MINE", p).replace("{0}", args[2]));
			return;
		}
		
		shop.setMine(mine);
		p.sendMessage(getMessage("CHANGE_SHOP_MINE", p).replace("{0}", shop.getName()).replace("{1}", mine.getName()));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
