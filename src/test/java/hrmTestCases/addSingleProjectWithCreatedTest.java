package hrmTestCases;


import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import BaseApiClass.BaseClass;
import contants.endpoints.IEndPoints;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import pojoclass.utility.createProject;

public class addSingleProjectWithCreatedTest extends BaseClass {
	String projectId;
	@Test
	public void addProject() throws Exception{
		
		String projectName = "Sample"+Jlib.getRandomNumber();
		String expMsg = "Successfully Added";
		
		createProject pObj = new createProject(projectName, "Venkat", 0, "Created");

		 Response resp = given()
			.spec(specReqObj)     //Always first Line in given()
//			.contentType(ContentType.JSON)
			.body(pObj)
			
		.when()
//			.post(Flib.readDataFromPropertyFile("BaseUri")+IEndPoints.addProj);
		 	.post(IEndPoints.addProj);
		 
		resp.then()
			.assertThat().statusCode(201)
//			.contentType("application/json")
			.time(Matchers.lessThan(3000L))
			.log().all()
			.spec(specRespObj);   //Always last line in then()

//		String actMsg = resp.jsonPath().get("msg");
//		Assert.assertEquals(expMsg, actMsg);
		System.out.println(Jsonlib.VerifyDataonJsonPath(resp,".msg", expMsg));
		
		projectId = resp.jsonPath().get("projectId");
		
		
		//DB Validation
		
//		Connection conn = null;
//		
//			Driver d = new Driver();
//			DriverManager.registerDriver(d);
//			String url ="jdbc:mysql://49.249.28.218:3333/ninza_hrm";
//			conn = DriverManager.getConnection(url, "root@%", "root");
//			System.out.println("=Connection Done=");
//			Statement stmt = conn.createStatement();
//			ResultSet result = stmt.executeQuery("select * from project");
//			
//			while(result.next()) {
//				System.out.println(result.getString(4));
//			}
//		
//			conn.close();
			
	}
	
	@Test
	public void deleteProject() {
				given()
					.spec(specReqObj)
					.delete(IEndPoints.delProj+projectId)
				.then()
					.log().all()
					.statusCode(204)
					.spec(specRespObj);
	}
}
