package ninzaHRM.crudWithBDD;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
public class UpdateProjectPut {
	@Test
	public void PutProject() {
		
		JSONObject obj = new JSONObject();
		obj.put("createdBy", "Venkat");
		obj.put("projectName", "Updated Sample");
		obj.put("status", "Created");
		obj.put("teamSize", 0);

		given()
			.contentType(ContentType.JSON)
			.body(obj.toJSONString())
		.when()
			.put("http://49.249.28.218:8091/project/NH_PROJ_1964")
		.then()
			.assertThat().statusCode(200)
			.log().all();
		
	}
}
