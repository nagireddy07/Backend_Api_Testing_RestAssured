package responseValidationPractice;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class VerifyResponseHeader {
	@Test
	public void getAllProjects() {
		Response resp = given().get("http://49.249.28.218:8091/projects");
		
		resp.then().assertThat().statusCode(200);
		resp.then().assertThat().contentType(ContentType.JSON);
		resp.then().assertThat().header("Connection", "keep-alive");
		
		resp.then().log().all();
		
	}
}
