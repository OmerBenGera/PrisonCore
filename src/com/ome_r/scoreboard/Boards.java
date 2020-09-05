package com.ome_r.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.ome_r.PrisonCore;

@SuppressWarnings("deprecation")
public class Boards {
	
	public String title;
	public List<String> lines;
	
	private List<Board> boards;
	private List<Animation> animations;
	private int interval;
	
	public Boards(){
		this.boards = new ArrayList<>();
		this.animations = new ArrayList<>();
		this.title = "";
		this.interval = 0;
		this.lines = new ArrayList<>();
	}
	
	public void load(String title, int interval, List<String> lines){
		this.title = title;
		this.lines = lines;
		this.interval = interval;
		Collections.reverse(lines);
	}
	
	public Board create(Player p){
		Board b = new Board(p, lines, title, getDefault());
		boards.add(b);
		
		return b;
	}
	
	public void destroy(Board b){
		boards.remove(b);
	}
	
	public void destroy(Player p){
		boards.remove(get(p));
	}
	
	public Board get(Player p){
		for(Board b : boards)
			if(b.getPlayer().equals(p)) return b;
		
		return null;
	}
	
	public Animation loadAnimation(String name, List<String> lines){
		Animation a = new Animation(name, lines);
		
		if(!animations.contains(a))
			animations.add(a);
		
		return a;
	}
	
	public void clearAnimations(){
		animations.clear();
	}
	
	public Animation getAnimation(String name){
		for(Animation an : animations)
			if(an.getName().equalsIgnoreCase(name)) return an;
			
		return null;
	}
	
	public List<Animation> animations(){
		return animations;
	}
	
	private Scoreboard getDefault(){
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective objective = board.registerNewObjective("scoreboard", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		for(int i = 0; i < lines.size(); i++){
			Team t = board.registerNewTeam(i + "");
			t.addEntry(ChatColor.values()[i] + "");
			objective.getScore(ChatColor.values()[i] + "").setScore(i);
		}
		
		return board;
	}
	
	public void runAnimationsTask(){
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(PrisonCore.getPlugin(PrisonCore.class), new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Animation a : animations)
					a.readAnimation();
			}
		}, 0, 1);
	}
	
	public void runTask(){
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PrisonCore.getPlugin(PrisonCore.class), new BukkitRunnable() {
			int counter = interval * 20;
			
			@Override
			public void run() {
				for(Board b : boards)
					b.updateLines(counter >= (interval * 20));

				if(counter >= (interval * 20) + 1) counter = 0;
				else counter++;
			}
		}, 0, 1);
	}
	
}
