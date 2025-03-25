package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.io.IOException;
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


public class TC0017_WingetEnableDisableOrg extends SetupDriver {

	@Test(description = "Verify Winget can be enable/disable from Organization settings.")
	public void wingetEDorg() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();

			Thread.sleep(3000);
			WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Organization Settings']"))).click();
           //disable winget

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//li[@class='mr-4']/div/label/span)[4]"))).click();
			
			driver.navigate().refresh();
			Thread.sleep(3000);
			WebElement check1=driver.findElement(By.xpath("//input[@id='chkEnableWinget']"));
			String result1=check1.getAttribute("value");
			System.out.println(result1);
			if(result1.equalsIgnoreCase("false")) {
				System.out.println("Disable successfull");

			}
			else {
				System.out.println("not Disabled");
			}
			
			//enable Winget
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//li[@class='mr-4']/div/label/span)[4]"))).click();
			
			//Enable Microsoft Windows Package Manager (winget) support
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='col-12 text-right']/button[@class='btn btn-primary compDetailsInputs'])[2]"))).click();
			
			driver.navigate().refresh();

			WebElement check=driver.findElement(By.xpath("//input[@id='chkEnableWinget']"));
			String result=check.getAttribute("value");
			System.out.println(result);
			if(result.equalsIgnoreCase("true")) {
				System.out.println("enable successfull");
			}
			else {
				System.out.println("not enabled");
				softAssert.fail("Unable to enable winget.");
			}
			softAssert.assertAll();
	}
}
