package DeploySmokeTestOnChrome;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;

public class TC0011_Viewscreen extends SetupDriver{
	
	
		
		@Test(description="Launch View Screen > Test Functionality")
		 public void VerifyViewScreen() throws  InterruptedException 
		{
			SoftAssert softAssert=new SoftAssert();
		
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String Window=Finalread.Window1;
			Thread.sleep(5000);
			WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(5));
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
        
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnApplicationRemConnect']"))).click();
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='View Screen']"))).click();
         WebDriverWait wait=new WebDriverWait(driver,Duration.ofMinutes(5));
         String condition1="//div[text()='Starting screenshot']";
         String condition2="//div[text()='Denied by User']";
         String condition3="//div[text()='No user logged in']";
         
        wait.until(ExpectedConditions.or(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
        		(ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))),ExpectedConditions.presenceOfElementLocated(By.xpath(condition3))));
        
         if(driver.findElements(By.xpath(condition1)).size() > 0)
         {
        	 System.out.println("Condition1 met and starting view screen");
         }
         else if(driver.findElements(By.xpath(condition2)).size() > 0)
        	
        	 
         {
        	 System.out.println("Condition2 met and "+driver.findElement(By.xpath(condition2)).getText());
        	 softAssert.fail("Unable to connect due to: Denied by User.");
         }
         else if(driver.findElements(By.xpath(condition3)).size() > 0)
         {
        	 System.out.println("Condition3 met and "+driver.findElement(By.xpath(condition3)).getText());
        	 softAssert.fail("Unable to connect due to: No user logged in.");
         }
         else
         {
        	 System.out.println("Both conditions are not met and View screen is not working.");
        	 softAssert.fail("Unable to connect due to: Both conditions are not met and View screen is not working.");
        	
         
	}
	Thread.sleep(3000);
	driver.findElement(By.xpath("//i[@onclick='closeRemotePopup();']")).click();
	Thread.sleep(5000);
	driver.findElement(By.xpath("//div[@id='dvCompDetailPopup']//button[@class='close']")).click();
	softAssert.assertAll();

}
		
	}
