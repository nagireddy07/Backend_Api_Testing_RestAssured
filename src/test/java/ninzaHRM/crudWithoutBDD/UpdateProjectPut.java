package ninzaHRM.crudWithoutBDD;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UpdateProjectPut {
	@Test
	public void PutProject() {
		
		JSONObject obj = new JSONObject();
		obj.put("createdBy", "Venkat");
		obj.put("projectName", "Updated Sample");
		obj.put("status", "Created");
		obj.put("teamSize", 0);
		
		RequestSpecification req = RestAssured.given();
		req.contentType(ContentType.JSON);
		req.body(obj.toJSONString());
		Response Resp = req.put("http://49.249.28.218:8091/project/NH_PROJ_1890");
		Resp.then().assertThat().statusCode(200);
		Resp.then().log().all();
		
	}
}
