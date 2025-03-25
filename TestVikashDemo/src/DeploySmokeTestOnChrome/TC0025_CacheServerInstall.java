package DeploySmokeTestOnChrome;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;


public class TC0025_CacheServerInstall extends SetupDriver{
	private static WebDriverWait wait ;

	@Test(description = "Make sure Cache Server can be installed on wks using Set as Cache Server action.")
	public void cacheinstall() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	

			Thread.sleep(5000);
			WebElement computerselect = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			
			WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			Thread.sleep(5000);
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@for='chkSetAsCacheServer']"))).click();
			Thread.sleep(3000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnYesToContinute']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navAnalytics']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Deploy Diagnostics']"))).click();
			Thread.sleep(5000);

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
				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(20)));

				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));
				System.out.println("Condition met");


				if (driver.findElements(By.xpath(condition1)).size() > 0) {
					System.out.println("Condition 1 met");
					Actions ac=new Actions(driver);
					WebElement Actual= driver.findElement(By.xpath("//tr[td[text()='"+textContent+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]"));
					ac.moveToElement(Actual).build().perform();
					
					String Result1=Actual.getText(); 
					System.out.println("This is actual result:"+Result1);



					// VERSION MATCH
					Thread.sleep(4000);


					if (Result1 != null && textContent != null && Result1.equals(textContent)) {
						System.out.println("version correct");

					}else {
						System.out.println("version Incorrect");
						softAssert.fail("Version incorrect"+ Result1);
						
					}
				}  else {
					System.out.println("Neither condition met");
					softAssert.fail("Neither condition met");
				}

			} catch (TimeoutException es) {
				System.out.println("Condition not met: Timeout occurred");
				softAssert.fail("Timeout occurred.");

			}
			softAssert.assertAll();
		
	}

}
