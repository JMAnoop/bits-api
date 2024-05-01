package utils;

import java.util.HashMap;
import java.util.Map;

public class HeaderConfig {
	
    String apikey = ReadPropertyFile.getProperty("api_key");
	
	public Map<String, String> headersWithToken() {
		
		Map<String,String> header = new HashMap<String,String>();
		header.put("Authorization", apikey);
		
		return header;
	}

}
