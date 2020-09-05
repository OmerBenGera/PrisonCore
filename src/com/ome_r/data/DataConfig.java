package com.ome_r.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ome_r.PrisonCore;
import com.ome_r.listeners.PlayerCommandPreprocessListener;

public class DataConfig {
	
	private File file;
	
	public DataConfig(){
		file = new File("plugins/PrisonCore/config.yml");
	}
	
	public void setStandard(){
		if(!file.exists())
			PrisonCore.getInstance().saveResource("config.yml", false);
		
		readData();
	}
	
	public void readData(){
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		//----------------------------------------//
		//                 MySQL                  //
		//----------------------------------------//
		
		if(!PrisonCore.getMySQL().isConnected())
			PrisonCore.getMySQL().load(
					cfg.getString("mysql.hostname"), 
					cfg.getString("mysql.port"), 
					cfg.getString("mysql.database"), 
					cfg.getString("mysql.username"), 
					cfg.getString("mysql.password"));
		PrisonCore.getMySQL().setSaveInterval(cfg.getLong("save-interval"));
		
		//----------------------------------------//
		//              Scoreboards               //
		//----------------------------------------//
		
		String name = cfg.getString("scoreboard.title");
		int interval = cfg.getInt("scoreboard.interval");
		List<String> lines = new ArrayList<>();
		for(String s : cfg.getStringList("scoreboard.lines"))
			lines.add(ChatColor.translateAlternateColorCodes('&', s));
		
		PrisonCore.getBoards().load(name, interval, lines);
		
		//----------------------------------------//
		//            Blocked Commands            //
		//----------------------------------------//
		
		PlayerCommandPreprocessListener.blockedCommands.clear();
		for(String s : cfg.getStringList("blocked-commands"))
			PlayerCommandPreprocessListener.blockedCommands.add(s);
		
	}
	
	public YamlConfiguration getConfig(){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public File getFile(){
		return file;
	}
	
}