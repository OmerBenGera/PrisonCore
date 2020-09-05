package com.ome_r.general;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.ome_r.commands.Command;
import com.ome_r.commands.CommandsHandle;

@Command(name = "teleport", aliases = "tp", args = "[player] <player OR x y z>", perm = "prisoncore.teleport",
			desc = "Teleports you to a specific player or coords.", minArgs = 1, maxArgs = 4)
public class TeleportCmd extends CommandsHandle{

	// tp [player] <player OR x y z>
	@Override
	public void run(Player p, String[] args) {
		if(args.length == 1){ //tp <player>
			Player p1 = Bukkit.getPlayer(args[0]);
			if(p1 == null){
				p.sendMessage(getMessage("INVALID_PLAYER", p).replace("{0}", args[0]));
				return;
			}
			
			p.teleport(p1);
			p.sendMessage("§aTeleported §7" + p.getName() + " §ato §7" + p1.getName() + "§a.");
			return;
		}
		
		else if(args.length == 2){ //tp <player1> <player2>
			Player p1 = Bukkit.getPlayer(args[0]), p2 = Bukkit.getPlayer(args[1]);
			if(p1 == null){
				p.sendMessage(getMessage("INVALID_PLAYER", p).replace("{0}", args[0]));
				return;
			}else if(p2 == null){
				p.sendMessage(getMessage("INVALID_PLAYER", p).replace("{0}", args[1]));
				return;
			}
			
			p1.teleport(p2);
			p.sendMessage("§aTeleported §7" + p1.getName() + " §ato §7" + p2.getName() + "§a.");
			return;
		}
		
		else if(args.length == 3){ //tp <x> <y> <z>
			String x = args[0], y = args[1], z = args[2];
			
			if(!NumberUtils.isNumber(x)){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", x));
				return;
			}else if(!NumberUtils.isNumber(y)){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", y));
				return;
			}else if(!NumberUtils.isNumber(z)){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", z));
				return;
			}
			
			p.teleport(new Location(p.getWorld(), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)));
			p.sendMessage("§aTeleported §7" + p.getName() + " §ato §7" + x + ", " + y + ", " + z + "§a.");
			return;
		}
		
		else if(args.length == 4){ //tp <player> <x> <y> <z>
			String x = args[1], y = args[2], z = args[3];
			Player p1 = Bukkit.getPlayer(args[0]);
			
			if(p1 == null){
				p.sendMessage(getMessage("INVALID_PLAYER", p).replace("{0}", args[0]));
				return;
			}else if(!NumberUtils.isNumber(x)){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", x));
				return;
			}else if(!NumberUtils.isNumber(y)){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", y));
				return;
			}else if(!NumberUtils.isNumber(z)){
				p.sendMessage(getMessage("INVALID_NUMBER", p).replace("{0}", z));
				return;
			}
			
			p1.teleport(new Location(p1.getWorld(), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)));
			p.sendMessage("§aTeleported §7" + p1.getName() + " §ato §7" + x + ", " + y + ", " + z + "§a.");
			return;
		}
		
	}

	@Override
	public List<String> getTabComplete(Player p, String[] args) {
		return null;
	}

}
