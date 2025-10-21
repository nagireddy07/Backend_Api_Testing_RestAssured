package requestChaining;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.Random;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoclass.utility.AddEmployee;
import pojoclass.utility.createProject;
public class CreateProjectAndAddEmployee {
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
		
		String projectName = resp.jsonPath().get("projectName");
		System.out.println(projectName + "\n--------------------------------------------------------------------");
		
		
		//API-2 ==> Adding Employee into Project
		
		AddEmployee empPojo = new AddEmployee("Developer", "01/01/2002", "practice@gmail.com", "user"+random, 10, "9234554321", projectName, "ROLE_EMPLOYEE", "user"+random);
		given()
			.contentType(ContentType.JSON)
			.body(empPojo)
		.when()
			.post("http://49.249.28.218:8091/employees")
		.then()
			.log().all()
			.assertThat().statusCode(201);
		
		
		//API-3 ==>Delete Project
		Object projectID = resp.jsonPath().get("projectId");
		System.out.println(projectID + "\n--------------------------------------------------------------------");
			
		
		given()
			.delete("http://49.249.28.218:8091/project/"+projectID)
		.then()
			.log().all()
			.statusCode(204);
			
	}
}
