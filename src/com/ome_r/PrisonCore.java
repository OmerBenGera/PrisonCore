package com.ome_r;

import java.lang.reflect.Field;
import java.util.Arrays;

import com.ome_r.plots.Plots;
import com.ome_r.plots.generator.PlotsGen;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.ome_r.commands.Commands;
import com.ome_r.commands.SubCommands;
import com.ome_r.data.DataAnimations;
import com.ome_r.data.DataConfig;
import com.ome_r.data.DataMessages;
import com.ome_r.data.DataMultipliers;
import com.ome_r.data.DataRankups;
import com.ome_r.data.MySQL;
import com.ome_r.economy.Economy_PrisonCore;
import com.ome_r.listeners.AsyncPlayerChatListener;
import com.ome_r.listeners.BlockBreakListener;
import com.ome_r.listeners.PlayerCommandPreprocessListener;
import com.ome_r.listeners.PlayerJoinListener;
import com.ome_r.listeners.PlayerQuitListener;
import com.ome_r.mines.Mines;
import com.ome_r.rankups.Rankups;
import com.ome_r.scoreboard.Boards;
import com.ome_r.shops.Shops;
import com.ome_r.shops.multipliers.Multipliers;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class PrisonCore extends JavaPlugin {

	private static PrisonCore instance;
	private static Mines mines;
	private static Shops shops;
	private static Rankups rankups;
	private static Boards boards;
	private static Multipliers multipliers;
	private static Plots plots;
	private static Economy economy;
	private static Permission permission;
	private static WorldEditPlugin worldEdit;
	
	private static DataAnimations dataAnimations;
	private static DataConfig dataConfig;
	private static DataMessages dataMessages;
	private static DataMultipliers dataMultipliers;
	private static DataRankups dataRankups;
	private static MySQL mysql;

	private static PlotsGen gen;

	public boolean placeHolders;
	
	@Override
	public void onEnable() {
		if(!setupClasses()) return;
		loadPlaceHolderAPI();
		loadCommands();
		loadListeners();
		loadData();
		loadMySQL();
		mines.runTask();
		boards.runAnimationsTask();
		boards.runTask();
		mysql.runTask();
		
		if(!mysql.isConnected())
			Bukkit.getPluginManager().disablePlugin(this);

	}
	
	@Override
	public void onDisable() {
		if(mysql != null){
			mysql.saveMines();
			mysql.saveShops();
			mysql.saveEconomy();
			mysql.saveKits();
			
			mysql.disconnect();
		}
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return gen;
	}

	private void loadCommands(){
		try {
			Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f.setAccessible(true);
			CommandMap cmap = (CommandMap) f.get(Bukkit.getServer());
			cmap.registerAll("prisoncore", Arrays.asList(
					new SubCommands("mines"), 
					new SubCommands("shops"), 
					new SubCommands("eco"),
					new SubCommands("multipliers"),
					new SubCommands("plots"),
					new Commands("warp"), 
					new Commands("setwarp"),
					new Commands("prisoncore", Arrays.asList("pc")), 
					new Commands("money", Arrays.asList("bal", "balance")), 
					new Commands("ranks"),
					new Commands("rankup"),
					new Commands("sell"),
					new Commands("teleport", Arrays.asList("tp")),
					new Commands("kit"),
					new Commands("savekit")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadListeners(){
		new BlockBreakListener(this);
		new AsyncPlayerChatListener(this);
		new PlayerCommandPreprocessListener(this);
		new PlayerJoinListener(this);
		new PlayerQuitListener(this);
	}
	
	public void loadData(){
		dataAnimations.setStandard();
		dataConfig.setStandard();
		dataMessages.setStandard();
		dataMultipliers.setStandard();
		dataRankups.setStandard();
	}
	
	private void loadMySQL(){
		mysql.connect();
		mysql.loadMines();
		mysql.loadShops();
		mysql.loadEconomy();
		mysql.loadKits();
	}
	
	private void loadPlaceHolderAPI(){
		this.placeHolders = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
	}
	
	private boolean setupClasses(){
		PluginManager pm = getServer().getPluginManager();
		
		if(!pm.isPluginEnabled("WorldEdit")){
			System.err.println("##############################################");
			System.err.println("##                                          ##");
			System.err.println("##  [PrisonCore] Please install WorldEdit!  ##");
			System.err.println("##                                          ##");
			System.err.println("##############################################");
			
			pm.disablePlugin(this);
			return false;
		}
		
		else if(!pm.isPluginEnabled("Vault")){
			System.err.println("##########################################");
			System.err.println("##                                      ##");
			System.err.println("##  [PrisonCore] Please install Vault!  ##");
			System.err.println("##                                      ##");
			System.err.println("##########################################");
			
			pm.disablePlugin(this);
			return false;
		}
		
		getServer().getServicesManager().register(Economy.class, new Economy_PrisonCore(), this, ServicePriority.Highest);
		
		instance = this;
		mines = new Mines();
		shops = new Shops();
		rankups = new Rankups();
		boards = new Boards();
		multipliers = new Multipliers();
		plots = new Plots();
		worldEdit = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
		economy = getRegistration(Economy.class).getProvider();
		permission = getRegistration(Permission.class).getProvider();
		mysql = new MySQL();
		
		dataAnimations = new DataAnimations();
		dataConfig = new DataConfig();
		dataMessages = new DataMessages();
		dataMultipliers = new DataMultipliers();
		dataRankups = new DataRankups();

		gen = new PlotsGen();

		return true;
		
	}
	
	private <T> RegisteredServiceProvider<T> getRegistration(Class<T> clazz){
		return getServer().getServicesManager().getRegistration(clazz);
	}
	
	public static PrisonCore getInstance(){
		return instance;
	}
	
	public static MySQL getMySQL(){
		return mysql;
	}
	
	public static Mines getMines(){
		return mines;
	}
	
	public static Shops getShops(){
		return shops;
	}
	
	public static Rankups getRankups(){
		return rankups;
	}
	
	public static Boards getBoards(){
		return boards;
	}
	
	public static Multipliers getMultipliers(){
		return multipliers;
	}

	public static Plots getPlots(){
		return plots;
	}
	
	public static Economy getEconomy(){
		return economy;
	}
	
	public static Permission getPermission(){
		return permission;
	}
	
    public static WorldEditPlugin getWorldEdit() {
    	return worldEdit;
    }
    
}
