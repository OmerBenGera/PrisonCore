package com.ome_r.mines.commands;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.sk89q.worldedit.bukkit.selections.Selection;

@Command(name = "create", aliases = "", args = "<name> ", perm = "prisoncore.mines.create",
			desc = "Create a new mine.", minArgs = 1, maxArgs = 1)
public class MinesCreateCmd extends CommandsHandle{
	
	// mines create <name>
	@Override
	public void run(Player p, String[] args) {
		Selection sel = getWorldEdit().getSelection(p);
		
		if(sel == null){
			p.sendMessage(getMessage("INVALID_SELECTION", p));
			return;
		}
		
		if(getMines().contains(args[1])){
			p.sendMessage(getMessage("MINE_ALREADY_EXISTS", p).replace("{0}", args[1]));
			return;
		}
		
		getMines().create(args[1], sel);
		p.sendMessage(getMessage("CREATE_MINE", p).replace("{0}", args[1]));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
