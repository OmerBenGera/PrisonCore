package com.ome_r.general.warps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@Command(name = "warp", aliases = "", args = "[name]", perm = "prisoncore.warp",
			desc = "Teleports the player to a specific warp.", minArgs = 0, maxArgs = 1)
public class WarpCmd extends CommandsHandle{

	public static Map<String, Location> warps;
	
	public WarpCmd() {
		warps = new HashMap<>();
	}
	
	//warp [name]
	@Override
	public void run(Player p, String[] args) {
		String perm = getClass().getAnnotation(Command.class).perm();
		
		if(args.length == 0){
			String warp = "";
			int size = 0;
			
			for(String s : warps.keySet())
				if(p.hasPermission(perm + "." + s) || p.hasPermission(perm + ".*")){
					if(warp.equals("")) warp += "§7" + s;
					else warp += "§a, I7" + s;
					size++;
				}
			
			p.sendMessage("§aWarps (" + size + "): " + warp);
			return;
		}
		
		if(!contains(args[0]) || (!p.hasPermission(perm + "." + args[0]) && !p.hasPermission(perm + ".*"))){
			p.sendMessage("§cCouldn't find a warp called " + args[0] + ".");
			return;
		}
		
		String warp = get(args[0]);
		
		p.teleport(warps.get(warp));
		p.sendMessage("§aTeleported you to §7" + warp + "§a.");
		
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	public static boolean contains(String warp){
		for(String s : warps.keySet())
			if(warp.equalsIgnoreCase(s)) return true;
		
		return false;
	}
	
	public static String get(String warp){
		for(String s : warps.keySet())
			if(warp.equalsIgnoreCase(s)) return s;
		
		return null;
	}

}
