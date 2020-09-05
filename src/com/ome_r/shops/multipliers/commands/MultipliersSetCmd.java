package com.ome_r.shops.multipliers.commands;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@SuppressWarnings("deprecation")
@Command(name = "set", aliases = "", args = "<name> [multiplier] ", perm = "prisoncore.multi.set",
			desc = "Change the multiplier for a player / group.", minArgs = 1, maxArgs = 2)
public class MultipliersSetCmd extends CommandsHandle{

	// multipliers set <name> [multiplier]
	@Override
	public void run(Player p, String[] args) {
		int multiplier = 1;
		
		if(args.length == 3){
			if(!NumberUtils.isNumber(args[2])){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", args[2]));
				return;
			}
			
			multiplier = Integer.parseInt(args[2]);
		}
		
		OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
		if(pl.hasPlayedBefore()){
			getMultipliers().set(pl.getUniqueId(), multiplier);
			p.sendMessage("Changed the multiplier of the player " + pl.getName() + " to " + multiplier + ".");
		}
		
		else if(Arrays.asList(getPermission().getGroups()).contains(args[1])){
			getMultipliers().set(args[1], multiplier);
			p.sendMessage("Changed the multiplier of the group " + args[1] + " to " + multiplier + ".");
		}
		
		else p.sendMessage("§cCouldn't find any player or group with the name " + args[1] + ".");
		
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
}
