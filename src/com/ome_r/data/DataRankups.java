package com.ome_r.data;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ome_r.PrisonCore;

public class DataRankups {
	
	private File file;
	
	public DataRankups(){
		file = new File("plugins/PrisonCore/rankups.yml");
	}
	
	public void setStandard(){
		if(!file.exists())
			PrisonCore.getInstance().saveResource("rankups.yml", false);
		
		readData();
	}
	
	public void readData(){
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		PrisonCore.getRankups().clear();
		
		for(String s : cfg.getConfigurationSection("rankups").getKeys(true))
			if(!s.contains(".")){
				String prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("rankups." + s + ".prefix")),
						rank = s, group = cfg.getString("rankups." + s + ".group");
				int level = cfg.getInt("rankups." + s + ".level");
				double price = cfg.getDouble("rankups." + s + ".price");
				List<String> commands = cfg.getStringList("rankups." + s + ".commands");
				
				PrisonCore.getRankups().load(rank, group, level, price, prefix, commands);
			}
		
	}
	
	public YamlConfiguration getConfig(){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public File getFile(){
		return file;
	}
	
}
