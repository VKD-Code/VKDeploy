package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;

import BaseClasses.Finalread;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;



public class TC0004_Assigntag extends SetupDriver{
	
	@Test(description="Select Workstation(s) > Add Tag > Verify Tag Assignment")
	public void assigntag() throws InterruptedException, AWTException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		 WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(5));
//		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']"))).click();
//		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']"))).click();
	     wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
	     wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Manage Tags']"))).click();
	     wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Tag']"))).click();
		
			Thread.sleep(3000);
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			String tag=Finalread.Assigntag;
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtNewTagName']"))).sendKeys(tag);
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='SAVE'])[4]"))).click();
		
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Done']"))).click();
			Thread.sleep(3000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-dialog']//input[@class='dx-texteditor-input']"))).sendKeys(tag);
			Thread.sleep(3000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dvTagGrey']//div[text()='"+tag+"']"))).click();
			Thread.sleep(3000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-header']/button[@class='close'])[2]"))).click();
		
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@aria-label='Column Tags']")));
			WebElement tags = driver.findElement(By.xpath("//td[@aria-label='Column Tags']"));
			String colIndex = tags.getAttribute("aria-colindex");
			System.out.println(colIndex);
			Thread.sleep(3000);
			WebElement tagname= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]"));
			String tagused=tagname.getText();
			if(tagused.equals(tagused)) {
				System.out.println("Tag assign successful");

			}else{
				System.out.println("Tag not assign successful");
				softAssert.fail("Tag is not assigned.");
			}
			softAssert.assertAll();




	}

}
