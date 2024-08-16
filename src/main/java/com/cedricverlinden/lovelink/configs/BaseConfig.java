package com.cedricverlinden.lovelink.configs;

import com.cedricverlinden.lovelink.LoveLink;
import com.cedricverlinden.lovelink.utils.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class BaseConfig {

	private final LoveLink plugin;
	private final File configFile;
	private final String folderName;

	protected FileConfiguration config;

	public BaseConfig(LoveLink plugin, String fileName) {
		this.plugin = plugin;
		this.folderName = "";
		this.configFile = new File(plugin.getDataFolder(), fileName);
		loadConfig();
	}

	public BaseConfig(LoveLink plugin, String folderName, String fileName) {
		this.plugin = plugin;
		this.folderName = folderName + File.separator;
		this.configFile = new File(plugin.getDataFolder() + folderName, fileName);
		loadConfig();
	}

	private void loadConfig() {
		if (!configFile.exists()) {
			if (!folderName.isEmpty()) {
				File folder = new File(plugin.getDataFolder(), folderName);
				if (!folder.exists()) {
					folder.mkdirs();
				}
			}

			if (!FileUtils.doesResourceExist(folderName, configFile.getName())) {
				plugin.saveResource(folderName + configFile.getName(), false);
			}
		}
		this.config = YamlConfiguration.loadConfiguration(configFile);
		loadValues();
	}

	public void saveConfig() {
		if (config == null || configFile == null) {
			return;
		}

		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reloadConfig() {
		if (configFile == null) {
			return;
		}

		config = YamlConfiguration.loadConfiguration(configFile);
		loadValues();
	}

	protected abstract void loadValues();
}
