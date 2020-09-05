package com.ome_r.general.warps;

import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@Command(name = "setwarp", aliases = "", args = "<name>", perm = "prisoncore.setwarp",
			desc = "Creates a new warp location.", minArgs = 1, maxArgs = 1)
public class SetwarpCmd extends CommandsHandle{
	
	// setwarp <name>
	@Override
	public void run(Player p, String[] args) {
		if(WarpCmd.contains(args[0])){
			p.sendMessage("§cWarp " + args[0] + " already exist.");
		}
		
		WarpCmd.warps.put(args[0], p.getLocation());
		p.sendMessage("§aSuccessfully changed the location of the warp §7" + args[0] + "§a.");
		
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
