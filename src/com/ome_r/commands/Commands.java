package com.ome_r.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import com.ome_r.economy.commands.MoneyCmd;
import com.ome_r.general.ReloadCmd;
import com.ome_r.general.TeleportCmd;
import com.ome_r.general.kits.KitCmd;
import com.ome_r.general.kits.SavekitCmd;
import com.ome_r.general.warps.SetwarpCmd;
import com.ome_r.general.warps.WarpCmd;
import com.ome_r.rankups.commands.RanksCmd;
import com.ome_r.rankups.commands.RankupCmd;
import com.ome_r.shops.commands.SellCmd;

public class Commands extends BukkitCommand{

	public Commands(String name) {
		super(name, desc(name), "", new ArrayList<>());
	}
	
	public Commands(String name, List<String> aliases){
		super(name, desc(name), "", aliases);
	}

	private static List<CommandsHandle> cmds;
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("§cError: Only players can run this command.");
			return false;
		}
		Player p = (Player) sender;
		
		Collections.sort(cmds);
		
		for(CommandsHandle c : cmds){
			Command data = c.getClass().getAnnotation(Command.class);
			
			if(!Arrays.asList(data.aliases().split(",")).contains(label.toLowerCase()) 
					&& !label.equalsIgnoreCase(data.name())) continue;
			
			if(!p.hasPermission(data.perm()) && !data.perm().equals("")){
				p.sendMessage(CommandsHandle.getMessage("NO_PERMISSION", p));
				return false;
			}
			
			if(!(args.length >= data.minArgs()) || !(args.length <= data.maxArgs())){
				p.sendMessage("§cUsage: /" + label + " " + data.args());
				return false;
			}
			
			c.run(p, args);
			return false;
		}
		
		return false;
	}
	
	private static String desc(String name){
		for(CommandsHandle c : cmds){
			Command data = c.getClass().getAnnotation(Command.class);
			if(name.equalsIgnoreCase(data.name())) return data.desc();
		}
		
		return "";
	}
	
	static{
		cmds = new ArrayList<>();
		cmds.add(new SetwarpCmd());
		cmds.add(new WarpCmd());	
		cmds.add(new ReloadCmd());
		cmds.add(new MoneyCmd());
		cmds.add(new RanksCmd());
		cmds.add(new RankupCmd());
		cmds.add(new SellCmd());
		cmds.add(new TeleportCmd());
		cmds.add(new KitCmd());
		cmds.add(new SavekitCmd());
	}
	
}
