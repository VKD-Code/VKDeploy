package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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


public class TC0019_WingetVerifyApp  extends SetupDriver{

	@Test(description = "Add few winget apps and verify apps are displayed under Winget group on Applications page.")

	public void Wingetverifyapp() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
	
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
			Thread.sleep(5000);
			//user option
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/i[@class='fa fa-user-circle']"))).click();
			
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
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvMainMenuContainer']//a[@id='navControlGrid']"))).click();
			
			//Kabab menu
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnApplicationsActionDropdown']"))).click();
			
			//Add winget option 
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aAddWingetApps']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='NEXT'])[2]"))).click();
			Thread.sleep(60000);

			//name app 
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			String App=Finalread. Wingetappname;

			WebElement app1=driver.findElement(By.xpath("//tr/td[text()='"+App+"']"));
			Actions action= new Actions(driver);
			action.moveToElement(app1).click().build().perform();

			
			//save to grid
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnSaveWingetAppToGrid']"))).click();
			
			//close button
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='CLOSE']"))).click();
			
		

			//off group by application
			WebElement gridOption = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn dropdown-toggle gridOptionsCustomBtn']")));
			gridOption.click();
			WebElement offButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkPendingUpdateSwitch' and @checked='checked']]")));
			offButton.click();
		
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
				WebElement check1=driver.findElement(By.xpath("//td//div[@class='dx-datagrid-text-content']//span[@title='"+App+"']//span[1]"));
				String result1=check1.getText();
				System.out.println(result1);
				System.out.println("app  found");
			}catch(Exception es1){
				System.out.println("app not found");
				softAssert.fail("App not found.");

			}
			softAssert.assertAll();
	
	}
}
