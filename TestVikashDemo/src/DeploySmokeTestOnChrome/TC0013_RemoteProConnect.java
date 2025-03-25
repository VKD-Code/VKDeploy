package DeploySmokeTestOnChrome;


import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;


public class TC0013_RemoteProConnect extends SetupDriver{
	@Test(description = "Make sure Remote pro get connected.")
	public void RemoteConnect() throws InterruptedException 
	{
		SoftAssert softAssert=new SoftAssert();
		Thread.sleep(5000);
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvFDMainMenuParent']//a[text()='Applications']"))).click();
		
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']"))).click();
		
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']"))).click();
		
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
		
		//Remote dropdown
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnApplicationRemConnect']"))).click();
	
		//Remote pro
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Remote Pro ']"))).click();

		try {
			WebDriverWait rwait=new WebDriverWait(driver, Duration.ofMinutes(3));
			rwait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='connectingRCP textColorWhite d-none']")));

			if(!driver.findElement(By.xpath("//div[@class='connectingRCP textColorWhite d-none']")).isDisplayed())
			{
				System.out.println("Remote pro is connected");
			}


		}
		catch(Exception e1) {
			System.out.println("Waited for 3 minutes but Remote Pro still not connected.");
			softAssert.fail("Wait for 3 minute but remote pro still not connected.");
		}
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@class='closeRemoteView']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//span[@id='dvCompDetailLabel']/following::button/span)[1]")).click();
		softAssert.assertAll();

	}

}
