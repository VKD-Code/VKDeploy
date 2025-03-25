package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;


public class TC0042_AntiviruspageDisable extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = " Make sure that the user is not able to access the antivirus page after clicking the 'Hide Antivirus' button from the organization page.")
	public void antiviruspageDisable() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		
			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Organization Settings']"))).click();
			Thread.sleep(5000);
			
			WebElement hideanti=driver.findElement(By.xpath("//input[@id='chkHideAntiVirus']/ancestor::label"));
			Actions action =new Actions(driver);
			Thread.sleep(3000);
			action.moveToElement(hideanti).click().perform();
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
			Thread.sleep(5000);
			String exp="//a[@data-target='#dvFDAntivirus']";
			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(exp)));
			if (driver.findElements(By.xpath(exp)).size() == 0) {
				System.out.println("Antivirus page disable");
			}else {
				System.out.println("Antivirus page Still visible");
				softAssert.fail("Antivirus page still visible.");
			}
			softAssert.assertAll();
}}
