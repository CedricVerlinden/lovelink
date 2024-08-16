package com.cedricverlinden.lovelink.commands;

import com.cedricverlinden.lovelink.configs.MessagesConfig;
import com.cedricverlinden.lovelink.managers.ConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ProposeCommand extends BaseCommand {

	MessagesConfig messages = ConfigurationManager.getInstance().getConfig(MessagesConfig.class);

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sendMessage("&cUsage: /propose <player>");
			return;
		}

		Player proposee = Bukkit.getPlayer(args[0]);
		if (proposee == player) {
			sendMessage("&cYou can't propose to yourself!");
			return;
		}

		if (proposee == null) {
			sendMessage("&cPlayer not found!");
			return;
		}

		Player amIAlreadyAPartner = AcceptCommand.getPartner(player);
		Player isTargetAlreadyAPartner = AcceptCommand.getPartner(proposee);
		if (amIAlreadyAPartner == null) {
			sendMessage("&cYou are already in a relationship!");
			return;
		}

		if (isTargetAlreadyAPartner == null) {
			sendMessage("&cThe player you are trying to propose to is already in a relationship!");
			return;
		}

		sendMessage(proposee, messages.getCOMMAND_PROPOSE_RECEIVER().replace("%player%", player.getName()));
		sendMessage(messages.getCOMMAND_PROPOSE_SENDER().replace("%player%", proposee.getName()));
		proposee.setMetadata("proposedBy", new FixedMetadataValue(plugin, player.getName()));
	}
}
