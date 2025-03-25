package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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


public class TC0028_UserAED extends SetupDriver {

	@Test(description = "Make sure User can be added, edited and deleted successfully.")
	public void userAED() throws InterruptedException, AWTException {


		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//a/i[@class='fa fa-user-circle']")).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='User Management']"))).click();
			

			//Add User***
			
			WebDriverWait wt=new WebDriverWait(driver, Duration.ofMinutes(1));
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
		
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button/i[@class='fa fa-angle-down'])[2]"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='User via Email']"))).click();
			
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String fullname=Finalread.Adduser;
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtUserFullName']"))).sendKeys(fullname);

			String emails1="deploy123887@gmail.com";
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtEmailAddress']"))).sendKeys(emails1);
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnAddUserSubmit']"))).click();
			
			driver.navigate().refresh();
			String condition="//div[contains(text(),'"+fullname+"')]";
			System.out.println(condition);

			WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(60));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition)));
			if (driver.findElements(By.xpath(condition)).size() > 0) {
				System.out.println("User created");
				

				//edit user***
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '"+fullname+"')]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnEditManageUser']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtUserFullName']"))).click();

				Robot robot1=new Robot();
				//press
				robot1.keyPress(KeyEvent.VK_CONTROL);
				robot1.keyPress(KeyEvent.VK_A);

				//release
				robot1.keyRelease(KeyEvent.VK_CONTROL);
				robot1.keyRelease(KeyEvent.VK_A);
				
				try {
					Finalread.readStringFromExcel();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				String Rename=Finalread.Edituser;
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtUserFullName']"))).sendKeys(Rename);
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnAddUserSubmit']"))).click();
	 				   
				driver.navigate().refresh();
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), '"+Rename+"')]"))).click();
				System.out.print("name edit sucessfully");
				
				try {
					//delete user***
					wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnEditManageUser']"))).click();
					
					wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnUserManageDeleteUser']"))).click();
					
					wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnDeleteUserPopup']"))).click();
					
					driver.navigate().refresh();
					String condition2="//div[contains(text(),'"+Rename+"')]";
					System.out.println(condition2);
					WebDriverWait wait2=new WebDriverWait(driver,Duration.ofSeconds(10));
					wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition2)));
					if (driver.findElements(By.xpath(condition2)).size() == 0) {
						System.out.println("User deleted");
						Thread.sleep(3000);
					}else {
						System.out.print("user not deleted ");
						softAssert.fail("user not deleted.");

					}
				}catch(Exception es) {
					System.out.print("name not edited ");
					softAssert.fail("name not edited.");
				}
			}else {
				System.out.println("User not created");
				softAssert.fail("User not created.");
			}
			softAssert.assertAll();
		}
}
