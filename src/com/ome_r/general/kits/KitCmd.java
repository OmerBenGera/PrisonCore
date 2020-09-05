package com.ome_r.general.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@Command(name = "kit", aliases = "", args = "[name]", perm = "prisoncore.kit",
			desc = "Gives a specific kit.", minArgs = 0, maxArgs = 1)
public class KitCmd extends CommandsHandle{

	public static List<Kit> kits = new ArrayList<>();
	
	@Override
	public void run(Player p, String[] args) {
		String perm = getClass().getAnnotation(Command.class).perm();
		
		if(args.length == 0){
			String kit = "";
			int size = 0;
			
			for(Kit k : kits)
				if(p.hasPermission(perm + "." + k.getName()) || p.hasPermission(perm + ".*")){
					if(kit.equals("")) kit += "§7" + k.getName();
					else kit += "§a, §7" + k.getName();
					size++;
				}
			
			p.sendMessage("§aKits (" + size + "): " + kit);
			return;
		}
		
		if(!contains(args[0]) || (!p.hasPermission(perm + "." + args[0]) && !p.hasPermission(perm + ".*"))){
			p.sendMessage("§cCouldn't find a kit called " + args[0] + ".");
			return;
		}
		
		Kit kit = get(args[0]);
		
		if(!p.hasPermission(perm + ".bypass") && !p.hasPermission(perm + ".*")){
			if(kit.getTime(p.getUniqueId()) > (System.currentTimeMillis() / 1000)){
				int timeLeft = (int) (kit.getTime(p.getUniqueId()) - (System.currentTimeMillis() / 1000L));
				p.sendMessage("§cPlease wait " + timeLeft + " seconds till using this kit again.");
				return;
			}else kit.removePlayer(p.getUniqueId());
		}
		
		for(ItemStack is : kit.getItems())
			if(is != null) p.getInventory().addItem(is);
		
		kit.addPlayer(p.getUniqueId());
		p.sendMessage("§aRedeemed the kit §7" + kit.getName() + "§a.");
		
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}
	
	public static boolean contains(String kit){
		for(Kit k : kits)
			if(kit.equalsIgnoreCase(k.getName())) return true;
		
		return false;
	}
	
	public static Kit get(String kit){
		for(Kit k : kits)
			if(kit.equalsIgnoreCase(k.getName())) return k;
		
		return null;
	}
	
	

}
