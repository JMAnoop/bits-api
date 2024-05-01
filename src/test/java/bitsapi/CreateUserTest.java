package bitsapi;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import restUtils.RestUtils;
import utils.HeaderConfig;
import utils.JsonValidator;
import utils.ReadPropertyFile;
import utils.ShareData;

public class CreateUserTest extends RestUtils {
	String endPoint = ReadPropertyFile.getProperty("create_user");
	HeaderConfig headerConfig = new HeaderConfig();

	@Test
	public void createUser() {

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());

		String userId = response.jsonPath().get("data.userId");
		String email = response.jsonPath().get("data.email");
		System.out.println(email);

		ShareData.setValue("userId", userId);
		ShareData.setValue("email", email);

		Assert.assertEquals(response.statusCode(), 200);

	}

	@Test
	public void getUser() {

		String user_id = (String) ShareData.getValue("userId");
		String getUserEndpoint = ReadPropertyFile.getProperty("get_user") + user_id;

		Response response = performGet(getUserEndpoint, headerConfig.headersWithToken());
		String userIdfromGet = response.jsonPath().get("data.userId");

		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(userIdfromGet, user_id);

	}

	@Test
	public void verifyJsonResponse() {

		JsonValidator validator = new JsonValidator();

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		String res = response.asString();

		Boolean value = validator.isValid(res);
		Assert.assertEquals(value, true);

	}

	@Test
	public void uniqueEmail() {

		String email = (String) ShareData.getValue("email");

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("email", email);

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyIncorrectEmail() {

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("email", "test1.com");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyPostWithoutAuthorization() {

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		HashMap<String, String> header = new HashMap<>();
		
		Response response = performPost(endPoint, payload, header);
		Assert.assertEquals(response.statusCode(), 401);
	}

	@Test
	public void verifyPostWithIncorrectAuthorization() {

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		HashMap<String, String> header = new HashMap<>();
		header.put("Authorization", "GombImxtesting12345");
		Response response = performPost(endPoint, payload, header);
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyGetCallWithoutAuthorizationKey() {
		String user_id1 = (String) ShareData.getValue("userId");
		String getUserEndpoint = ReadPropertyFile.getProperty("get_user") + user_id1;
		
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		HashMap<String, String> header = new HashMap<>();
		// header.put("Authorization", "GombImxOhMCa8AqMmNM9KEFwaSHSFHty");
		Response response = performGet(endPoint, header);
		Assert.assertEquals(response.statusCode(), 403);
	}

	@Test
	public void verifyGetCallWithIncorrectAuthorizationKey() {
		String user_id1 = (String) ShareData.getValue("userId");
		String getUserEndpoint = ReadPropertyFile.getProperty("get_user") + user_id1;

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		HashMap<String, String> header = new HashMap<>();
		header.put("Authorization", "testing");
		Response response = performGet(endPoint, header);
		Assert.assertEquals(response.statusCode(), 403);
	}

	@Test
	public void createUserWithoutTitle() {

		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("title", "");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyFirstName() {
		
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("firstName", "");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyFirstNameCannotHaveMoreThan255Charcters() {
		
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("firstName",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyFirstNameWithSingleCharater() {
		
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("firstName", "A");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyLastName() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("lastName", "");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyLastNameCannotHaveMoreThan255Charcters() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("lastName",
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries");
	
		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyLastNameWithSingleCharater() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("lastName", "P");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifydateOfBirth() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("dateOfBirth", "");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyAgeGreatherThan18() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("dateOfBirth", "2020-06-04");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyIncorrectYearInDateOfBirth() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("dateOfBirth", "20-06-04");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyIncorrectMonthInDateOfBirth() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("dateOfBirth", "1987-18-04");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyIncorrectDayInDateOfBirth() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("dateOfBirth", "1987-10-55");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyPassword() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("password", "");
	
		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyMinimumPasswordLength() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("password", "12");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyStatusForRatingBetweenOneAndFive() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("rating", 1);

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		String status = response.jsonPath().get("data.status");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(status, "new");
	}

	@Test
	public void verifyStatusForRatingGreaterThanFiveLessThanTen() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("rating", 9);

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		String status = response.jsonPath().get("data.status");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(status, "active");
	}

	@Test
	public void verifyStatusForRatingLessThanZero() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("rating", -1);

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		String status = response.jsonPath().get("data.status");
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(status, "rejected");
	}

	@Test
	public void verifyRatingGreaterThanTen() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("rating", 15);

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

	@Test
	public void verifyRatingWithStringValues() {
		Map<String, Object> payload = Payloads.getCreateUserPayloadFromMap();
		payload.put("rating", "rate123");

		Response response = performPost(endPoint, payload, headerConfig.headersWithToken());
		Assert.assertEquals(response.statusCode(), 400);
	}

}
