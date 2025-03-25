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



public class TC0032_TicketPagelaunch extends SetupDriver {
	@Test(description = "Make sure after clicking on Tickets button tickets page can be launched.")
	public void Ticketpagelaunch() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));

			Thread.sleep(3000);
			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[text"
					+ "()='Organization Settings']")).click();
			Thread.sleep(5000);
			driver.navigate().refresh();
			WebElement value=driver.findElement(By.xpath("//input[@id='chkEnableTicketing']"));
			String rlt=value.getAttribute("value");
			//enable
			if(rlt.equalsIgnoreCase("false")) {

				driver.findElement(By.xpath("(//div[@class='d-inline-block custom-switch checbox-switch switch-primary help-switch']/label/span)[4]")).click();
				Thread.sleep(5000);

				String title="demss";
				driver.findElement(By.xpath("//input[@id='txtTicketTitle']")).sendKeys(title);
				Thread.sleep(3000);
				driver.findElement(By.xpath("//div[@class='col-7 text-right']//button[text()='SAVE']")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//div[@class='col-md-9 text-right']//button[text()='Submit']")).click();
				System.out.println("ticketing enable");

				String condition="//li[@id='liNavTickets']";
				
				
				WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition)));
				if (driver.findElements(By.xpath(condition)).size() > 0) {
					System.out.println("Element found");
				}else {
					System.out.println("Element not found");
				}

			}else {
				System.out.println("Already enable");
			}
			driver.findElement(By.xpath("//a[@id='navTickets']")).click();
			
			
			String condition1="//div[@class='container-fluid']";
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(condition1)));
			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("Element found");
			}else {
				System.out.println("Element not found");
				softAssert.fail("Element not found.");
			}

			softAssert.assertAll();

	}

}
