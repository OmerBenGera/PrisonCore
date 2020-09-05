package com.ome_r.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.ome_r.PrisonCore;
import com.ome_r.rankups.Rankup;

public class AsyncPlayerChatListener implements Listener{

	public AsyncPlayerChatListener(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e){
		if(!e.getFormat().contains("$prefix")) return;
		
		Player p = e.getPlayer();
		Rankup rank = PrisonCore.getRankups().get(p);
		
		String prefix = "";
		if(PrisonCore.getRankups().contains(rank))
			prefix = rank.getPrefix();
		
		e.setFormat(e.getFormat().replace("$prefix", prefix));
	}
	
}
