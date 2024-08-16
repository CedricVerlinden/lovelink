package com.cedricverlinden.lovelink.commands;

import com.cedricverlinden.lovelink.configs.MessagesConfig;
import com.cedricverlinden.lovelink.managers.ConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeclineCommand extends BaseCommand {

	MessagesConfig messages = ConfigurationManager.getInstance().getConfig(MessagesConfig.class);

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!player.hasMetadata("proposedBy")) {
			sendMessage("&cNo proposal to decline!");
			return;
		}

		Player proposer = Bukkit.getPlayer(player.getMetadata("proposedBy").getFirst().asString());
		sendMessage(proposer, messages.getCOMMAND_PROPOSE_RECEIVER().replace("%player%", player.getName()));
		sendMessage(messages.getCOMMAND_PROPOSE_SENDER().replace("%player%", proposer.getName()));
		player.removeMetadata("proposedBy", plugin);
	}
}
