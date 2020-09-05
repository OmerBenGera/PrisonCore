package com.ome_r.shops.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@Command(name = "create", aliases = "", args = "<name> ", perm = "prisoncore.shops.create",
			desc = "Create a new shop.", minArgs = 1, maxArgs = 1)
public class ShopsCreateCmd extends CommandsHandle {

	// shops create <name>
	@Override
	public void run(Player p, String[] args) {
		
		if(getShops().contains(args[1])){
			p.sendMessage(getMessage("SHOP_ALREADY_EXISTS", p).replace("{0}", args[1]));
			return;
		}
		
		getShops().create(args[1]);
		p.sendMessage(getMessage("CREATE_SHOP", p).replace("{0}", args[1]));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

	
}
