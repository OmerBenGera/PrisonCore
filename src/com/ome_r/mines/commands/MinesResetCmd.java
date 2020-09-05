package com.ome_r.mines.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.Mine;

@Command(name = "reset", aliases = "", args = "<name> [-s] ", perm = "prisoncore.mines.reset",
			desc = "Reset a specific mine.", minArgs = 1, maxArgs = 2)
public class MinesResetCmd extends CommandsHandle {

	// mines reset <mine> [-s]
	@Override
	public void run(Player p, String[] args) {
		Mine m = getMines().get(args[1]);
		boolean silent = false;
		
		if(m == null){
			p.sendMessage(getMessage("INVALID_MINE", p).replace("{0}", args[1]));
			return;
		}
		
		if(args.length == 3)
			if(args[2].equals("-s")){
				silent = true;
			}else{
				p.sendMessage("§cUsage: /mines reset <name> [-s]");
				return;
			}
		
		m.reset();
		
		if(silent)
			p.sendMessage(getMessage("MINE_RESET_SILENT", p).replace("{0}", m.getName()));
		
		else for(Player pl : Bukkit.getOnlinePlayers())
			pl.sendMessage(getMessage("MINE_RESET_BROADCAST", p).replace("{0}", m.getName()));
		
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
