package com.ome_r.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.ome_r.PrisonCore;
import com.ome_r.scoreboard.Board;

public class PlayerJoinListener implements Listener{

	public PlayerJoinListener(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Board b = PrisonCore.getBoards().create(e.getPlayer());
		b.updateLines(true);
	}
	
}
