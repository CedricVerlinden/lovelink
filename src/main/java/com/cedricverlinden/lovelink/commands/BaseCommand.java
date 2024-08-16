package com.cedricverlinden.lovelink.commands;

import com.cedricverlinden.lovelink.LoveLink;
import com.cedricverlinden.lovelink.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class BaseCommand implements CommandExecutor {

	private final boolean allowConsole;

	protected LoveLink plugin;
	protected Player player;
	protected Chat chat;

	public BaseCommand(boolean allowConsole) {
		this.plugin = LoveLink.getPlugin();
		this.allowConsole = allowConsole;
		this.chat = new Chat();
	}

	public BaseCommand() {
		this(false);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if (!allowConsole && !(sender instanceof Player)) {
			sender.sendMessage("Only players can execute this command.");
			return true;
		}

		if (sender instanceof Player) {
			player = (Player) sender;
		}

		execute(sender, args);
		return true;
	}

	protected void sendMessage(String message) {
		player.sendMessage(chat.color(message));
	}

	protected void sendMessage(Player player, String message) {
		player.sendMessage(chat.color(message));
	}

	public abstract void execute(CommandSender sender, String[] args);
}
