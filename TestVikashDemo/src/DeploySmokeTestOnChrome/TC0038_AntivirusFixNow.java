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


public class TC0038_AntivirusFixNow extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description ="make sure antivirus get protected after click fix now.")
	public void antivirusFixNow() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
			Thread.sleep(3000);

			//Antivirus  page
			driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[@data-target='#dvFDAntivirus']")).click();
			
			WebElement computerselect = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			
			WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			Thread.sleep(5000);
			String exp="//div[@class='dx-datagrid-content dx-datagrid-content-fixed dx-pointer-events-target']//table//tr//td//span[text()='"+Window+"']/preceding-sibling::div[@title='Offline']";
			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(exp)));


			System.out.println("computer online ");
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-datagrid-content dx-datagrid-content-fixed dx-pointer-events-target']//table//tr//td//span[text()='"+Window+"']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnATFixNow']"))).click();
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			Thread.sleep(5000);

			WebElement index=driver.findElement(By.xpath("//td[@aria-label='Column Protection Status']"));
			String colindex=index.getAttribute("aria-colindex");


			String exp1="//tr[td[text()='Protected'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";


			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(60)));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(exp1)));
			if(driver.findElements(By.xpath(exp1)).size() > 0) {
				System.out.println("Protected");
			}
			else {

				System.out.println("fail Protected");
				softAssert.fail("Failed fix now.");
			}


		softAssert.assertAll();

	}
}
