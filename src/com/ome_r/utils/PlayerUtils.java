package com.ome_r.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtils {

	public static void sendActionBar(Player p, String message){
        try {
            Method aMethod = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class);
			Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
			
			sendPacket(p, constructor.newInstance(
					aMethod.invoke(null, "{\"text\":\"" + message + "\"}"), (byte) 2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendTitle(Player p, String title, String subTitle, int int1, int int2, int int3){
        try {
        	Class<?> enumTitleClass = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0];
            Method aMethod = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class);
			Constructor<?> constructor = getNMSClass("PacketPlayOutTitle").getConstructor(enumTitleClass, getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
			
			sendPacket(p, constructor.newInstance(
					enumTitleClass.getField("TITLE").get(null), 
					aMethod.invoke(null, "{\"text\":\"" + title + "\"}"), int1, int2, int3));
			sendPacket(p, constructor.newInstance(
					enumTitleClass.getField("SUBTITLE").get(null), 
					aMethod.invoke(null, "{\"text\":\"" + subTitle + "\"}"), int1, int2, int3));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void sendPacket(Player p, Object packet){
		try {
			Object handle = p.getClass().getMethod("getHandle").invoke(p);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Class<?> getNMSClass(String name){
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
