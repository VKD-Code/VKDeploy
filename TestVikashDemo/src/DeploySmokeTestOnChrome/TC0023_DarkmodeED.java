package DeploySmokeTestOnChrome;

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


public class TC0023_DarkmodeED extends SetupDriver{

	@Test(description = " Make sure that user should be able to enable/disable dark mode.")

	public void darkmodeED() throws InterruptedException {

		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='User Profile']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='chkDarkModeEdit']/ancestor::label"))).click();
			
			WebElement value1=driver.findElement(By.xpath("//label//input[@id='chkDarkModeEdit']"));
			String Value=value1.getAttribute("value");
			if(Value.equalsIgnoreCase("true")) {
				System.out.println("dark mode Enable");
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='chkDarkModeEdit']/ancestor::label"))).click();
				
			
				WebElement value2=driver.findElement(By.xpath("//label//input[@id='chkDarkModeEdit']"));
				String Value1=value2.getAttribute("value");
				if(Value1.equalsIgnoreCase("false")) {
					System.out.println("dark mode Disable");
				}else {
					System.out.println("dark mode not Disable");
					softAssert.fail("Dark mode is not Disable.");
				}
				
				
				
			}else {
				System.out.println("dark mode not Enable");
				softAssert.fail("Dark mode is not enabled.");
			}
			Thread.sleep(3000);
			driver.findElement(By.xpath("//a[@id='navControlGrid']")).click();
			softAssert.assertAll();

}
	}
