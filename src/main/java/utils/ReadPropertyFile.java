package utils;

import java.io.FileReader;
import java.util.Properties;

public class ReadPropertyFile {
	
	public static String getProperty(String key) {
		Properties prop = new Properties();
		try {
			FileReader fr = new FileReader(System.getProperty("user.dir")+"/resources/configfiles/config.properties");
			prop.load(fr);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return prop.getProperty(key);
	}

}
