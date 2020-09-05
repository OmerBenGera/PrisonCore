package com.ome_r.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.ome_r.PrisonCore;
import com.ome_r.mines.Mine;
import com.ome_r.mines.autopickup.AutoBlock;
import com.ome_r.mines.autopickup.AutoBurn;
import com.ome_r.mines.autopickup.AutoPickup;
import com.ome_r.mines.utils.LocationUtils;
import com.ome_r.shops.Shop;

public class BlockBreakListener implements Listener{

	private boolean autoSell = true;
	
	public BlockBreakListener(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent e){
		List<ItemStack> drops = new ArrayList<>(e.getBlock().getDrops());
		Player p = e.getPlayer();
		
		if(p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) return;

		boolean insideMine = false;
		Mine mine = null;
		for(Mine m : PrisonCore.getMines().mines())
			if(LocationUtils.betweenPoints(e.getBlock().getLocation(), m.getLocations()[0], m.getLocations()[1])){
				insideMine = true;
				mine = m;
				if(!e.getPlayer().hasPermission(m.getPermission()))
					e.setCancelled(true);
			}
		
		if(e.isCancelled())
			return;

		//Auto fortune the drops
		AutoPickup.run(p, drops);

		//Auto burn the drops
		AutoBurn.run(e.getBlock(), drops);

		//Add items to the inventory of the player
		for(ItemStack is : drops){
			e.setCancelled(true);
			e.getBlock().setType(Material.AIR);
			if(isEmpty(p.getInventory(), is)){
				Shop s = PrisonCore.getShops().get(mine);
				
				if(s != null && insideMine && autoSell){
					
					s.addPrice(is.getType(), is.getDurability(), 20);
					if(s.hasPrice(is.getType(), is.getDurability())){
						
						if(!PrisonCore.getEconomy().hasAccount(p))
							PrisonCore.getEconomy().createPlayerAccount(p);
						
						PrisonCore.getEconomy()
							.depositPlayer(p, s.getPrice(is.getType(), is.getDurability()) * is.getAmount());
						continue;
					}
					
				}else p.getInventory().addItem(is);
			
			}else p.sendMessage("§cYour inventory is full.");
		}

		//Auto block the drops
		AutoBlock.run(p);
	}

	private boolean isEmpty(Inventory inv, ItemStack itemStack){
		for(ItemStack is : inv.getContents())
			if(is == null || is.getType() == Material.AIR || (is.getType() == itemStack.getType() && 
			is.getDurability() == itemStack.getDurability() && is.getAmount() < is.getMaxStackSize())) 
				return true;

		return false;
	}

}
