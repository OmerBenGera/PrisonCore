package com.ome_r.mines.utils;

import org.bukkit.Material;

public class PMaterial implements Comparable<PMaterial> {

	private Material material;
	private byte data;
	private int chance;
	
	public PMaterial(Material material, byte data, int chance){
		this.material = material;
		this.chance = chance;
		this.data = data;
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public byte getData(){
		return data;
	}
	
	public int getChance(){
		return chance;
	}
	
	@Override
	public int compareTo(PMaterial o) {
		return String.valueOf(getChance()).compareTo(String.valueOf(o.getChance()));
	}
	
}
