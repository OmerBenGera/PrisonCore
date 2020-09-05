package com.ome_r.rankups.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.PrisonCore;
import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.rankups.Rankup;

@Command(name = "ranks", aliases = "", args = "", perm = "",
			desc = "Shows the list of the ranks.", minArgs = 0, maxArgs = 0)
public class RanksCmd extends CommandsHandle{
	
	@Override
	public void run(Player p, String[] args) {
		Rankup pRank = PrisonCore.getRankups().get(p);
		String ranks = "";
		
		for(Rankup r : PrisonCore.getRankups().rankups()){
			String youAreHere = "";
			if(pRank != null && pRank.equals(r)) youAreHere = getMessage("YOUAREHERE_FORMAT", p);
			if(r.getNextRank() == null)
				ranks += getMessage("LAST_RANK_FORMAT", p).replace("{0}", r.getRank()).replace("{1}", youAreHere) + "\n";
			else 
				ranks += getMessage("RANKS_FORMAT", p).replace("{0}", r.getRank())
				.replace("{1}", r.getNextRank().getRank()).replace("{2}", r.getNextRank().getPrice() + "")
				.replace("{3}", youAreHere) + "\n";
		}
		
		p.sendMessage(getMessage("RANKS_COMMAND", p).replace("{0}", ranks));
		
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
