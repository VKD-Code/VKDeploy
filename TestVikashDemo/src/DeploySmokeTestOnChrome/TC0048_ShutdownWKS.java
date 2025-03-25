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

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;


public class TC0048_ShutdownWKS extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description ="Verify wks can be shutdown using shutdown action from action toolbar.")
	public void Shutdown() throws InterruptedException {
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;

			WebElement computerselect = driver.findElement(By.xpath("//div/img[@id='appSearchImg']"));
			computerselect.click();
			Thread.sleep(5000);
			WebElement allButton = driver.findElement(By.xpath("//div/a[text()='All']"));
			allButton.click();
			Thread.sleep(Duration.ofMinutes(3));
			driver.navigate().refresh();
			
			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(5))); // Waiting for a maximum of 10 seconds
			String condition1="(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']/preceding-sibling::div[@title='Offline']";
			System.out.println(condition1);


			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition1)));

			System.out.println("Condition met");
			driver.navigate().refresh();

			Thread.sleep(5000);


			driver.findElement(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']")).click();
			Thread.sleep(4000);
			//Shutdown
			driver.findElement(By.xpath("//div[@id='dvCompActions']//input[@value='Shutdown']")).click();
			Thread.sleep(2000);

			driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
			WebDriverWait wait1 = new WebDriverWait( driver, Duration.ofMinutes((long)(15))); // Waiting for a maximum of 10 seconds
			

			wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));

			System.out.println("Condition met");


			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("Condition 1 met");
				System.out.println("computer is offline");


			} else {
				System.out.println("computer is still online");
			}

		
	}
}
