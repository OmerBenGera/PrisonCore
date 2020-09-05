package com.ome_r.mines.commands;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.Mine;
import com.ome_r.mines.utils.PMaterial;

@Command(name = "set", aliases = "", args = "<mine> <material> <chance> ", perm = "prisoncore.mines.set",
			desc = "Set a material.", minArgs = 3, maxArgs = 3)
public class MinesSetCmd extends CommandsHandle{

	// mines set <mine> <material> <chance>
	@Override
	public void run(Player p, String[] args) {
		Mine mine = getMines().get(args[1]);
		int chance;
		PMaterial mat;
		
		if(!getMines().contains(args[1])){
			p.sendMessage(getMessage("INVALID_MINE", p).replace("{0}", args[1]));
			return;
		}
		
		if(!NumberUtils.isNumber(args[3])){
			p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", args[3]));
			return;
		}
		chance = Integer.valueOf(args[3]);
		mat = getMaterial(args[2], chance);
		
		if(mat == null){
			p.sendMessage(getMessage("INVALID_MATERIAL", p).replace("{0}", args[2]));
			return;
		}
		
		if(chance < 0 || chance > 100){
			p.sendMessage(getMessage("INVALID_PERCENTAGE", p));
			return;
		}
		
		if(!mine.containsMaterial(mat) && chance == 0){
			p.sendMessage(getMessage("INVALID_MATERIAL_IN_MINE", p)
					.replace("{0}", mine.getName()).replace("{1}", mat.getMaterial().name()));
			return;
		}
		
		if(mine.getPercentageWithout(mat) + chance > 100){
			String materials = "";
			
			for(PMaterial pM : mine.getMaterials())
				materials += getMessage("MAXIMUM_PERCENTAGE_MATERIAL", p).replace("{0}", pM.getMaterial().name())
					.replace("{1}", pM.getData() + "").replace("{2}", pM.getChance() + "\n");
			
			p.sendMessage(getMessage("MAXIMUM_PERCENTAGE", p).replace("{0}", (100 - mine.getPercentage()) + "")
					.replace("{1}", materials));
			return;
		}
		
		mine.addMaterial(mat);
		p.sendMessage(getMessage("ADD_NEW_MATERIAL", p).replace("{0}", args[2]).replace("{1}", chance + "")
				.replace("{2}", mine.getName()));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private PMaterial getMaterial(String s, int chance){
		Material type;
		byte data = 0;
		
		if(NumberUtils.isNumber(s)){
			type = Material.getMaterial(Integer.parseInt(s));
		}
		
		else{
			if(s.contains(":")){
				type = Material.getMaterial(s.toUpperCase().split(":")[0]);
				data = Byte.parseByte(s.split(":")[1]);
			}else type = Material.getMaterial(s.toUpperCase());
		}
		
		return type == null ? null : new PMaterial(type, data, chance);
	}
	
}
