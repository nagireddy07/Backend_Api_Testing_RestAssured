package typesOfParameters;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class FormParamTest {
	@Test
	public void getProject() {
		given()
			.formParam("projectId", "NH_PROJ_001")
			.log().all()
		.when()
			.post("http://49.249.28.218:8091/project")  //http://49.249.28.218:8091/project?teamSize=1			
		.then()
			.assertThat().statusCode(200)
		.log().all();
	}
}
