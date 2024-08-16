package com.cedricverlinden.lovelink.listeners;

import com.cedricverlinden.lovelink.commands.AcceptCommand;
import com.cedricverlinden.lovelink.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Player partner = AcceptCommand.getPartner(player);

		if (partner == null) return;

		if (event.getMessage().startsWith("!")) {
			event.setMessage(event.getMessage().substring(1));
			return;
		}

		Chat chat = new Chat();

		event.setCancelled(true);
		player.sendMessage(chat.color("&dCOUPLE &8| &7" + player.getName() + " &f" + event.getMessage()));
		partner.sendMessage(chat.color("&dCOUPLE &8| &7" + player.getName() + " &f" + event.getMessage()));
	}
}
