package com.ome_r.scoreboard;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.ome_r.PrisonCore;
import com.ome_r.commands.CommandsHandle;

public class Board {

	private List<String> lines;
	private String title;
	private Scoreboard board;
	private Player p;
	
	public Board(Player p, List<String> lines, String title, Scoreboard board){
		this.p = p;
		this.lines = lines;
		this.title = title;
		this.board = board;
		p.setScoreboard(board);
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public Scoreboard getBoard(){
		return board;
	}
	
	public void updateLines(boolean vars){
		Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
		
		for(int i = 0; i < lines.size(); i++){
			Team t = board.getTeam(i + "");
			String line, prefix, suffix;
			
			if(lines.get(i).contains("%animation:"))
				line = translateAnimation(lines.get(i));
			
			else if(lines.get(i).contains("%") && vars)
				line = getString(lines.get(i), p);
			
			else if(lines.get(i).contains("%"))
				continue;
			
			else line = lines.get(i);
			
			prefix = line;
			suffix = "";
			
			if(line.length() > 16){
				prefix = line.substring(0, 16);
				
				suffix = ChatColor.getLastColors(line) + line.substring(16, line.length());
				
				if(line.charAt(15) == '§'){
					prefix = line.substring(0, 15);
					suffix = ChatColor.getLastColors(line) + line.substring(15, line.length());
				}
				
				if(suffix.length() > 16) suffix = suffix.substring(0, 16);
			}
			
			t.setPrefix(prefix);
			t.setSuffix(suffix);
		}
		
		if(vars) objective.setDisplayName(getString(title, p));
		else objective.setDisplayName(translateAnimation(title));
	}
	
	private String translateAnimation(String s){
		String str = s;
		for(Animation a : PrisonCore.getBoards().animations())
			str = str.replace("%animation:" + a.getName() + "%", a.getString());
		return str;
	}
	
	private String getString(String s, Player p){
		String str = s;
		
		if(p == null)
			return "";
		
		String money = PrisonCore.getEconomy().currencyNamePlural() + 
				PrisonCore.getEconomy().format(PrisonCore.getEconomy().getBalance(p)),
				rank = "None", nextRank = "None", mine = "None", multiplier = "x1";
		
		if(PrisonCore.getRankups().get(p) != null)
			rank = PrisonCore.getRankups().get(p).getRank();
		
		if(!rank.equals("None") && PrisonCore.getRankups().get(p).getNextRank() != null)
			nextRank = PrisonCore.getRankups().get(p).getNextRank().getRank();
		
		if(PrisonCore.getMines().get(p.getLocation()) != null)
			mine = PrisonCore.getMines().get(p.getLocation()).getName();
		
		multiplier = "x" + PrisonCore.getMultipliers().get(p.getUniqueId());
		
		if(str.contains("%animation:"))
			str = translateAnimation(str);
		
		return CommandsHandle.getMessage(str.replace("%money%", money).replace("%rank%", rank)
				.replace("%rankup%", nextRank).replace("%mine%", mine).replace("%multiplier%", multiplier), p);
	}
	
}
