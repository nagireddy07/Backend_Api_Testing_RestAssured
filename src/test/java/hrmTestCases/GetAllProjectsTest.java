package hrmTestCases;

import org.testng.annotations.Test;

import BaseApiClass.BaseClass;
import contants.endpoints.IEndPoints;

import static io.restassured.RestAssured.*;

public class GetAllProjectsTest extends BaseClass {
	@Test
	public void getAllProjects() {
		given()
			.spec(specReqObj)
		.when()
			.get(IEndPoints.getProj)
		.then()
			.assertThat().statusCode(200)
			.log().all()
			.spec(specRespObj);
	}
}
