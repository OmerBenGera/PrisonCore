package com.ome_r.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.ome_r.PrisonCore;
import com.ome_r.commands.CommandsHandle;
import com.ome_r.economy.Economy_PrisonCore;
import com.ome_r.general.kits.Kit;
import com.ome_r.general.kits.KitCmd;
import com.ome_r.mines.Mine;
import com.ome_r.mines.utils.PMaterial;
import com.ome_r.shops.Shop;
import com.ome_r.utils.DataUtils;

@SuppressWarnings("deprecation")
public class MySQL {

	private String host, port, database, username, password;
	private static Connection con;
	private int task;
	private long saveInterval;

	public void load(String host, String port, String database, String username, String password){
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
	}

	public void connect(){
		if(!isConnected()){
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				System.out.println("[PrisonCore] Connected to database");

				getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS mine(mine_name VARCHAR(36),locations VARCHAR(255),delay INT(10),materials VARCHAR(255))").executeUpdate();
				getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS shop(shop_name VARCHAR(36),mine_name VARCHAR(36),prices VARCHAR(255))").executeUpdate();
				getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS economy(player_uuid VARCHAR(36),balance VARCHAR(50))").executeUpdate();
				getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS kits(kit_name VARCHAR(36),inventory VARCHAR(255),delay INT(10))").executeUpdate();
				
			} catch (SQLException e) {
				System.err.println("###############################################");
				System.err.println("##                                           ##");
				System.err.println("##  [PrisonCore] Couldn't connect to MySQL!  ##");
				System.err.println("##                                           ##");
				System.err.println("###############################################");
				return;
			}
		}
	}

	public void disconnect(){
		if(isConnected()){
			try {
				con.close();
				System.out.println("[PrisonCore] Disconnected from database");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected(){
		return con != null;
	}

	public void update(String sql){
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection(){
		return con;
	}

	public void saveMines(){
		if(isConnected())
			try{
				getConnection().prepareStatement("DELETE FROM mine").executeUpdate();
				
				for(Mine m : PrisonCore.getMines().mines()){
					String name = m.getName(), materials = "",
							locations = getLocation(m.getLocations()[0]) + "," + getLocation(m.getLocations()[1]);
					int delay = (int) m.getDelay();

					for(PMaterial pM : m.getMaterials())
						if(materials.equals("")) 
							materials = pM.getMaterial().getId() + ":" + pM.getData() + ";" + pM.getChance();
						else materials += "," + pM.getMaterial().getId() + ":" + pM.getData() + ";" + pM.getChance();

					PreparedStatement ps = getConnection()
							.prepareStatement("INSERT INTO mine(mine_name,locations,delay,materials) VALUES(?,?,?,?)");

					ps.setString(1, name);
					ps.setString(2, locations);
					ps.setInt(3, delay);
					ps.setString(4, materials);
					ps.executeUpdate();
				}

			} catch (SQLException e){
				e.printStackTrace();
			}
	}

	public void loadMines(){
		if(isConnected())
			try{
				ResultSet rs = getConnection().prepareStatement("SELECT * FROM mine").executeQuery();
				while(rs.next()){
					String name = rs.getString("mine_name");
					long delay = rs.getLong("delay");
					
					String[] split = rs.getString("locations").split(",");
					Location loc1 = new Location(Bukkit.getWorld(split[0].split(";")[0]), 
							Double.valueOf(split[0].split(";")[1]), 
							Double.valueOf(split[0].split(";")[2]), 
							Double.valueOf(split[0].split(";")[3])),
							loc2 = new Location(Bukkit.getWorld(split[1].split(";")[0]), 
									Double.valueOf(split[1].split(";")[1]), 
									Double.valueOf(split[1].split(";")[2]), 
									Double.valueOf(split[1].split(";")[3]));

					List<PMaterial> chances = new ArrayList<>();
					if(!rs.getString("materials").equals(""))
						for(String s : rs.getString("materials").split(","))
							chances.add(new PMaterial(
									Material.getMaterial(Integer.parseInt(s.split(";")[0].split(":")[0])), 
									Byte.parseByte(s.split(";")[0].split(":")[1]), 
									Integer.parseInt(s.split(";")[1])));

					PrisonCore.getMines().load(name, loc1, loc2, chances, delay);
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
	}

	public void saveShops(){
		if(isConnected())
			try{
				getConnection().prepareStatement("DELETE FROM shop").executeUpdate();
				
				for(Shop s : PrisonCore.getShops().shops()){
					String name = s.getName(), mine = "", prices = "";

					if(s.getMine() != null)
						mine = s.getMine().getName();
					
					for(ItemStack is : s.getPrices().keySet())
						if(prices.equals("")) 
							prices = is.getTypeId() + ":" + is.getDurability() + ";" + s.getPrices().get(is);
						else prices += "," + is.getTypeId() + ":" + is.getDurability() + ";" + s.getPrices().get(is);

					PreparedStatement ps = getConnection()
							.prepareStatement("INSERT INTO shop(shop_name,mine_name,prices) VALUES(?,?,?)");

					ps.setString(1, name);
					ps.setString(2, mine);
					ps.setString(3, prices);
					ps.executeUpdate();
				}

			} catch (SQLException e){
				e.printStackTrace();
			}
	}

	public void loadShops(){
		if(isConnected())
			try{
				ResultSet rs = getConnection().prepareStatement("SELECT * FROM shop").executeQuery();
				while(rs.next()){
					String name = rs.getString("shop_name");
					Mine mine = PrisonCore.getMines().get(rs.getString("mine_name"));
					
					Map<ItemStack, Integer> prices = new HashMap<>();
					if(!rs.getString("prices").equals(""))
						for(String s : rs.getString("prices").split(","))
							prices.put(new ItemStack(
									Material.getMaterial(Integer.parseInt(s.split(";")[0].split(":")[0])), 
									1, 
									Short.parseShort(s.split(";")[0].split(":")[1])), 
									Integer.parseInt(s.split(";")[1]));

					PrisonCore.getShops().load(name, mine, prices);
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
	}
	
	public void saveEconomy(){
		if(isConnected())
			try{
				getConnection().prepareStatement("DELETE FROM economy").executeUpdate();
				
				for(UUID uuid : Economy_PrisonCore.accounts.keySet()){
					BigDecimal balance = Economy_PrisonCore.accounts.get(uuid);
					
					PreparedStatement ps = getConnection()
							.prepareStatement("INSERT INTO economy(player_uuid,balance) VALUES(?,?)");

					ps.setString(1, uuid.toString());
					ps.setString(2, balance.toString());
					ps.executeUpdate();
				}

			} catch (SQLException e){
				e.printStackTrace();
			}
	}

	public void loadEconomy(){
		if(isConnected())
			try{
				ResultSet rs = getConnection().prepareStatement("SELECT * FROM economy").executeQuery();
				while(rs.next()){
					UUID uuid = UUID.fromString(rs.getString("player_uuid"));
					BigDecimal balance = new BigDecimal(rs.getString("balance"));
					
					Economy_PrisonCore.accounts.put(uuid, balance);
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
	}
	
	public void saveKits(){
		if(isConnected())
			try{
				getConnection().prepareStatement("DELETE FROM kits").executeUpdate();
				
				for(Kit kit : KitCmd.kits){
					String name = kit.getName(), inv = DataUtils.itemsToString(kit.getItems());
					int delay = (int) kit.getDelay();
					
					PreparedStatement ps = getConnection()
							.prepareStatement("INSERT INTO kits(kit_name,inventory,delay) VALUES(?,?,?)");
					
					ps.setString(1, name);
					ps.setString(2, inv);
					ps.setInt(3, delay);
					ps.executeUpdate();
				}

			} catch (SQLException e){
				e.printStackTrace();
			}
	}

	public void loadKits(){
		if(isConnected())
			try{
				ResultSet rs = getConnection().prepareStatement("SELECT * FROM kits").executeQuery();
				while(rs.next()){
					String name = rs.getString("kit_name");
					List<ItemStack> items = DataUtils.itemsFromString(rs.getString("inventory"));
					long delay = rs.getLong("delay");
					
					KitCmd.kits.add(new Kit(name, items, delay));
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
	}

	public void runTask(){
		this.task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(PrisonCore.getPlugin(PrisonCore.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				Bukkit.broadcastMessage(CommandsHandle.getMessage("SAVE_DATA", null));
				saveMines();
				saveShops();
				saveEconomy();
				saveKits();
				Bukkit.broadcastMessage(CommandsHandle.getMessage("SAVE_DATA_COMPLETE", null));
			}
		}, 0, saveInterval * 20);
	}
	
	public void stopTask(){
		Bukkit.getScheduler().cancelTask(task);
	}
	
	public void setSaveInterval(long saveInterval){
		this.saveInterval = saveInterval;
	}
	
	private String getLocation(Location loc){
		return loc.getWorld().getName() + ";" + loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ();
	}

}