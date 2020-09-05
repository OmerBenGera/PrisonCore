package com.ome_r.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ome_r.plots.commands.PlotsInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.ome_r.economy.commands.EcoGiveCmd;
import com.ome_r.economy.commands.EcoHelpCmd;
import com.ome_r.economy.commands.EcoSetCmd;
import com.ome_r.economy.commands.EcoTakeCmd;
import com.ome_r.mines.commands.MinesCreateCmd;
import com.ome_r.mines.commands.MinesDeleteCmd;
import com.ome_r.mines.commands.MinesHelpCmd;
import com.ome_r.mines.commands.MinesInfoCmd;
import com.ome_r.mines.commands.MinesListCmd;
import com.ome_r.mines.commands.MinesResetCmd;
import com.ome_r.mines.commands.MinesSetCmd;
import com.ome_r.mines.commands.MinesSetDelay;
import com.ome_r.shops.commands.ShopsCreateCmd;
import com.ome_r.shops.commands.ShopsDeleteCmd;
import com.ome_r.shops.commands.ShopsHelpCmd;
import com.ome_r.shops.commands.ShopsInfoCmd;
import com.ome_r.shops.commands.ShopsListCmd;
import com.ome_r.shops.commands.ShopsSetCmd;
import com.ome_r.shops.commands.ShopsSetMineCmd;
import com.ome_r.shops.multipliers.commands.MultipliersHelpCmd;
import com.ome_r.shops.multipliers.commands.MultipliersSetCmd;

public class SubCommands extends BukkitCommand {

	private static List<CommandsHandle> cmds, mineCmds, shopCmds, ecoCmds, multiCmds, plotCmds;
	
	public SubCommands(String name) {
		super(name, desc(name), "", new ArrayList<>());
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("§cError: Only players can run this command.");
			return false;
		}
		
		Player p = (Player) sender;
		
		if(label.endsWith("mines"))
			cmds = mineCmds;
		else if(label.endsWith("shops"))
			cmds = shopCmds;
		else if(label.endsWith("eco"))
			cmds = ecoCmds;
		else if(label.endsWith("multipliers"))
			cmds = multiCmds;
		else if(label.endsWith("plots"))
			cmds = plotCmds;
		
		Collections.sort(cmds);
		
		if(args.length > 0)
			for(CommandsHandle c : cmds){
				Command data = c.getClass().getAnnotation(Command.class);
				
				if(!Arrays.asList(data.aliases().split(",")).contains(args[0].toLowerCase()) 
						&& !args[0].equalsIgnoreCase(data.name())) continue;
				
				if(data.name().equals("help") && !p.hasPermission(data.perm()) && !data.perm().equals("")){
					p.sendMessage(CommandsHandle.getMessage("NO_PERMISSION", p));
					return false;
				}
				
				if(!data.perm().equals("") && !p.hasPermission(data.perm())){
					Bukkit.dispatchCommand(p, label + " help 1");
					return false;
				}
				
				if(!(args.length >= (data.minArgs() + 1)) || !(args.length <= (data.maxArgs() + 1))){
					p.sendMessage("§cUsage: /" + label + " " + data.name() + " " + data.args());
					return false;
				}
				
				c.run(p, args);
				return false;
			}
		
		Bukkit.dispatchCommand(p, label + " help 1");
		
		return false;
	}
	
	public static List<CommandsHandle> getCommands(Player p, int page){
		List<CommandsHandle> list = new ArrayList<>(),
				cmdsList = new ArrayList<>(cmds);
		
		int counter = 0;
		for(int i = ((page - 1) * 10); i < cmds.size(); i++){
			String permission = cmds.get(i).getClass().getAnnotation(Command.class).perm();
			if(cmds.size() <= i || counter == 10) break;
			else if(permission.equals("") || p.hasPermission(permission)){
				list.add(cmdsList.get(i));
				counter++;
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public static int getMaxPages(Player p){
		List<CommandsHandle> list = new ArrayList<>();
		
		for(CommandsHandle c : cmds){
			String permission = c.getClass().getAnnotation(Command.class).perm();
			if(permission.equals("") || p.hasPermission(permission))
				list.add(c);
		}
		
		if(list.size() % 10 != 0) return (list.size() / 10) + 1;
		
		return cmds.size() / 10;
	}
	
	private static String desc(String name){
		switch(name){
		case "mines":
			return "Shows the list of commands for mines management.";
		case "shops":
			return "Shows the list of commands for shops management.";
		case "eco":
			return "Shows the list of commands for economy management.";
		case "multipliers":
			return "Shows the list of commands for multipliers management.";
		default:
			return "";
		}
	}
	
	static{
		mineCmds = new ArrayList<>();
		mineCmds.add(new MinesCreateCmd());
		mineCmds.add(new MinesSetCmd());
		mineCmds.add(new MinesResetCmd());
		mineCmds.add(new MinesHelpCmd());
		mineCmds.add(new MinesListCmd());
		mineCmds.add(new MinesDeleteCmd());
		mineCmds.add(new MinesInfoCmd());
		mineCmds.add(new MinesSetDelay());
	
		shopCmds = new ArrayList<>();
		shopCmds.add(new ShopsCreateCmd());
		shopCmds.add(new ShopsSetCmd());
		shopCmds.add(new ShopsHelpCmd());
		shopCmds.add(new ShopsListCmd());
		shopCmds.add(new ShopsInfoCmd());
		shopCmds.add(new ShopsSetMineCmd());
		shopCmds.add(new ShopsDeleteCmd());
	
		ecoCmds = new ArrayList<>();
		ecoCmds.add(new EcoHelpCmd());
		ecoCmds.add(new EcoSetCmd());
		ecoCmds.add(new EcoGiveCmd());
		ecoCmds.add(new EcoTakeCmd());
	
		multiCmds = new ArrayList<>();
		multiCmds.add(new MultipliersSetCmd());
		multiCmds.add(new MultipliersHelpCmd());

		plotCmds = new ArrayList<>();
		plotCmds.add(new PlotsInfo());
	}

}
