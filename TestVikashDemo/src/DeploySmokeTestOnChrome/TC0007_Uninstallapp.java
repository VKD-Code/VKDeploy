package DeploySmokeTestOnChrome;

import java.awt.AWTException;
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


public class TC0007_Uninstallapp  extends SetupDriver{
	private static WebDriverWait wait ;
	@Test(description = "Verify supported apps can be uninstalled using uninstall button.")
	public void uninstallapp() throws InterruptedException, AWTException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;

	
		
			
			Thread.sleep(5000);
			WebDriverWait wait2=new WebDriverWait(driver, Duration.ofSeconds(60));
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String application=Finalread.Uninstallapp;
			WebElement app = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@aria-label='Column "+application+"']")));

			String colIndex = app.getAttribute("aria-colindex");
			System.out.println(colIndex);
			
			WebElement app3= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")));

			Actions actions = new Actions(driver);
			actions.moveToElement(app3).click().perform();
			
			WebElement UNinstallApp = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnUninstallApplication")));
			UNinstallApp.click();
			

			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			
			WebElement app5 = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td/div[@class='dx-datagrid-text-content']//span[text()='"+application+"'])[1]")));
			Actions action1=new Actions(driver);
			action1.moveToElement(app5).click().perform();
			

			WebElement close2 = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal right rightPopup fade show']//div/div/button[@aria-label='Close']")));
			close2.click();

			
			WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
			String id2=app6.getAttribute("data-productid");



			try {

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15))); // Waiting for a maximum of 10 seconds
				String condition1=  "//tr[td[@class='prod-"+id2+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
				System.out.println(condition1);
				String condition2=  "//tr[td[text()='Failed'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
				System.out.println(condition2);


				wait.until(ExpectedConditions.or(
						ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
						ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))
						));

				System.out.println("Condition met");


				if (driver.findElements(By.xpath(condition1)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("App Uninstall");


				} else if (driver.findElements(By.xpath(condition2)).size() > 0) {
					System.out.println("Condition 2 met");

					WebElement fails= driver.findElement(By.xpath(condition2));
					fails.click(); 
					Thread.sleep(3000);
					WebElement failtext = driver.findElement(By.id("spnFailedReason"));
					String failedReason = failtext.getText();
					System.out.print(failedReason);
					Thread.sleep(3000);
					softAssert.fail("Case failed: "+failedReason);

					// Add version match in file

					WebElement clear = driver.findElement(By.xpath("//div/input[@value='CLEAR All FAILED']"));
					clear.click();
					Thread.sleep(3000);

					driver.navigate().refresh();
					Thread.sleep(3000);


				} else {
					System.out.println("Neither condition met");
					softAssert.fail("Case failed given conditions are not met.");

				}

			} catch (TimeoutException es) {
				System.out.println("Condition not met: Timeout occurred");
				softAssert.fail("Case failed Timeout occurred");

			}
			softAssert.assertAll();


	}
}
