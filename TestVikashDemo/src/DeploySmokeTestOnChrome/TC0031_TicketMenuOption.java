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



public class TC0031_TicketMenuOption extends SetupDriver {
	@Test(description = "Verify after enable Ticketing Ticket menu options should be available on top next to site name.")
	public void Ticketmenuopt() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));

			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Organization Settings']"))).click();
			Thread.sleep(5000);
			driver.navigate().refresh();
			WebElement value=driver.findElement(By.xpath("//input[@id='chkEnableTicketing']"));
			String rlt=value.getAttribute("value");
			//enable
			if(rlt.equalsIgnoreCase("false")) {

				driver.findElement(By.xpath("(//div[@class='d-inline-block custom-switch checbox-switch switch-primary help-switch']/label/span)[4]")).click();
				

				String title="demo";
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtTicketTitle']"))).sendKeys(title);
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-7 text-right']//button[text()='SAVE']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-md-9 text-right']//button[text()='Submit']"))).click();
				System.out.println("ticketing enable");

				String condition="//li[@id='liNavTickets']";
				WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition)));
				if (driver.findElements(By.xpath(condition)).size() > 0) {
					System.out.println("Element found");
				}else {
					System.out.println("Element not found");
					softAssert.fail("Element Is not found.");
				}

			}else {
				System.out.println("Already enable");
			}
			softAssert.assertAll();
	}
}
