package bitsapi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import net.datafaker.Faker;

public class Payloads {
	
    public static Map<String, Object> getCreateUserPayloadFromMap() {
        Map<String, Object> payload = new HashMap<>();
        Faker faker = new Faker();
        Date dob1 = faker.date().birthday();
        String dob = formatDateWithoutTime(dob1);
        
        payload.put("title", "Mr");
        payload.put("firstName", faker.name().firstName());
        payload.put("lastName", faker.name().lastName());
        payload.put("dateOfBirth", dob);
        payload.put("email", faker.name().firstName()+"@gmail.com");
        payload.put("password", generateRandomPassword(10));
        payload.put("rating", faker.number().numberBetween(1, 5));
        return payload;
    }
    
    private static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
    

    // Function to format date without time
    private static String formatDateWithoutTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
