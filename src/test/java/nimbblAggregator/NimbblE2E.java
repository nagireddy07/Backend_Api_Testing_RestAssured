package nimbblAggregator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.time.Duration;
import java.util.Random;

public class NimbblE2E {
	
	static String token;
	static String order_id;
	static String url;
	@Test(priority = 1)
	public void getToken() {
		RestAssured.baseURI = "https://api.nimbbl.tech";
		String body = "{\r\n"
				+ "  \"access_key\": \"access_key_pKx7rWVgVpbXQvq2\",\r\n"
				+ "  \"access_secret\": \"access_secret_DX3w55VKAkXbx7aB\"\r\n"
				+ "}";
		
		Response resp = given()
			.body(body)
		.when()
			.post("/api/v3/generate-token");
		resp.then().assertThat().statusCode(200).log().all();
		token  = resp.jsonPath().get("token");
		
		System.out.println("==========Token Created ================");
	}
	
	@Test(priority = 2)
	public void postOrderId() {
		RestAssured.baseURI = "https://api.nimbbl.tech";
		String invoice_id = "inv_asjjeibdh"+new Random().nextInt(1000);
		String body = "{\r\n"
				+ "  \"quantity\": 2,\r\n"
				+ "  \"amount_before_tax\": 2100,\r\n"
				+ "  \"tax\": 105,\r\n"
				+ "  \"total_amount\": 2205,\r\n"
				+ "  \"user\": {\r\n"
				+ "    \"email\": \"wonderwoman@themyscira.gov\",\r\n"
				+ "    \"first_name\": \"Diana\",\r\n"
				+ "    \"last_name\": \"Prince\",\r\n"
				+ "    \"country_code\": \"+91\",\r\n"
				+ "    \"mobile_number\": \"9876543210\"\r\n"
				+ "  },\r\n"
				+ "  \"shipping_address\": {\r\n"
				+ "    \"address_1\": \"1080 Beach Mansion\",\r\n"
				+ "    \"street\": \"Magic Beach Drive\",\r\n"
				+ "    \"landmark\": \"Opposite Magic Mountain\",\r\n"
				+ "    \"area\": \"Elyria\",\r\n"
				+ "    \"city\": \"Atlantis\",\r\n"
				+ "    \"state\": \"Castalia\",\r\n"
				+ "    \"pincode\": \"100389\",\r\n"
				+ "    \"address_type\": \"Beach House\",\r\n"
				+ "    \"label\": \"Sunny Home\"\r\n"
				+ "  },\r\n"
				+ "  \"billing_address\": {\r\n"
				+ "    \"address_1\": \"1080 Beach Mansion\",\r\n"
				+ "    \"street\": \"Magic Beach Drive\",\r\n"
				+ "    \"landmark\": \"Opposite Magic Mountain\",\r\n"
				+ "    \"area\": \"Elyria\",\r\n"
				+ "    \"city\": \"Atlantis\",\r\n"
				+ "    \"state\": \"Castalia\",\r\n"
				+ "    \"pincode\": \"100389\",\r\n"
				+ "    \"address_type\": \"Beach House\",\r\n"
				+ "    \"label\": \"Sunny Home\"\r\n"
				+ "  },\r\n"
				+ "  \"currency\": \"INR\",\r\n"
				+ "  \"invoice_id\": \""+invoice_id+"\",\r\n"
				+ "  \"referrer_platform\": \"string\",\r\n"
				+ "  \"referrer_platform_version\": \"string\",\r\n"
				+ "  \"ip_address\": \"106.201.232.161\",\r\n"
				+ "  \"merchant_shopfront_domain\": \"https://merchant-shopfront.example.com\",\r\n"
				+ "  \"offer_enabled\": false,\r\n"
				+ "  \"validate_order_line_item\": false,\r\n"
				+ "  \"order_line_items\": [\r\n"
				+ "    {\r\n"
				+ "      \"sku_id\": \"item_2783027490\",\r\n"
				+ "      \"title\": \"Best Sliced Alphonso Mango\",\r\n"
				+ "      \"description\": \"The Alphonso mango is a seasonal fruit harvested from mid-April through the end of June. The time from flowering to harvest is about 90 days, while the time from harvest to ripening is about 15 days.The fruits generally weigh between 150 and 300 grams (5.3 and 10.6 oz), have a rich, creamy, tender texture and delicate, non-fibrous, juicy pulp. As the fruit matures, the skin of an Alphonso mango turns golden-yellow with a tinge of red across the top of the fruit\",\r\n"
				+ "      \"image_url\": \"https://en.wikipedia.org/wiki/Alphonso_mango#/media/File:Alphonso_mango.jpg\",\r\n"
				+ "      \"rate\": 1050,\r\n"
				+ "      \"quantity\": \"2\",\r\n"
				+ "      \"amount_before_tax\": \"2100.00\",\r\n"
				+ "      \"tax\": \"105.00\",\r\n"
				+ "      \"total_amount\": \"2205.00\",\r\n"
				+ "      \"serial_numbers\": [\r\n"
				+ "        \"359043372654548\",\r\n"
				+ "        \"359043371395481\"\r\n"
				+ "      ]\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"bank_account\": {\r\n"
				+ "    \"account_number\": \"10038849992883\",\r\n"
				+ "    \"name\": \"Diana Prince\",\r\n"
				+ "    \"ifsc\": \"ICIC0000011\"\r\n"
				+ "  },\r\n"
				+ "  \"custom_attributes\": {\r\n"
				+ "    \"name\": \"Diana\",\r\n"
				+ "    \"place\": \"Themyscira\",\r\n"
				+ "    \"animal\": \"Jumpa\",\r\n"
				+ "    \"thing\": \"Tiara\"\r\n"
				+ "  }\r\n"
				+ "}";
		System.out.println("Token = "+token);
		Response resp = given()
			.header("Authorization","Bearer "+token)
			.body(body)
		.when()
			.post("/api/v3/create-order");
		order_id = resp.jsonPath().get("order_id");
		resp.then().assertThat().statusCode(201).log().all();
		System.out.println("OrderId = "+order_id);
		
		System.out.println("==========Order Created ================");
			
	}
	
//	@Test(priority = 3)
//	public void getPaymentModes() {
//		RestAssured.baseURI = "https://api.nimbbl.tech";
//		String body = "{\r\n"
//				+ "  \"order_id\": \""+order_id+"\"\r\n"
//				+ "}";
//		Response resp = given()
//				.header("Authorization","Bearer "+token)
//				.body(body)
//			.when()
//				.post("/api/v3/payment-modes");
//		resp.then().log().all();
//		
//	}
	


	
	@Test(priority = 4)
	public void initiateTxn() {
		RestAssured.baseURI = "https://api.nimbbl.tech";
		String body = "{\r\n"
				+ "  \"order_id\": \""+order_id+"\",\r\n"
				+ "  \"callback_url\": \"https://mangoseller.awesome/transaction-response\",\r\n"
				+ "  \"payment_mode_code\": \"net_banking\",\r\n"
				+ "  \"bank_code\": \"axis\"\r\n"
				+ "}";
		Response resp = given()
			.header("Authorization","Bearer "+token)
			.body(body)
		.when()
			.post("/api/v3/initiate-payment");
		resp.then().log().all();
		url = resp.jsonPath().get("next[0].url");
		System.out.println(url);
		
		System.out.println("==========Transaction Initiated ================");
		
	}
	
	
	@Test(priority = 5)
	public void webHookTest() throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(url);
		driver.manage().window().maximize();
		driver.findElement(By.id("username")).sendKeys("payu");
		driver.findElement(By.id("password")).sendKeys("payu");
		driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
		WebElement successSimulate = driver.findElement(By.xpath("//input[@value=\"Simulate Success Response\"]"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(successSimulate));
		driver.quit();
		
		System.out.println("==========Webhook Validated ================");
	}
	
}
