package utils;

import java.util.HashMap;

public class ShareData {
	
	public ShareData() {
		
	}
	
	private static HashMap<String, Object> dataMap = new HashMap<>();
	
	public static void setValue(String key, Object value) {
		dataMap.put(key,value);
	}
	
	public static Object getValue(String key) {
		return dataMap.get(key);
	}

}
