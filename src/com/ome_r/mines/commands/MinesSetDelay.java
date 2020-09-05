package com.ome_r.mines.commands;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.mines.Mine;

@Command(name = "setdelay", aliases = "delay", args = "<name> <delay> ", perm = "prisoncore.mines.setdelay",
			desc = "Change the reset time delay of a mine.", minArgs = 2, maxArgs = 2)
public class MinesSetDelay extends CommandsHandle {

	// mines setdelay <name> <delay>
	@Override
	public void run(Player p, String[] args) {
		Mine mine = getMines().get(args[1]);
		long delay;
		
		if(!getMines().contains(mine)){
			p.sendMessage(getMessage("INVALID_MINE", p).replace("{0}", args[1]));
			return;
		}
		
		if(!NumberUtils.isNumber(args[2])){
			p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", args[2]));
			return;
		}
		delay = Long.parseLong(args[2]);
		
		mine.setDelay(delay);
		p.sendMessage(getMessage("CHANGE_DELAY", p).replace("{0}", mine.getName()).replace("{1}", delay + ""));
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
