package com.ome_r.mines.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.Mine;

@Command(name = "delete", aliases = "", args = "<mine> ", perm = "prisoncore.mines.delete",
			desc = "Delete a mine.", minArgs = 1, maxArgs = 1)
public class MinesDeleteCmd extends CommandsHandle {

	@Override
	public void run(Player p, String[] args) {
		Mine m = getMines().get(args[1]);
		
		if(!getMines().contains(m)){
			p.sendMessage(getMessage("INVALID_MINE", p).replace("{0}", args[1]));
			return;
		}
		
		getMines().delete(m);
		p.sendMessage(getMessage("DELETE_MINE", p).replace("{0}", m.getName()));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
