package samplePractice;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class SampleMethodChaining {
	@Test
	public void sampleGetMethod() {
		get("https://reqres.in/api/users?page=2")
		.then()
		.log()
		.all();
	}
}
