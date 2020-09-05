package com.ome_r.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.ome_r.PrisonCore;

public class PlayerQuitListener implements Listener{

	public PlayerQuitListener(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		PrisonCore.getBoards().destroy(e.getPlayer());
	}
	
}
