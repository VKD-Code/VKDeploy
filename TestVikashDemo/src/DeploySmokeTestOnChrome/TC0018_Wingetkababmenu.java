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



public class TC0018_Wingetkababmenu  extends SetupDriver{
	@Test(description = "Verify after enable winget kabab menu and add winget app and manage credentials options should be available.")
	public void Wingetkababmenuvisible() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();

			Thread.sleep(3000);
			WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
			//user option
			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			
			//click organization setting
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Organization Settings']"))).click();
			Thread.sleep(4000);
			driver.navigate().refresh();

			WebElement check=driver.findElement(By.xpath("//input[@id='chkEnableWinget']"));
			String result=check.getAttribute("value");
			System.out.println(result);

			if(result.equalsIgnoreCase("true")) {
				System.out.println(" Already enable");
			}
			else {
				System.out.println("not enabled");
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//li[@class='mr-4']/div/label/span)[4]"))).click();
				Thread.sleep(5000);

				//Enable Microsoft Windows Package Manager (winget) support
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='col-12 text-right']/button[@class='btn btn-primary compDetailsInputs'])[2]"))).click();
				Thread.sleep(3000);	
				driver.navigate().refresh();

				WebElement check1=driver.findElement(By.xpath("//input[@id='chkEnableWinget']"));
				String result1=check1.getAttribute("value");
				System.out.println(result1);

				if(result.equalsIgnoreCase("true")) {
					System.out.println("enable  successfull");
				}else{
					System.out.println("enable  successfull");
				}}
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
			
			try {
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnApplicationsActionDropdown']"))).click();
				System.out.println("kabab menu visible ");


				//Add winget option 
				WebElement add=driver.findElement(By.xpath("//a[@id='aAddWingetApps']"));
				String text=add.getText();
				System.out.println(text);

				WebElement add1=driver.findElement(By.xpath("//a[@onclick='showViewManageCredentialsPopup();']"));
				String text1=add1.getText();
				System.out.println(text1);

			}catch(Exception es) {
				System.out.println("kabab menu not visible ");
				softAssert.fail("kabab menu not visible.");
			}
             softAssert.assertAll();	
	}

}
