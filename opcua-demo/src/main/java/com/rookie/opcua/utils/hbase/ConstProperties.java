package com.rookie.opcua.utils.hbase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConstProperties {

	public static String findPropertiesByKey(String key) {
		InputStream in = null;
		try {
			in = ConstProperties.class.getClassLoader().getResourceAsStream(
					"const.properties");
			Properties properties = new Properties();
			properties.load(in);
			//
			String value = properties.getProperty(key);
			return value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
