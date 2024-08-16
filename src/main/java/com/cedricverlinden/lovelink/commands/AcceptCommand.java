package com.cedricverlinden.lovelink.commands;

import com.cedricverlinden.lovelink.configs.MessagesConfig;
import com.cedricverlinden.lovelink.managers.ConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AcceptCommand extends BaseCommand {

	public static final Map<UUID, UUID> couples = new HashMap<>();
	MessagesConfig messages = ConfigurationManager.getInstance().getConfig(MessagesConfig.class);

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!player.hasMetadata("proposedBy")) {
			sendMessage("&cNo proposal to accept!");
			return;
		}

		Player proposer = Bukkit.getPlayer(player.getMetadata("proposedBy").getFirst().asString());
		couples.put(proposer.getUniqueId(), player.getUniqueId());
		sendMessage(proposer, messages.getCOMMAND_PROPOSE_RECEIVER().replace("%player%", player.getName()));
		sendMessage(messages.getCOMMAND_PROPOSE_SENDER().replace("%player%", proposer.getName()));
		player.removeMetadata("proposedBy", plugin);
	}

	public static Player getPartner(Player player) {
		UUID playerUUID = player.getUniqueId();
		for (Map.Entry<UUID, UUID> entry : couples.entrySet()) {
			if (entry.getKey().equals(playerUUID)) {
				return Bukkit.getPlayer(entry.getValue());
			} else if (entry.getValue().equals(playerUUID)) {
				return Bukkit.getPlayer(entry.getKey());
			}
		}
		return null;
	}
}
