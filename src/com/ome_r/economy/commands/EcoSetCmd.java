package com.ome_r.economy.commands;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.economy.Economy_PrisonCore;

@Command(name = "set", aliases = "", args = "<name> <amount> ", perm = "prisoncore.economy",
			desc = "Sets the amount of money for player.", minArgs = 2, maxArgs = 2)
public class EcoSetCmd extends CommandsHandle{

	// eco set <name> <amount>
	@SuppressWarnings("deprecation")
	@Override
	public void run(Player p, String[] args) {
		OfflinePlayer pl = Bukkit.getOfflinePlayer(args[1]);
		BigDecimal amount = new BigDecimal(args[2]);
		boolean bool = true;
		
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
		
		String balance = NumberFormat.getInstance(Locale.ENGLISH).format(amount).replace(",", "");
		
		if(balance.contains(".") && amount.toString().split("\\.")[0].length() >= 28)
			bool = false;
		
		else if(!balance.contains(".") && amount.toString().length() >= 28)
			bool = false;
		
		if(!bool){
			p.sendMessage("§c" + pl.getName() + " has reached the maximum amount of money.");
			return;
		}
		
		Economy_PrisonCore.accounts.put(pl.getUniqueId(), amount);
		
		p.sendMessage("§7" + pl.getName() + "'s §ebalance was changed to §7$" + getEconomy().format(amount.doubleValue()) + "§e.");
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
