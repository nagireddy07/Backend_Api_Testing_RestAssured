package typesOfParameters;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class QueryParamTest {
	@Test
	public void getProject() {
		given()
			.queryParam("teamSize", "1")
			.log().all()
		.when()
			.get("http://49.249.28.218:8091/project")  //http://49.249.28.218:8091/project?teamSize=1			
		.then()
			.assertThat().statusCode(200)
		.log().all();
	}
}
