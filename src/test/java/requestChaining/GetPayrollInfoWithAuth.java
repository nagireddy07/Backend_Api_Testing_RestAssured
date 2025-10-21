package requestChaining;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class GetPayrollInfoWithAuth {
	@Test
	public void getPayrollInfo() {
		
		Response resp = given()
			.formParam("client_id", "ninza-client")
			.formParam("client_secret", "gPQBf1Yxew50MccMhzos1GefIyiSnXzM")
			.formParam("grant_type", "client_credentials")
		.when()
			.post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");
		resp.then().log().all();
			
	}
}
