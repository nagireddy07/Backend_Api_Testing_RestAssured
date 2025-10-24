package ninzaHRM.crudWithBDD;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

public class GetAllProjectsInfo {
	@Test
	public void getAllProjects() {
		RestAssured.baseURI = "http://49.249.28.218:8091";
		
		given()
			.get("/projects")
		.then()
			.assertThat().statusCode(200)
		.log().all();
	}
}
