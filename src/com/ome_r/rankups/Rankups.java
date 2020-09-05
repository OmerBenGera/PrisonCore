package com.ome_r.rankups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;

import com.ome_r.PrisonCore;

public class Rankups {
	
	private List<Rankup> rankups;
	
	public Rankups(){
		rankups = new ArrayList<>();
	}
	
	public Rankup create(String rank, double price){
		Rankup rankup = new Rankup(rank, price);
		
		if(!rankups.contains(rankup))
			rankups.add(rankup);
		
		return rankup;
	}
	
	public Rankup get(String rank){
		for(Rankup rankup : rankups)
			if(rankup.getRank().equalsIgnoreCase(rank)) 
				return rankup;
		
		return null;
	}
	
	public Rankup get(Player p){
		Rankup rank = null;
		for(Rankup rankup : rankups)
			if(PrisonCore.getPermission().playerInGroup(p, rankup.getGroup()))
				if(rank == null || rankup.getLevel() > rank.getLevel())
					rank = rankup;
			
		return rank;
	}
	
	public Rankup get(int level){
		for(Rankup rankup : rankups)
			if(rankup.getLevel() == level) 
				return rankup;
		
		return null;
	}
	
	public void load(String rank, String group, int level, double price, String prefix, List<String> commands){
		Rankup rankup = new Rankup(rank, group, level, price, prefix, commands);
		
		if(!rankups.contains(rankup))
			rankups.add(rankup);
	}
	
	public void delete(Rankup rankup){
		if(rankups.contains(rankup))
			rankups.remove(rankup);
	}
	
	public boolean contains(String rank){
		for(Rankup rankup : rankups)
			if(rankup.getRank().equalsIgnoreCase(rank)) 
				return true;
		
		return false;
	}
	
	public boolean contains(int level){
		for(Rankup rankup : rankups)
			if(rankup.getLevel() == level) 
				return true;
		
		return false;
	}
	
	public boolean contains(Rankup rankup){
		return rankups.contains(rankup);
	}
	
	public List<Rankup> rankups(){
		Collections.sort(rankups);
		return new ArrayList<>(rankups);
	}
	
	public void clear(){
		rankups.clear();
	}
	
}
