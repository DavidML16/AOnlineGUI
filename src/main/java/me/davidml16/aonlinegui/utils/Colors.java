package me.davidml16.aonlinegui.utils;

import org.bukkit.ChatColor;

public class Colors {
	public static String translate(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
