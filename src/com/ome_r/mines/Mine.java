package com.ome_r.mines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.ome_r.mines.utils.LocationUtils;
import com.ome_r.mines.utils.PMaterial;

public class Mine {

	private String name;
	private List<PMaterial> chances;
	private World world;
	private int minX, maxX, minY, maxY, minZ, maxZ;
	private long resetTime;
	private long delay;

	//----------------------------------------//
	//             Create a Mine              //
	//----------------------------------------//
	
	public Mine(String name, Location loc1, Location loc2){
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX()), maxX = Math.max(loc1.getBlockX(), loc2.getBlockX()),
				minY = Math.min(loc1.getBlockY(), loc2.getBlockY()), maxY = Math.max(loc1.getBlockY(), loc2.getBlockY()),
				minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ()), maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		
		this.name = name;
		this.world = loc1.getWorld();
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;
		this.chances = new ArrayList<>();
		this.delay = 600L;
		this.resetTime = (System.currentTimeMillis() / 1000) + delay;
	}
	
	//----------------------------------------//
	//              Load a Mine               //
	//----------------------------------------//
	
	public Mine(String name, Location loc1, Location loc2, List<PMaterial> chances, long delay){
		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX()), maxX = Math.max(loc1.getBlockX(), loc2.getBlockX()),
				minY = Math.min(loc1.getBlockY(), loc2.getBlockY()), maxY = Math.max(loc1.getBlockY(), loc2.getBlockY()),
				minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ()), maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		
		this.name = name;
		this.world = loc1.getWorld();
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;
		this.chances = chances;
		this.delay = delay;
		this.resetTime = (System.currentTimeMillis() / 1000) + delay;
	}
	
	//----------------------------------------//
	//            Getter Methods              //
	//----------------------------------------//
	
	public String getName(){
		return name;
	}
	
	public Location[] getLocations(){
		Location[] locs = {
				new Location(world, minX, minY, minZ),
				new Location(world, maxX, maxY, maxZ)
		};
		return locs;
	}
	
	//----------------------------------------//
	//           Reset Time Section           //
	//----------------------------------------//
	
	public void setResetTime(long resetTime){
		this.resetTime = resetTime;
	}
	
	public long getResetTime(){
		return resetTime;
	}
	
	//----------------------------------------//
	//            Delay Section               //
	//----------------------------------------//
	
	public void setDelay(long delay){
		this.delay = delay;
	}
	
	public long getDelay(){
		return delay;
	}
	
	//----------------------------------------//
	//           Materials Section            //
	//----------------------------------------//
	
	public void addMaterial(PMaterial pMaterial){
		for(PMaterial pM : new ArrayList<>(chances))
			if((pM.getMaterial() == pMaterial.getMaterial()) && (pM.getData() == pMaterial.getData())){
				chances.remove(pM);
				if(pMaterial.getChance() == 0) return;
			}
		
		chances.add(pMaterial);
	}
	
	public boolean containsMaterial(PMaterial pMaterial){
		for(PMaterial pM : chances)
			if((pM.getMaterial() == pMaterial.getMaterial()) && (pM.getData() == pMaterial.getData()))
				return true;
		
		return false;
	}
	
	public List<PMaterial> getMaterials(){
		return chances;
	}
	
	//----------------------------------------//
	//          Percentages Section           //
	//----------------------------------------//
	
	public int getPercentage(){
		int percentage = 0;
		
		for(PMaterial pM : chances)
			percentage += pM.getChance();
		
		return percentage;
	}
	
	public int getPercentageWithout(PMaterial pMaterial){
		int percentage = 0;
		
		for(PMaterial pM : chances){
			if((pM.getMaterial() != pMaterial.getMaterial()) || (pM.getData() != pMaterial.getData()))
				percentage += pM.getChance();
		}
		
		return percentage;
	}
	
	//----------------------------------------//
	//          Permissions Section           //
	//----------------------------------------//
	
	public String getPermission(){
		return "prisoncore.mines.use." + name;
	}
	
	//----------------------------------------//
	//           Reset Mine Method            //
	//----------------------------------------//

	@SuppressWarnings("deprecation")
	public void reset(){
		List<PMaterial> chances = new ArrayList<>(this.chances);
		int percentage = getPercentage();

		if(percentage > 100){
			System.err.println("[PrisonCore] Mine " + name + " has more then 100%");
			return;
		}

		else if(percentage < 100){
			chances.add(new PMaterial(Material.AIR, (byte) 0, 100 - percentage));
		}
		
		Collections.sort(chances);
		
		Random r = new Random();
		for(int x = minX; x <= maxX; x++)
			for(int y = minY; y <= maxY; y++)
				for(int z = minZ; z <= maxZ; z++){
					int pMChance, i = 0, chance = r.nextInt(99) + 1;
					
					for(PMaterial pM : chances){
						pMChance = i + pM.getChance();
						i = pMChance;
						
						if(chance <= pMChance){
							world.getBlockAt(x, y, z).setTypeIdAndData(pM.getMaterial().getId(), pM.getData(), true);
							break;
						}
						
					}
					
				}
		
		for(Player p : Bukkit.getOnlinePlayers())
			if(LocationUtils.betweenPoints(p.getLocation(), getLocations()[0], getLocations()[1]))
				LocationUtils.teleport(p);
		
		resetTime = (System.currentTimeMillis() / 1000) + delay;
	}

}
