package com.ome_r.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ome_r.PrisonCore;

public class DataAnimations {
	
	private File file;
	
	public DataAnimations(){
		file = new File("plugins/PrisonCore/animations.yml");
	}
	
	public void setStandard(){
		if(!file.exists())
			PrisonCore.getInstance().saveResource("animations.yml", false);
		
		readData();
	}
	
	public void readData(){
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		PrisonCore.getBoards().clearAnimations();
		
		for(String s : cfg.getConfigurationSection("animations").getKeys(true))
			if(!s.contains(".")){
				List<String> list = new ArrayList<>();
				
				for(String str : cfg.getStringList("animations." + s))
					list.add(ChatColor.translateAlternateColorCodes('&', str));
				
				PrisonCore.getBoards().loadAnimation(s, list);
			}
		
	}
	
	public YamlConfiguration getConfig(){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public File getFile(){
		return file;
	}
	
}
