package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;



public class TC0010_UninstallCustomApp extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Verify custom apps can be uninstalled using uninstall button.")
	public void Uninstallcustomapp() throws InterruptedException, AWTException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		Thread.sleep(5000);
		driver.navigate().refresh();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']"))).click();
		
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']"))).click();
		
		String application="Sticky";
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td[@aria-label='Column Custom Apps'])[1]"))).click();
		

		Thread.sleep(3000);
		WebElement app2 = driver.findElement(By.xpath("(//td[@aria-label='Column "+application+"'])[1]"));
		String colIndex = app2.getAttribute("aria-colindex");
		System.out.println(colIndex);
		Thread.sleep(3000);

		WebElement app3= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]"));
		Actions actions = new Actions(driver);
		actions.moveToElement(app3).click().perform();
		
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnUninstallApplication"))).click();
		
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
		Thread.sleep(3000);

		WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
		String id2=app6.getAttribute("data-productid");

		try {

			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15))); // Waiting for a maximum of 10 seconds
			String condition1="//tr[td[@class='prod-"+id2+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
			System.out.println(condition1);
			String condition2= "//tr[td[@class='prod-"+id2+" textColorBlack dx-cell-focus-disabled failedString failed'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
			System.out.println(condition2);


			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))
					));

			System.out.println("Condition met");


			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("Condition 1 met");
				System.out.println("CustomApp Uninstall");


			} else if (driver.findElements(By.xpath(condition2)).size() > 0) {
				System.out.println("Condition 2 met");
				System.out.println("CustomApp fail to Uninstall");
				
				WebElement fails= driver.findElement(By.xpath(condition2));
				fails.click(); 
				Thread.sleep(3000);

				WebElement failtext = driver.findElement(By.id("spnFailedReason"));
				String failedReason = failtext.getText();
				System.out.print(failedReason);
				softAssert.fail("Custom app uninstallation is failed: "+failedReason);
				Thread.sleep(3000);

				// Add version match in file

				WebElement clear = driver.findElement(By.xpath("//div/input[@value='CLEAR All FAILED']"));
				clear.click();
				Thread.sleep(3000);

				driver.navigate().refresh();
				Thread.sleep(3000);


			} else {
				System.out.println("Neither condition met");
				softAssert.fail("No conditions are matched.");

			}

		} catch (TimeoutException es) {
			System.out.println("Condition not met: Timeout occurred");
			softAssert.fail("Custom app uninstallation failed: Timeout occurred");

		}
		softAssert.assertAll();

	}
}
