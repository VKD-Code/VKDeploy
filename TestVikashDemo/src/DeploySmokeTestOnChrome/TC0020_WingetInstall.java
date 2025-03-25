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


public class TC0020_WingetInstall extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Verify Winget can be installed on Win10 and above wks successfully.")
	public void Wingetinstall() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;

		//Thread.sleep(5000);
		//			WebElement computerselect = driver.findElement(By.xpath("//div/img[@id='appSearchImg']"));
		//			computerselect.click();
		//			Thread.sleep(5000);
		//			WebElement allButton = driver.findElement(By.xpath("//div/a[text()='All']"));
		//			allButton.click();
		//			Thread.sleep(5000);
		//			driver.findElement(By.xpath("//td//div[@class='dx-datagrid-text-content']//span[text()='Winget Apps']")).click();
		//			Thread.sleep(5000);

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
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		WebElement index=driver.findElement(By.xpath("//div[@id='dvPendingApplicationsGrid']//tr/td[@aria-label='Column Winget Status']"));
		String colindex=index.getAttribute("aria-colindex");
		System.out.println(colindex);
		WebElement index1=driver.findElement(By.xpath("(//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"])[1]"));
		String text=index1.getText();
		System.out.println(text);
		
		if(text.equals("v1.9")) {
			System.out.println("Winget is already installed.");
		}
		else {

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnInstallWinget']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtWAInstallDisplayName']"))).sendKeys(Window);
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtWAInstallUserName']"))).sendKeys("Administrator");
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtWAInstallPassword']"))).sendKeys("aloha");
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='installWingetPopupBtn']"))).click();
		wait = new WebDriverWait( driver, Duration.ofMinutes((long)(40))); // Waiting for a maximum of 10 seconds
		String condition1=  "//tr[td[text()='v1.9'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
		System.out.println(condition1);
		String condition2=  "//tr[td[text()='Failed'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
		System.out.println(condition2);

		wait.until(ExpectedConditions.or(
				ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
				ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))
				));

		if (driver.findElements(By.xpath(condition1)).size() > 0) {
			System.out.println("Condition 1 met");
			System.out.println("winget  Install");
		}else if (driver.findElements(By.xpath(condition2)).size() > 0) {
			System.out.println("Condition 2 met");
			System.out.println("winget failed to install");
			softAssert.fail("winget failed to install.");
			

			//	driver.navigate().refresh();

		}
		softAssert.assertAll();
	}
	}
}
