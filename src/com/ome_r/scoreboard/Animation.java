package com.ome_r.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Animation {

	private List<String> lines;
	private String currentLine, name;
	private int timer, line;
	
	public Animation(String name){
		this.lines = new ArrayList<>();
		this.currentLine = "";
		this.name = name;
		this.line = 0;
		this.timer = 0;
	}
	
	public Animation(String name, List<String> lines){
		this.lines = lines;
		this.currentLine = "";
		this.name = name;
		this.line = 0;
		this.timer = 0;
	}

	public String getName(){
		return name;
	}
	
	public void addLine(String message){
		lines.add(message);
	}
	
	public void removeLine(String message){
		lines.remove(message);
	}
	
	public List<String> getLines(){
		return new ArrayList<>(lines);
	}
	
	public void readAnimation(){
		if(line >= lines.size()) line = 0;
		
		String message = lines.get(line).split("]")[1];
		int time = Integer.valueOf(lines.get(line).split("]")[0].substring(1));
		timer++;
		
		if(!currentLine.equals(message))
			currentLine = message;
		
		if(timer >= time){
			timer = 0;
			line++;
		}
	}
	
	public String getString(){
		return currentLine;
	}
	
}
