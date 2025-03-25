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


public class TC0009_CustomAppInstall extends SetupDriver{
	@Test(description = "Verify custom app can be downloaded and installed from web.")

	public void customappinstall() throws InterruptedException, AWTException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	
			Thread.sleep(5000);
			 WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']"))).click();
			
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']"))).click();
			
		
			//click custom apps
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button/i[@class='fa fa-pencil-alt faIconPosition pr-2'])[1]"))).click();
			
			
			//click  create custom apps
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create Custom App']"))).click();
			
			//Add package name 
			String packagenames="Sticky";
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='frmCustomAppDetails']//input[@id='txtPackageName']"))).sendKeys(packagenames);
			

			//Add url 
			String urls1="http://downloads.stickypassword.com/files/SP7/StickyPassword_rev821228.exe";
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='frmCustomAppDetails']//input[@id='txtLocation']"))).sendKeys(urls1);
			
			//Add Architecture

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='frmCustomAppDetails']//select[@name='Architecture']"))).click();
			
			String A1="32-bit";
			String A2="64-bit";
			String A3="32 and 64 bit";

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@id='frmCustomAppDetails']//select/option[text()='"+A3+"']"))).click();
			
			//Add install command line 
			String command="/VERYSILENT";
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtInstallCommandline']"))).sendKeys(command);
			
			//Add uninstall command line
			String command1="/SILENT";
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtUninstallCommandline']"))).sendKeys(command1);
		
			//Add restart option

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddMSIRestart']"))).click();
			
			String opt1="Always";

			String opt2="Not Set";
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddMSIRestart']//option[text()='"+opt2+"']"))).click();
			

			/*
		    //Add install timeout
		    WebElement insatlltimeout = driver.findElement(By.xpath("//select[@id='ddMSIRestart']//option[text()='"+opt1+"']"));
		    insatlltimeout.click();
		    Thread.sleep(3000);
			 */

			//Add restart option
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustAppSpecifiedUserAcc']"))).click();
			
			String Run1="System Account";

			String Run2="Specified User Account";
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustAppSpecifiedUserAcc']//option[text()='"+Run1+"']")));
			
			//Click next
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnNextSCA"))).click();
			
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='col-md-9 pt-3 popupBodyHeight']//tr/td/span[text()='"+Window+"'])[2]"))).click();

			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnNextSCA"))).click();
			
			//click install button
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnCustomAppInstall']"))).click();
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input [@id='btnConfirmCommonYes']"))).click();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(30));
			String condition1="//div[@class='col-md-9 pt-3 popupBodyHeight']//tr/td[text()='8.2']";
			String condition2="//div[@class='col-md-9 pt-3 popupBodyHeight']//tr/td[text()='Failed']";

			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))
					));

			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("App installed");
				Thread.sleep(5000);
				WebElement ok = driver.findElement(By.xpath("//button[@id='btnNextSCA']"));
				ok.click();
			} else if (driver.findElements(By.xpath(condition2)).size() > 0) {
				System.out.println("Condition 2 met");
				System.out.println("Failed");
				softAssert.fail("App is not installed or version mismatch.");

			} else {
				System.out.println("Neither condition met");
				softAssert.fail("App installation is failed due no conditions match.");
			}
			softAssert.assertAll();


	}

}
