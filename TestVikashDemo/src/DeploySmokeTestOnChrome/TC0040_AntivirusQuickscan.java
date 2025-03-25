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

public class TC0040_AntivirusQuickscan extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description ="Make sure that Quick scan should work properly from the Antivirus page.")
	public void antivirusQuickscan() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait2=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		

			//Antivirus  page
			driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[@data-target='#dvFDAntivirus']")).click();
			
			WebElement computerselect = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			
			WebElement allButton = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			Thread.sleep(5000);
			String exp="//div[@class='dx-datagrid-content dx-datagrid-content-fixed dx-pointer-events-target']//table//tr//td//span[text()='"+Window+"']/preceding-sibling::div[@title='Offline']";
			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(exp)));


			System.out.println("computer online ");
			Thread.sleep(5000);

			driver.findElement(By.xpath("//div[@class='dx-datagrid-content dx-datagrid-content-fixed dx-pointer-events-target']//table//tr//td//span[text()='"+Window+"']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[@id='btnATAVScan']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("(//a[text()='Quick Scan'])[2]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
			Thread.sleep(5000);
			WebDriverWait wait1 = new WebDriverWait( driver, Duration.ofMinutes((long)(30)));
			WebElement index=driver.findElement(By.xpath("//td[@aria-label='Column Task Status']"));
			String colindex=index.getAttribute("aria-colindex");
			
			String exp1="//tr[td[text()='Quick Scan Initiated'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
			String exp2="//tr[td[text()='Quick Scan Failed'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
			wait1.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(exp1)),ExpectedConditions.presenceOfElementLocated(By.xpath(exp2))));
			if (driver.findElements(By.xpath(exp1)).size() > 0) {
				System.out.println("Quick scan successfully completed");
			}else if(driver.findElements(By.xpath(exp2)).size() > 0) {
				System.out.println("Quick Scan failed ");
				softAssert.fail("Quick scan failed.");
			}
			softAssert.assertAll();
			}}

