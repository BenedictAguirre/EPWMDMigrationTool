package com.svi.epwmd.migrationtool.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static Properties properties = null;

	private Config(){}

	public static String getProperty(String property) {
		if (properties == null) {
			loadProperties();
			return properties.getProperty(property);
		} else {
			return properties.getProperty(property);
		}
	}
	
	public static void setProperty(String key, String value) {
		if (properties == null) {
			loadProperties();
			properties.setProperty(key, value);
		} else {
			properties.setProperty(key, value);
		}
	}

	private static void loadProperties() {
		properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("config/config.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			System.err.println("Config file not found");
		} catch (IOException e) {
			System.err.println("Error reading config file");
		}
	}

}
