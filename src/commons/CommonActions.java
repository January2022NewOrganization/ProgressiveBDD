package commons;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.io.Files;

import base.BaseClass;
import reporting.Loggers;

/*
 * Purpose of common actions class is to reuse all events (Click, SendKeys, GetText) again for all test steps
 */

public class CommonActions {

	WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(10));
	JavascriptExecutor jsExecutor = (JavascriptExecutor)BaseClass.driver;
	
	public void click(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			//Reporter.log("Element is Clicking : " + element );
			Loggers.log("Element is Cliking : " + element);
		} catch (TimeoutException e) {
			e.printStackTrace();
			//Reporter.log("Element is Clicking : " + element );
			Loggers.log("Element is unable to click: "+ element+"\n" + e.getMessage() );
			Assert.fail();
		}
	}
	
	public void inputText(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(text);
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log(text +" : value passed to element : " + element );
		} catch (TimeoutException e) {
			e.printStackTrace();
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log("Element is not found : " + element+"\n" + e.getMessage() );
			Assert.fail();
		}
	}
	
	public void inputText(WebElement element, char text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(String.valueOf(text));
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log(text +" : value passed to element : " + element );
		} catch (TimeoutException e) {
			e.printStackTrace();
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log("Element is not found : " + element+"\n" + e.getMessage() );
			Assert.fail();
		}
	}
	
	public String getUrl() {
		String currentUrl = BaseClass.driver.getCurrentUrl();
		//Reporter.log("Element is Cliking : " + element );
		Loggers.log("Current URL is : " + currentUrl );
		return currentUrl;
	}
	
	public void selectByValue(WebElement element, String value) {
		try {
			sleep(0.5);
			Select select = new Select(element);
			select.selectByValue(value);
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log(value +" : value has been selected from the element : " + element );
		}catch (NoSuchElementException e) {
			e.printStackTrace();
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log("Locator doesn't match for : " + element+"\n" + e.getMessage() );
			Assert.fail();
		}
	}
	
	public boolean isSelected(WebElement element) {
		boolean status = false;
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			status = element.isSelected();
			if(status) {
				//Reporter.log("Element is Cliking : " + element );
				Loggers.log(element +" : is selected" );
			}else {
				//Reporter.log("Element is Cliking : " + element );
				Loggers.log(element +" : is Not selected" );
			}
		}catch (TimeoutException e) {
			e.printStackTrace();
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log("Element Not Found : " + element+"\n" + e.getMessage() );
			Assert.fail();
		}
		return status;
	}
	
	public void sleep(double seconds) {
		try {
			Thread.sleep((long) (seconds * 1000));
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log("Sleeping for : " + seconds + " seconds zZzz.." );
		}catch (InterruptedException e) {
			e.printStackTrace();
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log("Sleeping interuppted" );
		}
	}
	
	public void inputUsingJSXforIdLocator(String element, String text) {
		try {
			jsExecutor.executeScript("document.getElementById('"+ element +"').value='"+text+"';");
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log(text +" : value passed to element : " + element );
		} catch (Throwable e) {
			e.printStackTrace();
			//Reporter.log("Element is Cliking : " + element );
			Loggers.log("Element is not found : " + element+"\n" + e.getMessage() );
			Assert.fail();
		}
	}
	
	public boolean isPresent(By by) {
		boolean status = false;
		try {
			List<WebElement> elements = BaseClass.driver.findElements(by);
			if(elements.size() > 0) {
				status = true;
				//Reporter.log("Element is Cliking : " + element );
				Loggers.log(by + " : Element is Present" );
			}else {
				//Reporter.log("Element is Cliking : " + element );
				Loggers.log(by + " : Element is Not Present" );
			}
		} catch (Throwable e) {
			e.printStackTrace();
			//Reporter.log("Element is not found : " + by+"\n" + e.getMessage() );
			Loggers.log("Element is not found : " + by+"\n" + e.getMessage() );
		}
		return status;
	}
	
	public String getText(WebElement element) {
		String textString = element.getText();
		Loggers.log(element+" has text : " +textString );
		return textString;
	}
	
	public void textVerification(String actual, String expected) {
		Loggers.log("Actual : "+actual+"<<<>>>"+"Expeted : " + expected);
		Assert.assertEquals(actual, expected);
	}
	
	public void logEvent(String eventMsg) {
		//Reporter.log(eventMsg );
		Loggers.log(eventMsg);
	}
	
	public void logEventAndFail(String eventMsg) {
		//Reporter.log(eventMsg );
		Loggers.log(eventMsg);
		Assert.fail();
	}
	
	public String getScreenShot() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy_hh.mm.ss");
		String suffix = dateFormat.format(date);
		File file = new File("screenShots/error_"+suffix+".png");
		String fileLocation = file.getAbsolutePath();
		TakesScreenshot ss = ((TakesScreenshot)BaseClass.driver);
		File srcFile = ss.getScreenshotAs(OutputType.FILE);
		try {
		Files.copy(srcFile, file.getAbsoluteFile());
			Loggers.log("Test Failed & Sceenshot taken in location : "+ fileLocation );
		}catch (IOException e) {
			Loggers.log("Error while taking screen shot");
		}
		return fileLocation;
	}
}
