package com.cedricverlinden.lovelink.managers;

import com.cedricverlinden.lovelink.LoveLink;
import com.cedricverlinden.lovelink.configs.BaseConfig;
import com.cedricverlinden.lovelink.configs.MessagesConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {

	private static ConfigurationManager instance;
	private static LoveLink plugin;
	private final Map<Class<? extends BaseConfig>, BaseConfig> configs = new HashMap<>();

	private ConfigurationManager() {
		plugin = LoveLink.getPlugin();
		loadConfigurations();
	}

	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
		}
		return instance;
	}

	private void loadConfigurations() {
		registerConfig(MessagesConfig.class, new MessagesConfig(plugin));
	}

	private <T extends BaseConfig> void registerConfig(Class<T> configClass, T configInstance) {
		configs.put(configClass, configInstance);
	}

	public <T extends BaseConfig> T getConfig(Class<T> configClass) {
		return configClass.cast(configs.get(configClass));
	}

	public void reloadConfigurations() {
		configs.clear();
		loadConfigurations();
	}
}
