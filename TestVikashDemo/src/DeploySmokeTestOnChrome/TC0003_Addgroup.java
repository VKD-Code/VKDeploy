package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.Finalread;
import BaseClasses.SetupDriver;



public class TC0003_Addgroup extends SetupDriver{
	
	@Test(description="Select Workstation(s) > Assign Group > Verify Group Assignment")
	public void addgroup() throws InterruptedException, AWTException {
		SoftAssert softAssert = new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
		// Open first url deep freeze website
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		driver.get(Finalread.URL1);

		// Enter email
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		WebElement emailField = driver.findElement(By.xpath("//input[@id='txtLoginEmail']"));
		emailField.sendKeys(Finalread.Email1);

		// Enter password
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		System.out.println("Check console log for login");
		WebElement passwordField = driver.findElement(By.xpath("//input[@id='Password']"));
		passwordField.sendKeys(Finalread. Password1);
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value='Sign in']")).click();
		System.out.println("Login successfull.");
		
		Thread.sleep(5000);
		
		WebDriverWait wt=new WebDriverWait(driver, Duration.ofSeconds(5));
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Grid Options']"))).click();
		wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
		System.out.println("Vikash Test log");
//			wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']"))).click();
//			wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']"))).click();
			wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			System.out.println("Check console log");
			//Add group
			wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvSearchGroup']"))).click();
			
			Thread.sleep(3000);
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			
			String Groupname=Finalread.Groupname;
			wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtNewGroupName']"))).sendKeys(Groupname);
			wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='addgroupbtn']"))).click();
			Thread.sleep(7000);
			driver.findElement(By.xpath("//div[@id='dvSearchGroup']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@class='dx-popup-content']//div[text()='"+Groupname+"']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='groupFAIcon']")).click();
			
			WebElement group = driver.findElement(By.xpath("//td[@aria-label='Column Group']"));

			String colIndex = group.getAttribute("aria-colindex");
			System.out.println(colIndex);
			Thread.sleep(3000);

			WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
			String groupid=app6.getText();
			if(groupid.equals(Groupname)) {
				System.out.println("Group assign");
			}
			else {
				System.out.println("Group assign not properly");
				softAssert.fail("Group is not assigned.");
			}
			softAssert.assertAll();


	
	}
}
