package com.ome_r.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ome_r.commands.CommandsHandle;

public class DataMessages {
	
	private Map<String, String> defaults;
	private File file;
	
	public DataMessages(){
		defaults = new HashMap<>();
		file = new File("plugins/PrisonCore/lang.yml");
		loadStandards();
	}
	
	public void setStandard(){
		if(!file.exists()){
			try {
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
				cfg.options().copyDefaults(true);
				
				List<String> list = new ArrayList<>(defaults.keySet());
				Collections.sort(list);
				
				for(String path : list)
					cfg.addDefault(path, defaults.get(path));
				
				cfg.save(getFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		readData();
	}
	
	public void readData(){
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		for(String path : defaults.keySet()){
			if(!cfg.contains(path)){
				try{
					 cfg.set(path, defaults.get(path));
					 cfg.save(file);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			CommandsHandle.messages.put(path, ChatColor.translateAlternateColorCodes('&', cfg.getString(path)));
		}
	}
	
	public YamlConfiguration getConfig(){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public File getFile(){
		return file;
	}
	
	private void loadStandards(){
		
		defaults.put("ADD_NEW_MATERIAL", "%prefix% &7Added the material {0} with the chance {1} to the mine {2}.");
		defaults.put("ADD_NEW_PRICE", "%prefix% &7Added the material {0} with the price {1} to the shop {2}.");
		defaults.put("CANNOT_SELL_ITEM", "&cYou cannot sell this item to this shop.");
		defaults.put("CHANGE_DELAY", "%prefix% &7Updated the delay of {0} to {1}.");
		defaults.put("CHANGE_SHOP_MINE", "%prefix% &7Updated the mine of the shop {0} to {1}.");
		defaults.put("COMMANDS_FORMAT", "  &e/{0} {1} &8- &7{2}");
		defaults.put("COMMANDS_LIST", "&8&m--------------------------------------------------\n&r %prefix% &e{0} Sub-Commands List: [Page {1}/{2}]\n{3}\n &6To view other pages, use &e/{4} help <page#>&6.\n&8&m--------------------------------------------------");
		defaults.put("CREATE_MINE", "%prefix% &7Created a new mine with the name {0}.");
		defaults.put("CREATE_SHOP", "%prefix% &7Created a new shop with the name {0}.");
		defaults.put("DELETE_MINE", "%prefix% &7Deleted the mine {0}.");
		defaults.put("DELETE_SHOP", "%prefix% &7Deleted the shop {0}.");
		defaults.put("HELP_MESSAGE", "&8&m------------------------------\n&e  Hello, %player%!\n&e  Welcome to &6&lSERVER&e!\n&8&m------------------------------");
		defaults.put("INVALID_MATERIAL", "&c{0} is not a valid material.");
		defaults.put("INVALID_MATERIAL_IN_MINE", "&cThe mine {0} doesn't contain the material {1}.");
		defaults.put("INVALID_MINE", "&cCouldn't find a mine with the name {0}.");
		defaults.put("INVALID_NUMBER", "&c{0} is not a valid number.");
		defaults.put("INVALID_PERCENTAGE", "&cPercentage should be a number between 0 to 100.");
		defaults.put("INVALID_PLAYER", "&cCouldn't find a player with the name {0}.");
		defaults.put("INVALID_SELECTION", "&cMake a region selection first.");
		defaults.put("INVALID_SHOP", "&cCouldn't find a shop with the name {0}.");
		defaults.put("LAST_RANK_FORMAT", "  &e{0} &7-> &eLast Rank {1}");
		defaults.put("MATERIALS_INFO_FORMAT", "&7{0}:{1}[{2}%]");
		defaults.put("MATERIALS_INFO_SPACER", "&7, ");
		defaults.put("MAX_PAGES", "&cThere are only {0} pages.");
		defaults.put("MAXIMUM_PERCENTAGE", "&cThe maximum percentage that left is {0}.\n&7{1}");
		defaults.put("MAXIMUM_PERCENTAGE_MATERIAL", "&7{0}:{1} - {2}");
		defaults.put("MINE_ALREADY_EXISTS", "&cMine {0} already exists.");
		defaults.put("MINE_INFO", "&8&m--------------------------------------------------\n&r %prefix% &e{0}:\n&e  Location: &7{1}\n&e  Permission: &7{2}\n&e  Delay: &7{3}\n&e  Materials: &7{4}\n&8&m--------------------------------------------------");
		defaults.put("MINE_RESET_AUTOMATICALLY", "%prefix% &7The mine {0} has been reset automatically.");
		defaults.put("MINE_RESET_BROADCAST", "%prefix% &7The mine {0} has been reset by %player%.");
		defaults.put("MINE_RESET_SILENT", "%prefix% &7The mine {0} has been reset successfully.");
		defaults.put("MINES_LIST", "&fMines ({0}):&r {1}");
		defaults.put("MINES_NAME", "&a{0}");
		defaults.put("MINES_SPACER", "&f, ");
		defaults.put("NO_PERMISSION", "&fUnknown command. Type \"/help\" for help.");
		defaults.put("NOT_ENOUGH_MONEY", "&cYou don't have enough money to rankup.");
		defaults.put("PREFIX", "&6&lPrisonCore");
		defaults.put("PRICE_FORMAT", "&7{0}:{1}[${2}]");
		defaults.put("PRICE_SPACER", "&7, ");
		defaults.put("RANKS_COMMAND", "&8&m--------------------------------------------------\n&r %prefix% &7Ranks:\n{0}\n&8&m--------------------------------------------------");
		defaults.put("RANKS_FORMAT", "  &e{0} &7-> &e{1} &7(${2}) {3}");
		defaults.put("RANKUP_LAST", "%prefix% &7You are in the last rank.");
		defaults.put("RANKUP_NO_ACCESS", "&cYou don't have access to rankup. Please contact the staff members.");
		defaults.put("SAVE_DATA", "%prefix% &7Saving data.");
		defaults.put("SAVE_DATA_COMPLETE", "%prefix% &7Saving data completed.");
		defaults.put("SHOP_ALREADY_EXISTS", "&cShop {0} already exists.");
		defaults.put("SHOP_INFO", "&8&m--------------------------------------------------\n&r %prefix% &e{0}:\n&e  Mine: &7{1}\n&e  Prices: &7{2}\n&8&m--------------------------------------------------");
		defaults.put("SHOPS_LIST", "&fShops ({0}):&r {1}");
		defaults.put("SHOPS_NAME", "&a{0}");
		defaults.put("SHOPS_SPACER", "&f, ");
		defaults.put("SOLD_ITEM", "&aSold {0} {1} for {2} dollars.");
		defaults.put("YOUAREHERE_FORMAT", "&aYOU ARE HERE!");
		
	}
	
}
