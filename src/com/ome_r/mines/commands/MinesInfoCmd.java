package com.ome_r.mines.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.Mine;
import com.ome_r.mines.utils.PMaterial;

@Command(name = "info", aliases = "show", args = "<name> ", perm = "prisoncore.mines.info",
			desc = "Shows information about a mine.", minArgs = 1, maxArgs = 1) 
public class MinesInfoCmd extends CommandsHandle{

	// mines info <name>
	@Override
	public void run(Player p, String[] args) {
		Mine mine = getMines().get(args[1]);
		
		if(!getMines().contains(mine)){
			p.sendMessage(getMessage("INVALID_MINE", p).replace("{0}", args[1]));
			return;
		}
		
		Location loc = mine.getLocations()[0];
		String materials = "none";
		
		for(PMaterial pM : mine.getMaterials()){
			String materialsFormat = getMessage("MATERIALS_INFO_FORMAT", p).replace("{0}", getType(pM.getMaterial()))
					.replace("{1}", pM.getData() + "").replace("{2}", pM.getChance() + "");
			if(materials.equals("none"))
				materials = materialsFormat;
			else materials += getMessage("MATERIALS_INFO_SPACER", p) + materialsFormat;
		}
		
		p.sendMessage(getMessage("MINE_INFO", p).replace("{0}", mine.getName())
				.replace("{1}", loc.getWorld().getName() + ", " + 
						loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ())
				.replace("{2}", mine.getPermission()).replace("{3}", (mine.getDelay() / 60) + " minutes")
				.replace("{4}", materials));
		
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	private String getType(Material type){
		String str = type.name();
		
		if(str.contains("_"))
			for(String s : str.split("_"))
				if(str.equals(type.name())) str = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
				else str += " " + s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		else str = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
		
		return str;
	}

}
