package com.cedricverlinden.lovelink;

import com.cedricverlinden.lovelink.commands.*;
import com.cedricverlinden.lovelink.listeners.ChatListener;
import com.cedricverlinden.lovelink.managers.ConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public final class LoveLink extends JavaPlugin {

	static LoveLink plugin;
	ConfigurationManager configManager;

	private File couplesFolder;

	@Override
	public void onEnable() {
		plugin = this;
		getLogger().info("LoveLink has been enabled!");

		// Load config files
		configManager = ConfigurationManager.getInstance();

		// Load the commands
		loadCommands();

		// Load the listeners
		loadListeners();

		// Start the ActionBarUpdater
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new ActionBarUpdater(), 0L, 2L);

		// Load couples from file
		loadCouples();
	}

	@Override
	public void onDisable() {
		saveCouples();
	}

	public void loadCommands() {
		getCommand("propose").setExecutor(new ProposeCommand());
		getCommand("accept").setExecutor(new AcceptCommand());
		getCommand("decline").setExecutor(new DeclineCommand());
	}

	public void loadListeners() {
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
	}

	public void loadCouples() {
		couplesFolder = new File(getDataFolder(), "couples");
		if (!couplesFolder.exists()) {
			couplesFolder.mkdirs();
		}

		File[] files = couplesFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".yml")) {
					FileConfiguration config = YamlConfiguration.loadConfiguration(file);
					List<String> players = config.getStringList("players");
					AcceptCommand.couples.put(UUID.fromString(players.get(0)), UUID.fromString(players.get(1)));
				}
			}
		}
	}

	public void saveCouples() {
		if (couplesFolder == null) {
			return;
		}

		for (Map.Entry<UUID, UUID> entry : AcceptCommand.couples.entrySet()) {
			UUID player1 = entry.getKey();
			UUID player2 = entry.getValue();

			String fileName = player1.toString() + "_" + player2.toString() + ".yml";
			File coupleFile = new File(couplesFolder, fileName);
			if (!coupleFile.exists()) {
				try {
					coupleFile.createNewFile();
				} catch (IOException e) {
					getLogger().log(Level.SEVERE, "Could not create couple file for " + player1 + " and " + player2, e);
					continue;
				}
			}

			List<String> players = Arrays.asList(player1.toString(), player2.toString());

			FileConfiguration config = YamlConfiguration.loadConfiguration(coupleFile);
			config.set("players", players);

			try {
				config.save(coupleFile);
			} catch (IOException e) {
				getLogger().log(Level.SEVERE, "Could not save couple file for " + player1 + " and " + player2, e);
			}
		}
	}

	public static LoveLink getPlugin() {
		return plugin;
	}
}