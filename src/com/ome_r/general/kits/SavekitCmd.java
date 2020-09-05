package com.ome_r.general.kits;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.general.warps.WarpCmd;

@Command(name = "savekit", aliases = "", args = "<name> [delay]", perm = "prisoncore.savekit",
			desc = "Creates a new kit inventory.", minArgs = 1, maxArgs = 2)
public class SavekitCmd extends CommandsHandle{
	
	// setwarp <name> [delay]
	@Override
	public void run(Player p, String[] args) {
		if(WarpCmd.contains(args[0])){
			p.sendMessage("§cKit " + args[0] + " already exist.");
		}
		
		List<ItemStack> itemStacks = new ArrayList<>();
		for(ItemStack is : p.getInventory().getContents())
			if(is != null) itemStacks.add(is);
		
		if(KitCmd.contains(args[0]))
			KitCmd.kits.remove(KitCmd.get(args[0]));
		
		if(args.length == 2){
			if(!NumberUtils.isNumber(args[1])){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", args[1]));
				return;
			}
			KitCmd.kits.add(new Kit(args[0], itemStacks, Long.parseLong(args[1])));
		}
		
		else KitCmd.kits.add(new Kit(args[0], itemStacks));
		
		p.sendMessage("§aSuccessfully changed the inventory of the kit §7" + args[0] + "§a.");
		
		return;
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
