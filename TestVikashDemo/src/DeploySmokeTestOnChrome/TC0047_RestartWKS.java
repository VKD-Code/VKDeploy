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


public class TC0047_RestartWKS extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description ="Verify wks can be rebooted using restart action from action toolbar.")
	public void Restart () throws InterruptedException {
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
		//a[@id='navControlGrid']
	Thread.sleep(5000);
		    driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Applications']")).click();
			Thread.sleep(5000);
			WebElement computerselect = driver.findElement(By.xpath("//div/img[@id='appSearchImg']"));
			computerselect.click();
			Thread.sleep(5000);
			WebElement allButton = driver.findElement(By.xpath("//div/a[text()='All']"));
			allButton.click();
			Thread.sleep(5000);

			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10))); // Waiting for a maximum of 10 seconds
			String condition1="(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']/preceding-sibling::div[@title='Offline']";
			System.out.println(condition1);


			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition1)));

			System.out.println("Condition met");
			driver.navigate().refresh();

			Thread.sleep(5000);

			driver.findElement(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']")).click();
			Thread.sleep(4000);
			//Reboot Restart
			driver.findElement(By.xpath("//div[@id='dvCompActions']//input[@value='Restart']")).click();
			Thread.sleep(2000);

			driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
			Thread.sleep(Duration.ofMinutes(4));
		
			WebDriverWait wait = new WebDriverWait( driver, Duration.ofMinutes((long)(20)));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));
			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("computer is Offline");
				
				WebDriverWait wait2 = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

				wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition1)));
				if (driver.findElements(By.xpath(condition1)).size() == 0) {
					System.out.println("computer is  Online");
					System.out.println("computer  Restart successfully");
				}else {
					System.out.println("computer is still Offline");


				}
			}else {

				System.out.println("computer is still Online");

			}

	
	}

}
