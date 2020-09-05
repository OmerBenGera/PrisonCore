package com.ome_r.mines.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.Mine;

@Command(name = "list", aliases = "", args = "", perm = "prisoncore.mines.list",
			desc = "Shows the list of mines.", minArgs = 0, maxArgs = 0)
public class MinesListCmd extends CommandsHandle {

	// mines list
	@Override
	public void run(Player p, String[] args) {
		String mines = "";
		int size = getMines().mines().size();
		
		for(Mine m : getMines().mines())
			if(mines.equals("")) mines += getMessage("MINES_NAME", p).replace("{0}", m.getName());
			else mines += getMessage("MINES_SPACER", p) + getMessage("MINES_NAME", p).replace("{0}", m.getName());
		
		p.sendMessage(getMessage("MINES_LIST", p).replace("{0}", size + "").replace("{1}", mines));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
