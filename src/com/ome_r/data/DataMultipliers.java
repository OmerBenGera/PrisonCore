package com.ome_r.data;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.ome_r.PrisonCore;

public class DataMultipliers {
	
	private File file;
	
	public DataMultipliers(){
		file = new File("plugins/PrisonCore/multipliers.yml");
	}
	
	public void setStandard(){
		if(!file.exists())
			PrisonCore.getInstance().saveResource("multipliers.yml", false);
		
		readData();
	}
	
	public void readData(){
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		for(String group : cfg.getConfigurationSection("groups").getKeys(true))
			if(!group.contains("."))
				PrisonCore.getMultipliers().set(group, cfg.getInt("groups." + group));
		
	}
	
	public YamlConfiguration getConfig(){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public File getFile(){
		return file;
	}
	
}