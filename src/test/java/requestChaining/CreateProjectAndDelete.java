package requestChaining;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.Random;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoclass.utility.createProject;
public class CreateProjectAndDelete {
	@Test
	public void PostProject() {
		Random rand = new Random();
		int random = rand.nextInt(5000);
		createProject pObj = new createProject("Sample"+random, "Venkat", 0, "Created");
		
		//API-1 ==> Creating Project
		 Response resp = given()
			.contentType(ContentType.JSON)
			.body(pObj)
		.when()
			.post("http://49.249.28.218:8091/addProject");
		 
		resp.then()
			.assertThat()
			.statusCode(201)
			.log().all();
		
		Object projectID = resp.jsonPath().get("projectId");
		System.out.println(projectID + "\n--------------------------------------------------------------------");
			
		//API-2 ==> Deleting Project
		given()
			.delete("http://49.249.28.218:8091/project/"+projectID)
		.then()
			.log().all()
			.statusCode(204);
		
	}
}
