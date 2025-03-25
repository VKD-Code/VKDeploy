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
import BaseClasses.Finalread;

public class TC0024_LatManStandingEnableDisable extends SetupDriver {
	
	private static WebDriverWait wait ;
	@Test(description = "Verify LMS can be Enable And Disable on wks successfully.")
	public  void EDLastmanstanding() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
			WebElement computerselect = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			
			WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			Thread.sleep(5000);
			WebElement cmpid=driver.findElement(By.xpath("(//td[span[text()='"+Window+"']]//div)[2]"));//computer id 
			String computerid=cmpid.getAttribute("id");
			System.out.println(computerid);
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			Thread.sleep(5000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@for='chkSetAsLMS']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvCompDetailPopup']//button[@class='close']"))).click();

			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(5))); // Waiting for a maximum of 5 minutes
			String condition1="(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//div[@id='dvLastManStanding"+computerid+"']//div[text()='L']";
			System.out.println(condition1);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));

			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("last man standing enable ");
			}
			else {
				System.out.println("last man standing not  enable ");
				softAssert.fail("last man standing not  enable.");
			}
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@for='chkSetAsLMS']"))).click();
			Thread.sleep(5000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnDIsableLMSYes']"))).click();
			Thread.sleep(5000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvCompDetailPopup']//button[@class='close']"))).click();
			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(5))); // Waiting for a maximum of 5 minutes
			String condition2="(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//div[@id='dvLastManStanding"+computerid+"']//div[text()='L']";
			System.out.println(condition2);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition2)));

			if (driver.findElements(By.xpath(condition2)).size() == 0) {

				System.out.println("last man standing disable");
			}
			else {
				System.out.println("last man standing not disable");
				softAssert.fail("last man standing not disable.");
			}


			softAssert.assertAll();
	}
}
