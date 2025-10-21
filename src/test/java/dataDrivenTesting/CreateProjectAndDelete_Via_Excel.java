package dataDrivenTesting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.io.IOException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojoclass.utility.createProject;
public class CreateProjectAndDelete_Via_Excel {
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
	public Object[][] getData() throws IOException {
		Object[][] data = FileUtility.getMultipleDataFromExcel("addProject");
		return data;
		
	}
}
