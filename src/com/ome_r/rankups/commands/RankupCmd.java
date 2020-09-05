package com.ome_r.rankups.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ome_r.PrisonCore;
import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.rankups.Rankup;
import com.ome_r.utils.PlayerUtils;

import net.md_5.bungee.api.ChatColor;

@Command(name = "rankup", aliases = "", args = "", perm = "",
			desc = "Rank-up to the next rank.", minArgs = 0, maxArgs = 0)
public class RankupCmd extends CommandsHandle{
	
	@Override
	public void run(Player p, String[] args) {
		Rankup rank = PrisonCore.getRankups().get(p);
		
		if(!PrisonCore.getRankups().contains(rank)){
			p.sendMessage(getMessage("RANKUP_NO_ACCESS", p));
			return;
		}
		
		if(rank.getNextRank() == null){
			p.sendMessage(getMessage("RANKUP_LAST", p));
			return;
		}
		
		if(!PrisonCore.getEconomy().hasAccount(p))
			PrisonCore.getEconomy().createPlayerAccount(p);
		
		if(PrisonCore.getEconomy().getBalance(p) < rank.getPrice()){
			p.sendMessage(getMessage("NOT_ENOUGH_MONEY", p));
			return;
		}
		
		PrisonCore.getEconomy().withdrawPlayer(p, rank.getPrice());
		for(String command : rank.getCommands()){
			String message = command.replace("%player%", p.getName())
					.replace("%rankup%", rank.getNextRank().getRank()).replace("%rank%", rank.getRank());
			
			if(command.startsWith("$message "))
				p.sendMessage(translate(message.replace("$message ", "")));
			
			else if(command.startsWith("$broadcast "))
				for(Player pl : Bukkit.getOnlinePlayers())
					pl.sendMessage(translate(message.replace("$broadcast ", "")));
			
			else if(command.startsWith("$actionbar "))
				PlayerUtils.sendActionBar(p, translate(message.replace("$actionbar ", "")));
				
			else if(command.startsWith("$title ")){
				message = translate(message.replace("$title ", ""));
				PlayerUtils.sendTitle(p, message.split(", ")[0], message.split(", ")[1], 5, 20, 5);
			}
			
			else 
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message);
		}
		
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	private String translate(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
