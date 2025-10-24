package ninzaHRM.crudWithBDD;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class CreateProjectPost {
	@Test
	public void PostProject() {
		
		JSONObject obj = new JSONObject();
		obj.put("createdBy", "Venkat");
		obj.put("projectName", "Sample_4");
		obj.put("status", "Created");
		obj.put("teamSize", 0);
		
		RestAssured.baseURI = "http://49.249.28.218:8091";
		RestAssured.basePath = "/addProject";

		given()
			.contentType(ContentType.JSON)
			.body(obj.toJSONString())
		.when()
			.post()
		.then()
			.assertThat()
			.statusCode(201)
			.log().all();
		
			
		
	}
}
