package com.ome_r.general;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ome_r.PrisonCore;
import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@Command(name = "prisoncore", aliases = "pc", args = "reload", perm = "prisoncore.reload",
			desc = "Loads the settings from config files.", minArgs = 1, maxArgs = 1)
public class ReloadCmd extends CommandsHandle{
	
	//warp [name]
	@Override
	public void run(Player p, String[] args) {
		if(!args[0].equalsIgnoreCase("reload")){
			p.sendMessage("§cUsage: /pc reload");
			return;
		}
		
		PrisonCore.getInstance().loadData();
		PrisonCore.getMySQL().stopTask();
		PrisonCore.getMySQL().runTask();
		reloadScoreboards();
		
		p.sendMessage("§aPrisonCore reloaded successfully.");
		
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	private void reloadScoreboards(){
		for(Player pl : Bukkit.getOnlinePlayers()){
			pl.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			PrisonCore.getBoards().destroy(pl);
			PrisonCore.getBoards().create(pl).updateLines(true);
		}
	}

}
