package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonValidator {
	
	public boolean isValid(String json) {
	    try {
	        new JSONObject(json);
	    } catch (JSONException e) {
	        try {
	            new JSONArray(json);
	        } catch (JSONException ne) {
	            return false;
	        }
	    }
	    return true;
	}

}
