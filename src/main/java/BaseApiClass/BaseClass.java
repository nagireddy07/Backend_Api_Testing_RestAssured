package BaseApiClass;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import backendTesting.genericUtilities.FileUtility;
import backendTesting.genericUtilities.JSON_Utility;
import backendTesting.genericUtilities.JavaUtility;
//import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseClass {
	public JavaUtility Jlib = new JavaUtility();
	public FileUtility Flib = new FileUtility();
	public JSON_Utility Jsonlib = new JSON_Utility();
	public static RequestSpecification specReqObj;
	public static  ResponseSpecification specRespObj;
	
	
	@BeforeSuite
	public void configDBConnection() throws Exception {
		System.out.println("=================CONNECTED TO DB============");
		
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setContentType(ContentType.JSON);
//		builder.setAuth(basic("un", "pass"));
//		builder.addHeader("", "");
		builder.setBaseUri(Flib.readDataFromPropertyFile("BaseUri"));
		specReqObj=builder.build();
		
		ResponseSpecBuilder respBuilder = new ResponseSpecBuilder();
		respBuilder.expectContentType(ContentType.JSON);
		specRespObj=respBuilder.build();
	}
	
	@AfterSuite
	public void configDBClose() {
		System.out.println("=================DISCONNECTED FROM DB============");
	}
}
