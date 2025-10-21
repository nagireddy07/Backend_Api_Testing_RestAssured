package ninzaHRM.crudWithoutBDD;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateProjectPost {
	@Test
	public void PostProject() {
		
		JSONObject obj = new JSONObject();
		obj.put("createdBy", "Venkat");
		obj.put("projectName", "Sample");
		obj.put("status", "Created");
		obj.put("teamSize", 0);
		
		RequestSpecification req = RestAssured.given();
		req.contentType(ContentType.JSON);
		req.body(obj.toJSONString());
		Response Resp = req.post("http://49.249.28.218:8091/addProject");
		Resp.then().assertThat().statusCode(201);
		Resp.then().log().all();
		
	}
}
