package postRequestTypes;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import io.restassured.http.ContentType;
public class WithHashMap {
	@Test
	public void PostProject() {
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("createdBy", "Venkat");
		map.put("projectName", "Sample 3");
		map.put("status", "Created");
		map.put("teamSize", 0);

		given()
			.contentType(ContentType.JSON)
			.body(map)
		.when()
			.post("http://49.249.28.218:8091/addProject")
		.then()
			.assertThat()
			.statusCode(201)
			.log().all();
		
			
		
	}
}
