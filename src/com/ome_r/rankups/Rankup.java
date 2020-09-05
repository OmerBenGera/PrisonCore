package com.ome_r.rankups;

import java.util.ArrayList;
import java.util.List;

import com.ome_r.PrisonCore;

public class Rankup implements Comparable<Rankup>{

	private double price;
	private int level;
	private String rank, prefix, group;
	private List<String> commands;
	
	public Rankup(String rank, double price){
		this.rank = rank;
		this.group = rank;
		this.level = generateLevel();
		this.price = price;
		this.prefix = "";
		this.commands = new ArrayList<>();
	}
	
	public Rankup(String rank, String group, int level, double price, String prefix, List<String> commands){
		this.rank = rank;
		this.group = group;
		this.level = level;
		this.price = price;
		this.prefix = prefix;
		this.commands = commands;
	}
	
	public void setRank(String rank){
		this.rank = rank;
	}
	
	public String getRank(){
		return rank;
	}
	
	public String getGroup(){
		return group;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}
	
	public String getPrefix(){
		return prefix;
	}
	
	public void addCommand(String command){
		if(!commands.contains(command))
			commands.add(command);
	}
	
	public void delCommand(String command){
		if(commands.contains(command))
			commands.add(command);
	}
	
	public boolean hasCommand(String command){
		return commands.contains(command);
	}
	
	public List<String> getCommands(){
		return commands;
	}
	
	public Rankup getNextRank(){
		int maxLevel = this.level, level = this.level + 1;
		
		for(Rankup r : PrisonCore.getRankups().rankups())
			if(r.getLevel() > maxLevel) maxLevel = r.getLevel();
		
		while(!PrisonCore.getRankups().contains(level)){
			if(level > maxLevel) return null;
			level++;
		}
			
		return PrisonCore.getRankups().get(level);
	}
	
	private int generateLevel(){
		int level = 1;
		while(PrisonCore.getRankups().contains(level))
			level++;
		
		return level;
	}

	@Override
	public int compareTo(Rankup o) {
		return String.valueOf(level).compareTo(String.valueOf(o.getLevel()));
	}
	
}
