package com.ome_r.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import com.ome_r.commands.CommandsHandle;

public class PlayerCommandPreprocessListener implements Listener {
	
	public static List<String> blockedCommands = new ArrayList<>();
	
	public PlayerCommandPreprocessListener(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){
		Player p = e.getPlayer();
		String label = e.getMessage().substring(1);
		
		if(!e.getMessage().startsWith("/")) 
			return;
		
		for(String cmd : blockedCommands){
			if(label.equalsIgnoreCase(cmd)){
				e.setCancelled(true);
				p.sendMessage(CommandsHandle.getMessage("HELP_MESSAGE", p));
			}
		}
	}
	
}
