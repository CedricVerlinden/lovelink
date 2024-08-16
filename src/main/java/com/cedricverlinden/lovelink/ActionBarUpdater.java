package com.cedricverlinden.lovelink;

import com.cedricverlinden.lovelink.commands.AcceptCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Map;
import java.util.UUID;

public class ActionBarUpdater implements Runnable {

	@Override
	public void run() {
		for (Map.Entry<UUID, UUID> entry : AcceptCommand.couples.entrySet()) {
			Player partnerOne = Bukkit.getPlayer(entry.getKey());
			Player partnerTwo = Bukkit.getPlayer(entry.getValue());

			if (partnerOne == null && partnerTwo == null) {
				continue;
			}

			if (partnerOne != null && partnerTwo != null) {
				updateActionBar(partnerOne, partnerTwo);
				updateActionBar(partnerTwo, partnerOne);

				updateTabList(partnerOne, partnerTwo);
				updateTabList(partnerTwo, partnerOne);
			}

			if (partnerOne != null && partnerTwo == null) {
				partnerOne.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "PARTNER IS OFFLINE"));
				partnerOne.setPlayerListName(ChatColor.RED + " •" + ChatColor.DARK_GRAY + " | " + ChatColor.WHITE + partnerOne.getName());
			}

			if (partnerOne == null && partnerTwo != null) {
				partnerTwo.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "PARTNER IS OFFLINE"));
				partnerTwo.setPlayerListName(ChatColor.RED + " •" + ChatColor.DARK_GRAY + " | " + ChatColor.WHITE + partnerTwo.getName());
			}
		}
	}

	private void updateActionBar(Player player, Player partner) {
		Location playerLocation = player.getLocation();
		Location partnerLocation = partner.getLocation();

		String direction = isWithinRadius(playerLocation, partnerLocation, 5.0D) ? ChatColor.RED + "❤": getDirectionArrow(playerLocation, partnerLocation);
		String message = ChatColor.GRAY + "X " + ChatColor.WHITE + partnerLocation.getBlockX() + ChatColor.DARK_GRAY + "   |   " + ChatColor.GRAY + "Y " + ChatColor.WHITE + partnerLocation.getBlockY() + ChatColor.DARK_GRAY + "   |   " + ChatColor.GRAY + "Z " + ChatColor.WHITE + partnerLocation.getBlockZ() + ChatColor.DARK_GRAY + "   |   " + ChatColor.WHITE + direction;

		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
	}

	private void updateTabList(Player player, Player partner) {
		double distance = player.getLocation().distance(partner.getLocation());
		String playerName = ChatColor.GRAY + String.format("%.1f", distance) + "m" + ChatColor.DARK_GRAY + " | " + ChatColor.WHITE + player.getName();
		player.setPlayerListName(playerName);
	}

	private boolean isWithinRadius(Location loc1, Location loc2, double radius) {
		return loc1.getWorld().equals(loc2.getWorld()) && loc1.distance(loc2) <= radius;
	}

	private String getDirectionArrow(Location from, Location to) {
		double dx = to.getX() - from.getX();
		double dz = to.getZ() - from.getZ();
		double yaw = from.getYaw();

		double angle = Math.toDegrees(Math.atan2(dz, dx)) - yaw;

		angle = (angle + 360) % 360;

		if (angle >= 22.5 && angle < 67.5) {
			return "↖";
		} else if (angle >= 67.5 && angle < 112.5) {
			return "↑";
		} else if (angle >= 112.5 && angle < 157.5) {
			return "↗";
		} else if (angle >= 157.5 && angle < 202.5) {
			return "→";
		} else if (angle >= 202.5 && angle < 247.5) {
			return "↘";
		} else if (angle >= 247.5 && angle < 292.5) {
			return "↓";
		} else if (angle >= 292.5 && angle < 337.5) {
			return "↙";
		} else {
			return "←";
		}
	}
}
