package typesOfParameters;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class PathParamTest {
	@Test
	public void getProject() {
		given()
			.pathParam("projectId", "NH_PROJ_001")
			.get("http://49.249.28.218:8091/project/{projectId}")
		.then()
			.assertThat().statusCode(200)
		.log().all();
	}
}
