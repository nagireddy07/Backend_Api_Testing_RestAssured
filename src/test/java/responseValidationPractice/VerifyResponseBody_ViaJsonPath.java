package responseValidationPractice;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.*;

public class VerifyResponseBody_ViaJsonPath {
	@Test
	public void getAllProjects() {
		Response resp = given().get("http://49.249.28.218:8091/projects-paginated");
		
		//Getting data from Response Body
		int data1 = resp.jsonPath().get("numberOfElements");
		System.out.println(data1);
		Object data2 = resp.jsonPath().get("pageable.sort.sorted");
		System.out.println(data2);
		Object data3 = resp.jsonPath().get("content[0].projectId");
		System.out.println(data3);
		
		//Validating Response Body
		ValidatableResponse validate = resp.then().assertThat();
		validate.body("numberOfElements", Matchers.greaterThanOrEqualTo(20));
		validate.body("pageable.sort.sorted", Matchers.equalTo(false));
		
		resp.then().log().all();
		
	}
}
