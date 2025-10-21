package dataDrivenTesting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoclass.utility.createProject;
public class CreateProjectAndDelete {
	@Test(dataProvider = "getData")
	public void PostProject(String pName,String status) {
		
		createProject pObj = new createProject(pName, "Venkat", 0, status);
		
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
	@DataProvider
	public Object[][] getData() {
		Object[][] data = new Object[3][2];
		
		data[0][0] = "Project_1";
		data[0][1] = "Created";
		
		data[1][0] = "Project_2";
		data[1][1] = "Created";
		
		data[2][0] = "Project_3";
		data[2][1] = "Created";
		return data;
		
	}
}
