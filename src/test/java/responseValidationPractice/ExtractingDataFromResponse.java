package responseValidationPractice;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;


import static io.restassured.RestAssured.*;

import java.util.List;

public class ExtractingDataFromResponse {
	@Test
	public void getAllProjects() {
		Response resp = given().get("http://49.249.28.218:8091/projects-paginated");

		List<String> list = JsonPath.read(resp.asString(), ".content[*].[?(@.projectName=='Airtel_4335')].projectId");
		String data = list.get(0);
		System.out.println(data);
		
		//Extracting Data
		//Request Chaining
		//DB Validation
		
	}
}
