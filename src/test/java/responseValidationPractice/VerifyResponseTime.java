package responseValidationPractice;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class VerifyResponseTime {
	@Test
	public void getAllProjects() {
		Response resp = given().get("http://49.249.28.218:8091/projects");
		resp.then().log().all();
		System.out.println(resp.time()+"ms");
		resp.then().assertThat().time(Matchers.lessThan(900L));
		resp.then().assertThat().time(Matchers.greaterThan(300L));
		resp.then().assertThat().time(Matchers.both(Matchers.lessThan(900L)).and(Matchers.greaterThan(300L)));
		
	}
}
