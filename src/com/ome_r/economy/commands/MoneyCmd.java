package com.ome_r.economy.commands;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.ome_r.PrisonCore;
import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.economy.Economy_PrisonCore;

import net.milkbowl.vault.economy.Economy;

@SuppressWarnings("deprecation")
@Command(name = "money", aliases = "bal,balance", args = "[name]", perm = "",
			desc = "Shows the amount of dollars of a specific player.", minArgs = 0, maxArgs = 1)
public class MoneyCmd extends CommandsHandle{
	
	private Economy econ = PrisonCore.getEconomy();
	
	//warp [name]
	@Override
	public void run(Player p, String[] args) {
		OfflinePlayer target = p;
		BigDecimal amount;
		
		if(args.length != 0 && args.length != 1){
			p.sendMessage("§cUsage: /money [player]");
			return;
		}
		
		if(args.length == 1){
			target = Bukkit.getOfflinePlayer(args[0]);
			if(!target.hasPlayedBefore()){
				p.sendMessage("§cCouldn't find a player called " + args[0] + ".");
				return;
			}
		}
		
		amount = Economy_PrisonCore.accounts.get(target.getUniqueId());
		
		if(!econ.hasAccount(target))
			econ.createPlayerAccount(target);
		
		String balance = NumberFormat.getInstance(Locale.ENGLISH).format(amount),
				beforeDot = balance, afterDot = "";
		
		if(balance.contains(".")){
			beforeDot = balance.split("\\.")[0];
			afterDot = "." + balance.split("\\.")[1];
			if(afterDot.length() > 3)
				afterDot = afterDot.substring(0, 3);
		}
		
		String playersName = "Your";
		if(!target.equals(p))
			playersName = target.getName() + "'s";
		
		p.sendMessage("§e" + playersName + " money: §7" +
				econ.currencyNamePlural() + beforeDot + afterDot);
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
