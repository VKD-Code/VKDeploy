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


public class TC0021_WingetAppInstall extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Verify Winget app can be installed on Win10 and above wks successfully.")
	public void Wingetappinstall() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;

//			Thread.sleep(5000);
//			WebElement computerselect = driver.findElement(By.xpath("//div/img[@id='appSearchImg']"));
//			computerselect.click();
//			Thread.sleep(5000);
//			WebElement allButton = driver.findElement(By.xpath("//div/a[text()='All']"));
//			allButton.click();
//			Thread.sleep(5000);
	//	driver.findElement(By.xpath("//td//div[@class='dx-datagrid-text-content']//span[text()='Winget Apps']")).click();
		

			//install winget app
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
		Thread.sleep(5000);
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			String App=Finalread. Wingetappname;
			
			WebElement index=driver.findElement(By.xpath("//div[@id='dvPendingApplicationsGrid']//tr/td[@aria-label='Column "+App+"']"));
			String colindex=index.getAttribute("aria-colindex");
			System.out.println(colindex);
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]"))).click();
		
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnInstallApplication']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='liTaskHistory']"))).click();
			
			WebElement index1=driver.findElement(By.xpath("//td[@aria-label='Column Status']"));
			String statusid=index1.getAttribute("aria-colindex"); 
			
		String exp1="//tr[1][td/span[text()='Completed']and td/span[text()='"+Window+"']]//td["+statusid+"]";
		String exp2="//tr[1][td/span[@style='color:red;']and td/span[text()='"+Window+"']]//td["+statusid+"]";
		
		wait = new WebDriverWait( driver, Duration.ofMinutes((long)(40))); // Waiting for a maximum of 10 seconds

	
		wait.until(ExpectedConditions.or(
				ExpectedConditions.presenceOfElementLocated(By.xpath(exp1)),
				ExpectedConditions.presenceOfElementLocated(By.xpath(exp2))
				));
		if (driver.findElements(By.xpath(exp1)).size() > 0) {
			System.out.print("task completed");
			driver.findElement(By.xpath("//a[@id='navControlGrid']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//td//div[@class='dx-datagrid-text-content']//span[text()='Winget Apps']")).click();
			Thread.sleep(5000);
			WebElement content=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]"));
			String version=content.getText();
			System.out.println(version);

			Thread.sleep(3000);
			
		}else if(driver.findElements(By.xpath(exp2)).size() > 0){
			System.out.print("task Failed");
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td//div[@class='dx-datagrid-text-content']//span[text()='Winget Apps']"))).click();
			Thread.sleep(5000);
			WebElement content=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]"));
			String version=content.getText();
			System.out.println(version);
			softAssert.fail("Task Failed");
		}
		softAssert.assertAll();
	
	}


}
