package encryption;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GetAllProjectsInfo {
	@Test
	public void getAllProjects() {
		given()
			.auth().preemptive().basic("rmgyantra", "rmgy@9999")
			.log().all()
//		.when()
			.get("http://49.249.28.218:8091/projects")
			
		.then()
			.assertThat().statusCode(200)
		.log().all();
	}
}
