package com.ome_r.shops.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.shops.Shop;

@Command(name = "delete", aliases = "", args = "<mine> ", perm = "prisoncore.shops.delete",
			desc = "Delete a shop.", minArgs = 1, maxArgs = 1)
public class ShopsDeleteCmd extends CommandsHandle {

	@Override
	public void run(Player p, String[] args) {
		Shop s = getShops().get(args[1]);
		
		if(!getShops().contains(s)){
			p.sendMessage(getMessage("INVALID_SHOP", p).replace("{0}", args[1]));
			return;
		}
		
		getShops().delete(s);
		p.sendMessage(getMessage("DELETE_SHOP", p).replace("{0}", s.getName()));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
