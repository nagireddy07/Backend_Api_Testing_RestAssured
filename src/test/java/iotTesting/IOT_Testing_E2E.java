package iotTesting;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Random;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IOT_Testing_E2E
{
     private static final String FILE_NAME = System.getProperty("user.home") + "/Downloads/SensorData.xlsx";
    @SuppressWarnings("unchecked")
	@Test
    public void eyeOnIOT() throws InterruptedException
    {
    
        
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("temperature", 93);
        jsonObject.put("healthIndex", "25.3");
        jsonObject.put("criticalityIndex", "22.6");
        given()
        .contentType("application/json")
        .body(jsonObject)
        .when()
        .patch("http://49.249.28.218:8095/api/update-transformer?id=1")
        .then()
        .assertThat().statusCode(200)
        .log().all();
        
    
        
        WebDriver driver=new EdgeDriver();
        driver.manage().window().maximize();
        driver.get("http://49.249.28.218:8095/");
        driver.findElement(By.id("username")).sendKeys("rmgyantra");
        driver.findElement(By.id("password")).sendKeys("rmgy@9999");
        driver.findElement(By.xpath("//button[.='Sign In']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a[.='Transformers']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//li[contains(.,'TX-100')]")).click();
        String eyeOnHandle=driver.getWindowHandle();
        
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://thingsboard.cloud/home");
        Thread.sleep(2000);
        
        Actions actions=new Actions(driver);
        actions.scrollToElement(driver.findElement(By.xpath("//input[@formcontrolname='username']"))).perform();
        
        driver.findElement(By.xpath("//input[@formcontrolname='username']")).sendKeys("yashas.y@fireflink.com");
        driver.findElement(By.id("password-input")).sendKeys("Chandhu@123");
        driver.findElement(By.xpath("//span[.='Login']")).click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement devicesButton = wait.until(ExpectedConditions.elementToBeClickable(
                   By.xpath("//span[@class='mdc-button__label']/child::span[.='Devices']")));
        devicesButton.click();

            
        WebElement mqttDevice = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[.='mymqttDevice']")));
        mqttDevice.click();

            
        WebElement telemetryTab = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[.='Latest telemetry' and @class='mdc-tab__text-label']")));
        telemetryTab.click();
        createExcelFile();
        readExcelFileAndSendAPIRequest();
            
            
        
        
    }
    
     public void createExcelFile() {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("SensorData");

            // Header Row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Temperature");
            headerRow.createCell(1).setCellValue("HealthIndex");
            headerRow.createCell(2).setCellValue("CriticalityIndex");

            Random random = new Random();

            // Create 3 rows of random values
            for (int i = 1; i <= 3; i++) {
                Row row = sheet.createRow(i);

                int temperature = random.nextInt(100); 
                double healthIndex = random.nextDouble() * 10; // < 10
                double criticalityIndex = random.nextDouble() * 5; // < 5

                if(i==3)
                    temperature+=100;
                row.createCell(0).setCellValue(temperature);
                row.createCell(1).setCellValue(healthIndex);
                row.createCell(2).setCellValue(criticalityIndex);
                
                System.out.println("Temp: "+temperature+" health: "+healthIndex+" critical: "+criticalityIndex);
            }

            // Auto-size columns
            for (int i = 0; i < 3; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the Excel file
            try (FileOutputStream outputStream = new FileOutputStream(FILE_NAME)) {
                workbook.write(outputStream);
                System.out.println("Excel file created successfully at: " + FILE_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     
     public void readExcelFileAndSendAPIRequest() {
            try (FileInputStream fis = new FileInputStream(FILE_NAME);
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);
                int rowCount = sheet.getPhysicalNumberOfRows();

                System.out.println("\n📘 Reading Excel Data:");
                for (int i = 1; i < rowCount; i++) { // skip header
                    Row row = sheet.getRow(i);
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("temperature", (int) row.getCell(0).getNumericCellValue());
                    jsonObject.put("healthIndex", row.getCell(1).getNumericCellValue());
                    jsonObject.put("criticalityIndex", row.getCell(2).getNumericCellValue());
                    
                     given()
                     .contentType("application/json")
                     .body(jsonObject)
                     .when()
                         .post("http://thingsboard.cloud/api/v1/UO7mKGYe0Lik8w7dMzBw/telemetry")
                     .then()
                      .log().all();
                    
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
     }
    
}