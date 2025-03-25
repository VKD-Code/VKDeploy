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


public class TC0022_WigetAppUninstall extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Verify Winget app can be uninstalled on Win10 and above wks successfully.")
	public void Wingetappinstall() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	
			
//			WebElement computerselect = driver.findElement(By.xpath("//div/img[@id='appSearchImg']"));
//			computerselect.click();
//			Thread.sleep(5000);
//			WebElement allButton = driver.findElement(By.xpath("//div/a[text()='All']"));
//			allButton.click();
		//Thread.sleep(5000);
			//driver.findElement(By.xpath("//td//div[@class='dx-datagrid-text-content']//span[text()='Winget Apps']")).click();
			//Thread.sleep(5000);
			//uninstall winget app
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
			Thread.sleep(5000);
			int time = 0;
			try {
			WebElement btn = driver.findElement(By.xpath("//td//div[@class='dx-datagrid-text-content']//span[text()='Winget Apps']"));
				if(btn.isDisplayed()) {
					btn.click();
					time = 5000;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(time);
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			String App=Finalread. Wingetappname;
			WebElement index=driver.findElement(By.xpath("//div[@id='dvPendingApplicationsGrid']//tr/td[@aria-label='Column "+App+"']"));
			String colindex=index.getAttribute("aria-colindex");
			System.out.println(colindex);
			Thread.sleep(5000);
	

			WebElement content=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]"));
			String version=content.getText();
			System.out.println(version);

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]"))).click();
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnUninstallApplication']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			
			WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]")); 
			String id2=app6.getAttribute("data-productid");
			//wait = new WebDriverWait( driver, Duration.ofMinutes((long)(40))); // Waiting for a maximum of 10 seconds


				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(20))); // Waiting for a maximum of 10 seconds
				String condition1=  "//tr[td[@class='prod-"+id2+"'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
				System.out.println(condition1);
				String condition2=  "//tr[td[text()='Failed'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
				System.out.println(condition2);


				wait.until(ExpectedConditions.or(
						ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition1)),
						ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))
						));


			if (driver.findElements(By.xpath(condition1)).size() == 0) {
				System.out.println("Condition 1 met");
				System.out.println("winget app Uninstall");

			}else if (driver.findElements(By.xpath(condition2)).size() > 0) {
				System.out.println("Condition 2 met");
				System.out.println("failed");
				softAssert.fail("Winget app uninstallation is failed.");
				WebElement failtext = driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]"));
				String failedReason = failtext.getText();
				System.out.print(failedReason);
				Thread.sleep(3000);
				// Add version match in file

			}else {
				System.out.println("Both condition not met");
				softAssert.fail("Both condition not met.");
			}
		softAssert.assertAll();
		}

		
}
