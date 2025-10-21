package ninzaHRM.crudWithoutBDD;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAllProjectsInfo {
	@Test
	public void getAllProjects() {
		Response Resp = RestAssured.get("http://49.249.28.218:8091/projects");
		Resp.then().assertThat().statusCode(200);
		Resp.then().log().all();
	}
}
