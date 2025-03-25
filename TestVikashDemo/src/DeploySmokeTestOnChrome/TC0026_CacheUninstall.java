package DeploySmokeTestOnChrome;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;


public class TC0026_CacheUninstall extends SetupDriver{
	private static WebDriverWait wait ;
	@Test(description = "Make sure Cache Server can be uninstalled on wks using Set as Cache Server action.")
	public void cacheuninstall() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;

		
			Thread.sleep(5000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
//			WebElement computerselect = driver.findElement(By.xpath("//div/img[@id='appSearchImg']"));
//			computerselect.click();
//			Thread.sleep(5000);
//			WebElement allButton = driver.findElement(By.xpath("//div/a[text()='All']"));
//			allButton.click();
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			Thread.sleep(5000);

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@for='chkSetAsCacheServer']"))).click();
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnYesToContinute']"))).click();
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navAnalytics']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Deploy Diagnostics']"))).click();


			WebElement cache = driver.findElement(By.xpath("//td[@aria-label='Column Cache Server']"));

			String colIndex = cache.getAttribute("aria-colindex");
			System.out.println(colIndex);
			Thread.sleep(3000);

			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String textContent=Finalread.cacheversion;

			String condition1 ="//tr[td[text()='"+textContent+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
			System.out.println(condition1);


			try {
				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition1)));
				System.out.println("Condition met");


				if (driver.findElements(By.xpath(condition1)).size() == 0) {
					System.out.println("Condition 1 met");
					System.out.println("cache uninstall successfull");


				}  else {
					System.out.println("Neither condition met");
					System.out.println("cache not uninstall ");
					softAssert.fail("Cache is not uinstalled.");
				}

			} catch (TimeoutException es) {
				System.out.println("Condition not met: Timeout occurred");
				softAssert.fail("Timeout occurred");

			}
			softAssert.assertAll();

		
	}

}
