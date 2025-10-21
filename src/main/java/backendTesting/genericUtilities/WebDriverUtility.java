package backendTesting.genericUtilities;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtility {
	/**
	 * This Method consists of generic methods related to Web Drivers
	 * @author Bobbala Venkata Nagi
	 */
	
	/**
	 * This Method will minimize the window
	 * @param driver
	 */
	public void minimizeWindow(WebDriver driver) {
		driver.manage().window().minimize();
	}
	
	/**
	 * This Method will maximize the window
	 * @param driver
	 */
	public void maximizeWindow(WebDriver driver) {
		driver.manage().window().maximize();
	}
	
	/**
	 * This Method will fullscreen the window
	 * @param driver
	 */
	public void fullscreenWindow(WebDriver driver) {
		driver.manage().window().fullscreen();
	}
	/**
	 * This Method used to wait implicitly
	 * @param driver
	 */
	public void waitImplicitly(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	/**
	 * This Method used to wait explicitly based on condition
	 * @param driver
	 * @param ele
	 */
	public void waitExplicitly(WebDriver driver,WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ele));
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	/**
	 * This Method Select the value based on visible text in the drop down
	 * @param ele
	 * @param text
	 */
	public void handleDropDown(WebElement ele,String text) {
		Select s = new Select(ele);
		s.selectByVisibleText(text);
	}
	/**
	 * This Method Select the value based on index in the drop down
	 * @param ele
	 * @param index
	 */
	public void handleDropDown(WebElement ele,int index) {
		Select s = new Select(ele);
		s.selectByIndex(index);
	}
	/**
	 * This Method Select the value based on name attribute value in the drop down
	 * @param value
	 * @param ele
	 */
	public void handleDropDown(String value,WebElement ele) {
		Select s = new Select(ele);
		s.selectByValue(value);
	}
	
	/**
	 * This Method is Used to move to element
	 * @param driver
	 * @param ele
	 */
	public void mouseHoveringAction(WebDriver driver,WebElement ele) {
		Actions a = new Actions(driver);
		a.moveToElement(ele).perform();
		
	}
	/**
	 * This Method is Used to drag from one place to drop into another
	 * @param driver
	 * @param drag
	 * @param drop
	 */
	public void dragAndDropAction(WebDriver driver,WebElement drag,WebElement drop) {
		Actions a = new Actions(driver);
		a.dragAndDrop(drag, drop).perform();
	}
	/**
	 * This Method is Used to click and hold until release
	 * @param driver
	 */
	public void clickAndHoldAction(WebDriver driver) {
		Actions a = new Actions(driver);
		a.clickAndHold().perform();
	}
	
/**
 * This Method is used to release the cursor
 * @param driver
 */
	public void releaseAction(WebDriver driver) {
		Actions a = new Actions(driver);
		a.release().perform();
	}
	/**
	 * This method used to move the cursor based on coordinates
	 * @param driver
	 * @param x
	 * @param y
	 */
	public void moveByOffsetAction(WebDriver driver,int x,int y) {
		Actions a = new Actions(driver);
		a.moveByOffset(x,y).perform();
	}
	/**
	 * This method used to perform double click
	 * @param driver
	 */
	public void doubleClickAction(WebDriver driver) {
		Actions a = new Actions(driver);
		a.doubleClick().perform();
	}
	/**
	 * This method used to perform right click
	 * @param driver
	 */
	public void contextClickAction(WebDriver driver) {
		Actions a = new Actions(driver);
		a.contextClick().perform();
	}
	/**
	 * This method is used scroll the page to element
	 * @param driver
	 * @param ele
	 */
	public void ScrollToElementActions(WebDriver driver,WebElement ele) {
		Actions a = new Actions(driver);
		a.scrollToElement(ele).perform();
	}
	
	/**
	 * This Method is used to switch to child frame based on index
	 * @param driver
	 * @param index
	 */
	public void switchToChildFrame(WebDriver driver,int index) {
		driver.switchTo().frame(index);
	}
	/**
	 * This Method is used to switch to child frame based on name/id attribue
	 * @param driver
	 * @param name
	 */
	public void switchToChildFrame(WebDriver driver,String name) {
		driver.switchTo().frame(name);
	}
	/**
	 * This Method is used to switch to child frame based element
	 * @param driver
	 * @param ele
	 */
	public void switchToChildFrame(WebDriver driver,WebElement ele) {
		driver.switchTo().frame(ele);
	}
	/**
	 * This Method is used to switch to parent frame
	 * @param driver
	 */
	public void switchToParentFrame(WebDriver driver) {
		driver.switchTo().parentFrame();
	}
	/**
	 * This Method is used to switch to dafault frame
	 * @param driver
	 */
	public void switchToDefaultFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	/**
	 * This Method is used to accept the popup
	 * @param driver
	 */
	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}
	/**
	 * This Method is used to decline the popup
	 * @param driver
	 */
	public void dismissAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}
	/**
	 * This method is used to get text from the popup
	 * @param driver
	 * @return
	 */
	public String getTextAlert(WebDriver driver) {
		return driver.switchTo().alert().getText();
	}
	/**
	 * This Method is used to send value to alert
	 * @param driver
	 * @param value
	 */
	public void sendKeysAlert(WebDriver driver,String value) {
		driver.switchTo().alert().sendKeys(value);;
	}
	
	/**
	 * This Method is used to switch to window based on partial title
	 * @param driver
	 * @param title
	 */
	public void SwitchtoWindow(WebDriver driver,String title) {
		Set<String> ids = driver.getWindowHandles();
		for(String id : ids ) {
			driver.switchTo().window(id);
			if(driver.getTitle().contains(title)) {
				break;
			}
		}
	}
	
	/**
	 * This Method is used when there are two windows , and want to switch to other window.
	 * @param driver
	 */
	public void toWindow(WebDriver driver) {
		String mainId = driver.getWindowHandle();
		Set<String> ids = driver.getWindowHandles();
		for(String id : ids) {
			if(!mainId.equals(id)) {
				driver.switchTo().window(id);
			}
		}
	}
	/**
	 * This method will capture the screen shot and return the path to caller.
	 * @param driver
	 * @param screenShotName
	 * @return
	 * @throws IOException
	 */
	public String captureScreenShot(WebDriver driver,String screenShotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dst = new File(".\\ScreenShots\\"+screenShotName+".png");
		FileHandler.copy(src, dst);
		return dst.getAbsolutePath(); // For Extent Reports
	}
	
}
