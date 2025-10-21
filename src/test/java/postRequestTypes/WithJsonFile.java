package postRequestTypes;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import java.io.File;


import io.restassured.http.ContentType;
public class WithJsonFile {
	@Test
	public void PostProject() {
		File file = new File("./sample.json");
		given()
			.contentType(ContentType.JSON)
			.body(file)
		.when()
			.post("http://49.249.28.218:8091/addProject")
		.then()
			.assertThat()
			.statusCode(201)
			.log().all();
		
			
		
	}
}
