package ninzaHRM.crudWithBDD;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;


public class CreateProjectPost {
	@Test
	public void PostProject() {
		
		JSONObject obj = new JSONObject();
		obj.put("createdBy", "Venkat");
		obj.put("projectName", "Sample 2");
		obj.put("status", "Created");
		obj.put("teamSize", 0);

		given()
			.contentType(ContentType.JSON)
			.body(obj.toJSONString())
		.when()
			.post("http://49.249.28.218:8091/addProject")
		.then()
			.assertThat()
			.statusCode(201)
			.log().all();
		
			
		
	}
}
