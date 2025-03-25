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

import BaseClasses.Finalread;
import BaseClasses.SetupDriver;



public class TC0006_Installapp extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Verify supported apps can be downloaded and installed from web.")
	public void installapp() throws InterruptedException, AWTException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;


		Thread.sleep(3000);
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Applications']"))).click();
		
		WebElement gridOption = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn dropdown-toggle gridOptionsCustomBtn']")));
		gridOption.click();
		

		//off group by application
		WebElement offButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkPendingUpdateSwitch' and @checked='checked']]")));
		offButton.click();
		WebElement div = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("dvSUApplications")));
		div.click();
		
		WebElement computerselect = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
		computerselect.click();
		
		WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
		allButton.click();
		
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String application=Finalread.installapp;
		WebElement app = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@aria-label='Column "+application+"']")));

		String colIndex = app.getAttribute("aria-colindex");
		System.out.println(colIndex);
		
		WebElement app3=wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")));

		Actions actions = new Actions(driver);
		actions.moveToElement(app3).click().perform();
		
		WebElement installApp = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnInstallApplication")));
		installApp.click();
	
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
	
		WebElement app5 =wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td/div[@class='dx-datagrid-text-content']//span[text()='" + application +"']")));
		app5.click();
		
		WebElement currerntversion = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnCurrentVersion")));
		String textContent = currerntversion.getText();
		
		WebElement close2 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal right rightPopup fade show']//div/div/button[@aria-label='Close']")));
		close2.click();


		String condition1 ="//tr[td[text()='"+textContent+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
		System.out.println(condition1);
		String condition2 ="//tr[td[text()='Failed'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";

		System.out.println(condition2);

		try {
			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))
					));
			System.out.println("Condition met");


			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("Condition 1 met");
				WebElement Actual= driver.findElement(By.xpath("//tr[td[text()='"+textContent+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]"));
				String Result1=Actual.getText(); 
				System.out.println("This is actual result:"+Result1);



				// VERSION MATCH
				Thread.sleep(4000);


				if (Result1 != null && textContent != null && Result1.equals(textContent)) {
					System.out.println("version correct");

				}else {
					System.out.println("version Incorrect");
					softAssert.fail("Case failed because version is incorrect");


				}
			} else if (driver.findElements(By.xpath(condition2)).size() > 0) {
				System.out.println("Condition 2 met");

				WebElement fails= driver.findElement(By.xpath(condition2));
				fails.click(); 
				Thread.sleep(3000);

				WebElement failtext = driver.findElement(By.id("spnFailedReason"));
				String failedReason = failtext.getText();
				System.out.print(failedReason);
				Thread.sleep(3000);
				softAssert.fail("Case failed because: "+failedReason);

				// Add version match in file

				WebElement clear = driver.findElement(By.xpath("//div/input[@value='CLEAR All FAILED']"));
				clear.click();
				Thread.sleep(3000);

				driver.navigate().refresh();
				Thread.sleep(3000);


			} else {
				System.out.println("Neither condition met");
				WebElement appupdate= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]"));
				String result2=appupdate.getText();

				// VERSION MATCH
				Thread.sleep(4000);

				if (result2 != null && textContent != null && result2.equals(textContent)) {
					System.out.println("version correct");


				}else {
					System.out.println("version Incorrect");
					softAssert.fail("Case failed because because version is incorrect");

				} 

			}

		} catch (TimeoutException es) {
			System.out.println("Condition not met: Timeout occurred");
			softAssert.fail("Case failed because: Timeout occurred.");

		}
		softAssert.assertAll();


	}

}
