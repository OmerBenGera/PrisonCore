package com.ome_r.economy.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

import net.milkbowl.vault.economy.EconomyResponse;

@Command(name = "give", aliases = "", args = "<name> <amount> ", perm = "prisoncore.economy",
			desc = "Gives money for player.", minArgs = 2, maxArgs = 2)
public class EcoGiveCmd extends CommandsHandle{

	// eco add <name> <amount>
	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, String[] args) {
		OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
		BigDecimal amount = new BigDecimal(args[2]);
		
		if(!NumberUtils.isNumber(args[2])){
			getMessage("INVALID_NUMBER", p).replace("{0}", args[2]);
			return;
		}
		
		if(!pl.hasPlayedBefore()){
			p.sendMessage(getMessage("INVALID_PLAYER", p).replace("{0}", args[1]));
			return;
		}
		
		if(!getEconomy().hasAccount(pl))
			getEconomy().createPlayerAccount(pl);
		
		EconomyResponse r = getEconomy().depositPlayer(pl, amount.doubleValue());
		
		if(r.transactionSuccess())
			p.sendMessage("§7" + pl.getName() + "§e's balance was credited in §7$" + getEconomy().format(amount.doubleValue()) + "§e.");
		else p.sendMessage(r.errorMessage);
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		List<String> list = new ArrayList<>();
		
		if(args.length == 2)
			for(Player pl : Bukkit.getOnlinePlayers())
				if(pl.getName().toLowerCase().startsWith(args[1].toLowerCase()))
					list.add(pl.getName());
				
		return list;
	}

}
