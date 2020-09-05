package com.ome_r.shops.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.shops.Shop;

@Command(name = "list", aliases = "", args = "", perm = "prisoncore.shops.list",
			desc = "Shows the list of shops.", minArgs = 0, maxArgs = 0)
public class ShopsListCmd extends CommandsHandle {

	// mines list
	@Override
	public void run(Player p, String[] args) {
		
		String shops = "";
		int size = getShops().shops().size();
		
		for(Shop shop : getShops().shops())
			if(shops.equals("")) shops += getMessage("SHOPS_NAME", p).replace("{0}", shop.getName());
			else shops += getMessage("SHOPS_SPACER", p) + getMessage("SHOPS_NAME", p).replace("{0}", shop.getName());
		
		p.sendMessage(getMessage("SHOPS_LIST", p).replace("{0}", size + "").replace("{1}", shops));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
