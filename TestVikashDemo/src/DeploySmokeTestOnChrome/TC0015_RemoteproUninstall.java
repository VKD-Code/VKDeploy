package DeploySmokeTestOnChrome;

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

public class TC0015_RemoteproUninstall  extends SetupDriver{
	private static WebDriverWait wait ;
	@Test(description="Make sure Remote policy should be uninstalled if assigned without remote pro enabled policy.")
	public void remoteproUninstall() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		Thread.sleep(5000);

		//policy
		WebDriverWait wait2=new WebDriverWait(driver, Duration.ofSeconds(60));

		 wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a#navControlGrid"))).click();
		
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']"))).click();
		
		//select policy

		String policyname="Remote";
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+policyname+"']]"))).click();
		

		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-12 text-right']//button[text()='EDIT POLICY']"))).click();
		
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-target='#remotePro']"))).click();
		
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnUninstallRCP']"))).click();
		
		wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnPopupUninstallRCP']"))).click();
		

		WebElement policy1 = driver.findElement(By.xpath("//td[@aria-label='Column Remote Pro']"));

		String colIndex1 = policy1.getAttribute("aria-colindex");
		System.out.println(colIndex1);
		Thread.sleep(3000);
		String conditions1 ="//tr[td[text()='Enabled'] and td/span[1][text()='"+policyname+"']]/td["+colIndex1+"]";
		System.out.println(conditions1);

		wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(conditions1)));
		System.out.println("Condition met");
		if (driver.findElements(By.xpath(conditions1)).size() == 0) {
			System.out.println("Condition 1 met");
			System.out.println("Remote disabled");
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Applications']"))).click();
			Thread.sleep(5000);

			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-dialog']//div[@class='dx-texteditor-input-container'])[3]"))).click();
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-popup-content']//div[text()='Manual']"))).click();
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='policyFAIcon']"))).click();
		
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			Thread.sleep(5000);
			WebElement policy = driver.findElement(By.xpath("//td[@aria-label='Column Policy']"));

			String colIndex = policy.getAttribute("aria-colindex");
			System.out.println(colIndex);
			Thread.sleep(5000);

			String condition1 ="//tr[td[text()='Manual'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
			System.out.println(condition1);

			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));
			System.out.println("Condition met");


			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("Condition 1 met");
				WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
				String policyid=app6.getText();
				if(policyid.equals("Manual")) {
					System.out.println("policy  assign");
					
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']"))).click();

					
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+policyname+"']"))).click();
					
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button#btnDeletePolicyMain"))).click();
					
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#btnYesToDelete"))).click();
					WebDriverWait wait1 = new WebDriverWait( driver, Duration.ofSeconds(30));
					wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='"+policyname+"']")));
					System.out.println("Policy:"+policyname+"deleted");
				} else {
					System.out.println("policy  assign not properly");
					softAssert.fail("Policy is not properly assigned.");
				}
			}else {
				System.out.println("condition not met");
				softAssert.fail("Remote pro is not uninstalled.");

			}
		}else {
			System.out.println("Remote  not disable");
			softAssert.fail("Unabble to disable the remote pro from policy.");
		}
		softAssert.assertAll();


	}
}
