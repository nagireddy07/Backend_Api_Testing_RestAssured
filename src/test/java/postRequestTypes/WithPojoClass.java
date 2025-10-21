package postRequestTypes;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.Random;

import io.restassured.http.ContentType;
import pojoclass.utility.createProject;
public class WithPojoClass {
	@Test
	public void PostProject() {
		Random rand = new Random();
		int random = rand.nextInt(5000);
		createProject pObj = new createProject("Sample"+random, "Venkat", 0, "Created");

		given()
			.contentType(ContentType.JSON)
			.body(pObj)
		.when()
			.post("http://49.249.28.218:8091/addProject")
		.then()
			.assertThat()
			.statusCode(201)
			.log().all();
		
			
		
	}
}
