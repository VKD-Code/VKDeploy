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


public class TC0034_TicketClose extends SetupDriver{
	@Test(description ="Make sure Ticket can be closed from console and close confirmation email should be sent successfully with note if \"\"Send email to..\"\" option is checked while closing.")

	public void Ticketclose() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
	
		
			Thread.sleep(3000);
			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[text()='Organization Settings']")).click();
			Thread.sleep(5000);
			driver.navigate().refresh();
			WebElement value=driver.findElement(By.xpath("//input[@id='chkEnableTicketing']"));
			String rlt=value.getAttribute("value");
			//enable
			if(rlt.equalsIgnoreCase("false")) {

				driver.findElement(By.xpath("(//div[@class='d-inline-block custom-switch checbox-switch switch-primary help-switch']/label/span)[4]")).click();
				Thread.sleep(5000);

				try {
					Finalread.readStringFromExcel();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				String title=Finalread.Ticketname;
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
			Thread.sleep(5000);
			//new ticket
			driver.findElement(By.xpath("//button[text()='New Ticket']")).click();
			Thread.sleep(5000);
			//select computer
			driver.findElement(By.xpath("//div[@id='ddlComputersSelectBox']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[text()='"+Window+"']")).click();
			Thread.sleep(3000);
			String description="vfbdsfcghv xbasjhvbdhj.";
			driver.findElement(By.xpath("//textarea[@name='Description']")).sendKeys(description);
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@class='col-12 text-right pl-3 pr-3']//button[text()='SAVE']")).click();

			Thread.sleep(5000);
			driver.findElement(By.xpath("//tr[td//span[text()='"+Window+"']]//td[5]")).click();
			Thread.sleep(3000);

			//Ticket action
			driver.findElement(By.xpath("(//button[@class='btn btnWhite customAppButton '])[1]")).click();
			Thread.sleep(3000);

			//closed ticket
			driver.findElement(By.xpath("//a[@id='aCloseTicket']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//label[@for='chkSendEmailToWhoSubmitedThisTicket']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[@id='btnCloseTicketDone']")).click();

			String condition="//tr[td//span[text()='"+Window+"']]//td[5][text()='Closed']";
			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(120));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition)));
			if (driver.findElements(By.xpath(condition)).size() > 0) {
				System.out.println("Ticket closed");
			}else {
				System.out.println("Ticket not closed");
				softAssert.fail("Ticket is not closed.");
			}
			softAssert.assertAll();
	}


}
