package DeploySmokeTestOnChrome;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
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


public class TC0029_SiteAED  extends SetupDriver{
	@Test(description = "Make sure site can be created, edited and deleted successfully.")

	public void siteAED() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		Thread.sleep(5000);

	
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='dropdownMenuSites']"))).click();
		//ADD site*** 
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String sitename=Finalread.Addsite;
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtSiteName']"))).sendKeys(sitename);
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='spnAddSite']"))).click();
			try {

				System.out.println("Site created successfully");
				driver.navigate().refresh();

				//Edit site***
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='dropdownMenuSites']"))).click();
				Thread.sleep(3000);
				
				

				Actions actions = new Actions(driver);
				Thread.sleep(3000);
				WebElement site =driver.findElement(By.xpath("//li[@data-site-name='"+sitename+"']"));

				actions.moveToElement(site).build().perform();
				Thread.sleep(3000);

			WebElement rename=	driver.findElement(By.xpath("//li[@data-site-name='"+sitename+"']/i[@title='Rename site']"));
			actions.moveToElement(rename).click().build().perform();
				Thread.sleep(3000);
				String renamesite="NewSite";
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtNewSiteName']"))).sendKeys(renamesite);
				Thread.sleep(3000);
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='DONE']"))).click();
				Thread.sleep(3000);
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='dropdownMenuSites']"))).click();
			Thread.sleep(3000);
				WebElement site2=driver.findElement(By.xpath("//li[@data-site-name='"+renamesite+"']//a"));
				String name =site2.getText();

				if(name.equals(renamesite)) {
					System.out.println("name edit successfully");
				}else {
					System.out.println("name not edited ");
					softAssert.fail("Name is not edited.");
				}
				
				driver.navigate().refresh();
				//delete site**
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='dropdownMenuSites']"))).click();
				Thread.sleep(5000);
				WebElement site3 =driver.findElement(By.xpath("//li[@data-site-name='"+renamesite+"']"));

				Actions actions1 = new Actions(driver);
				Thread.sleep(3000);
				actions1.moveToElement(site3).build().perform();
				Thread.sleep(3000);
				WebElement delete=driver.findElement(By.xpath("//li[@data-site-name='"+renamesite+"']/i[@title='Delete site']"));
				actions1.moveToElement(delete).click().build().perform();
				Thread.sleep(3000);
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtTypeDelete']"))).sendKeys("Delete");
				Thread.sleep(3000);
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnDeleteSitePopup']"))).click();
				Thread.sleep(3000);
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='dropdownMenuSites']"))).click();
				
				WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//li[@data-site-name='"+renamesite+"']")));
				System.out.println("Site delete ");


			}catch(Exception es) {
				WebElement comment=driver.findElement(By.xpath("//label[@id='lblAddSiteError']")); 
				String Text=comment.getText();
				System.out.println(Text);
				softAssert.fail(Text);
			}

		softAssert.assertAll();
	}
}
