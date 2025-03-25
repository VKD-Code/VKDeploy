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


public class TC0016_AppPresetAssign extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Make sure apps can be installed using App preset.")
	public void Appreset() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		Thread.sleep(5000);
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		driver.findElement(By.cssSelector("a#navControlGrid")).click();
		
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']"))).click();
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']"))).click();
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
		
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='INSTALL APP PRESETS ']"))).click();
			
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[ @id='dvCompDetailPopup']//a[@class='dropdown-item open-app-presets-popup-link']"))).click();
			Thread.sleep(5000);
			//Add App preset name 
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String name =Finalread.Apppresetname;
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='AppPresetName']"))).sendKeys(name);
			Thread.sleep(3000);

			//add application
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		
			String Appname =Finalread.Apppresetappname;
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtSearchSUApplication"))).sendKeys(Appname);
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvAppPresetsPopupContent']//label[text()='"+Appname+"']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnSaveAppPresets']"))).click();
		
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvSearchAppPreset']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='"+name+"']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			
			WebElement gridOption = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn dropdown-toggle gridOptionsCustomBtn']")));
			gridOption.click();
			

			//off group by application
			WebElement offButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkPendingUpdateSwitch' and @checked='checked']]")));
			offButton.click();
			WebElement div = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("dvSUApplications")));
			div.click();
			
			WebElement computerselect1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect1.click();
			
			WebElement allButton1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton1.click();
			
			WebElement app = driver.findElement(By.xpath("//td[@aria-label='Column "+Appname+"']"));

			String colIndex = app.getAttribute("aria-colindex");
			System.out.println(colIndex);
			Thread.sleep(3000);



			WebElement app5 = driver.findElement(By.xpath("//td/div[@class='dx-datagrid-text-content']//span[text()='" + Appname +"']"));
			Actions actions = new Actions(driver);
			actions.moveToElement(app5).click().perform();
			

			Thread.sleep(4000);
			WebElement currerntversion = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnCurrentVersion")));
			String textContent = currerntversion.getText();
			Thread.sleep(3000);
			WebElement close2 = driver.findElement(By.xpath("//div[@class='modal right rightPopup fade show']//div/div/button[@aria-label='Close']"));
			close2.click();

			Thread.sleep(2000);
			WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
			String id2=app6.getAttribute("data-productid");



			String condition1 ="//tr[td[text()='"+textContent+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
			System.out.println(condition1);
			String condition2 ="//tr[td[@class='prod-"+id2+" dx-cell-focus-disabled failedString failed'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";

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
						softAssert.fail("Version incorrect");


					}
				} else if (driver.findElements(By.xpath(condition2)).size() > 0) {
					System.out.println("Condition 2 met");

					WebElement fails= driver.findElement(By.xpath(condition2));
					fails.click(); 
					Thread.sleep(3000);
					WebElement failtext = driver.findElement(By.id("spnFailedReason"));
					String failedReason = failtext.getText();
					System.out.print(failedReason);
					softAssert.fail(failedReason+": Iinstallation failed.");
					Thread.sleep(3000);

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
						softAssert.fail("Version incorrect");

					} 
				}

			} catch (TimeoutException es) {
				System.out.println("Condition not met: Timeout occurred");
				softAssert.fail("Timeout occurred.");

			}
			softAssert.assertAll();
		
	}


}
