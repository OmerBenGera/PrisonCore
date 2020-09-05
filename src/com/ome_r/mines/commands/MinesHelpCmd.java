package com.ome_r.mines.commands;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.commands.SubCommands;

@Command(name = "help", aliases = "", args = "[page#] ", perm = "prisoncore.mines",
			desc = "Shows the list of commands of this plugin.", minArgs = 0, maxArgs = 1)
public class MinesHelpCmd extends CommandsHandle{

	// mines help [page]
	@Override
	public void run(Player p, String[] args) {
		int page = 1, maxPages = SubCommands.getMaxPages(p);
		
		if(args.length == 2)
			if(!NumberUtils.isNumber(args[1])){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", args[1]));
				return;
			}else page = Integer.valueOf(args[1]);
		
		if(page > maxPages){
			p.sendMessage(getMessage("MAX_PAGES", p).replace("{0}", maxPages + ""));
			return;
		}
		
		List<CommandsHandle> cmds = SubCommands.getCommands(p, page);
		
		String commands = "";
		
		for(CommandsHandle c : cmds){
			Command data = c.getClass().getAnnotation(Command.class);
			commands += getMessage("COMMANDS_FORMAT", p).replace("{0}", "mines").replace("{1}", data.name())
					.replace("{2}", data.desc() + "\n");
		}
		
		p.sendMessage(getMessage("COMMANDS_LIST", p).replace("{0}", "Mines").replace("{1}", page + "")
				.replace("{2}", maxPages + "").replace("{3}", commands).replace("{4}", "mines"));
	}
	
	

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
