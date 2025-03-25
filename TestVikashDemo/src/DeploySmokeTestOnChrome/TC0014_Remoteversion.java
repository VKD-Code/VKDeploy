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

public class TC0014_Remoteversion extends SetupDriver {

	@Test(description = "Make sure after assign policy remote version get correctly.")

	public void remoteversion() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
			Thread.sleep(5000);
			//Analytics  page
			 WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navAnalytics']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Deploy Diagnostics']"))).click();
			
			WebDriverWait wt=new WebDriverWait(driver, Duration.ofMinutes(1));
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
			Thread.sleep(10000);
			WebElement remote=driver.findElement(By.xpath("//td[@aria-label='Column Remote Pro']"));
			Actions action=new Actions(driver);
			action.moveToElement(remote).build().perform();
			String remoteindex=remote.getAttribute("aria-colindex");
			Thread.sleep(5000);
	

			WebElement remoteversion=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+remoteindex+"]"));
			action.moveToElement(remoteversion).build().perform();
			String Actualversion=remoteversion.getText();
            System.out.print(Actualversion);
			String Expectedversion="1.20.8100.32";
			if(Actualversion.equals(Expectedversion)) {
				System.out.println("Remote version correct");

			}else {
				System.out.println("Remote version is incorrect");
				softAssert.fail("Remote version is incorrect");
			}
			softAssert.assertAll();
		}
}
