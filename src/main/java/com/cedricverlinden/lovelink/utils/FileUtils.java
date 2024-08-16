package com.cedricverlinden.lovelink.utils;

import com.cedricverlinden.lovelink.LoveLink;

import java.io.File;

public class FileUtils {

	static LoveLink plugin = LoveLink.getPlugin();

	public static boolean doesResourceExist(String folderName, String fileName) {
		folderName = plugin.getDataFolder() + File.separator + folderName;
		File file = new File(folderName, fileName);
		return file.exists();
	}

	public static boolean doesResourceExist(String fileName) {
		File file = new File(plugin.getDataFolder(), fileName);
		return file.exists();
	}
}
