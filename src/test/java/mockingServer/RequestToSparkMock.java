package mockingServer;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;


public class RequestToSparkMock {
	@SuppressWarnings("unchecked")
	@Test
	public void postCreditCard() {
		JSONObject obj = new JSONObject();
		obj.put("creditcard", "123456789124");
		obj.put("cvv", "123");
		given()
			.body(obj)
			.contentType(ContentType.JSON)
		.when()
			.post("http://localhost:8989/credit-card")
		.then()
			.log().all()
			.assertThat().statusCode(200);
			
			
		
	}
}
