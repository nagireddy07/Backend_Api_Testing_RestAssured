package ninzaHRM.crudWithBDD;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

public class DeleteProject {
	@Test
	public void deleteProject() {
//		Response Resp = RestAssured.delete("http://49.249.28.218:8091/project/NH_PROJ_1890");
//		Resp.then().assertThat().statusCode(204);
//		Resp.then().log().all();
		
		RestAssured.baseURI = "http://49.249.28.218:8091";
		
		given()
			.delete("/project/NH_PROJ_1964")
		.then()
			.assertThat().statusCode(204)
			.log().all();
	}
}
