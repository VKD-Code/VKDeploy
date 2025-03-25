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


public class TC0041_AntivirusUninstall extends SetupDriver{
	private static WebDriverWait wait ;
	@Test(description = "Make sure AV get uninstalled if disable AV in assigned policy.")

	public void Antivirusuninstall() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait2=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	
			
			Thread.sleep(5000);

			//policy

			driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']")).click();
			

			//select policy
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String policyname=Finalread.AntiPolicyName;
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+policyname+"']]"))).click();
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-12 text-right']//button[text()='EDIT POLICY']"))).click();
			
			//Anti-virus Uninstall
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-target='#antiVirus']"))).click();
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Remove Anti-Virus']"))).click();
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnUninstallAV']"))).click();
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnPopupUninstallAV']"))).click();
			Thread.sleep(5000);
			WebElement policy1 = driver.findElement(By.xpath("//td[@aria-label='Column Anti-Virus']"));

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
				System.out.println("Antivirus disabled");
				driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Applications']")).click();
				Thread.sleep(5000);
				String condition2="(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']/preceding-sibling::div[@title='Offline']";
				WebDriverWait wait = new WebDriverWait( driver, Duration.ofMinutes((long)(20)));

				wait.until(ExpectedConditions.or(
						ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))));
				if (driver.findElements(By.xpath(condition2)).size() > 0) {
					System.out.println("computer is offline");
					WebDriverWait wait1 = new WebDriverWait( driver, Duration.ofMinutes((long)(20)));
					wait1.until(ExpectedConditions.or(
							ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition2))));
					if (driver.findElements(By.xpath(condition2)).size() == 0) {
						System.out.println("computer is online");
						
						driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Applications']")).click();
						Thread.sleep(5000);

						driver.findElement(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']")).click();
						Thread.sleep(5000);
						driver.findElement(By.xpath("(//div[@class='modal-dialog']//div[@class='dx-texteditor-input-container'])[3]")).click();
						Thread.sleep(5000);
						driver.findElement(By.xpath("//div[@class='dx-popup-content']//div[text()='Manual']")).click();
						Thread.sleep(3000);
						driver.findElement(By.xpath("//div[@id='policyFAIcon']")).click();
						Thread.sleep(5000);
						driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
						Thread.sleep(5000);
						WebElement policy = driver.findElement(By.xpath("//td[@aria-label='Column Policy']"));

						String colIndex = policy.getAttribute("aria-colindex");
						System.out.println(colIndex);
						Thread.sleep(5000);

						String condition1 ="//tr[td[text()='Manual'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
						System.out.println(condition1);

						wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));
						System.out.println("Condition met");


						if (driver.findElements(By.xpath(condition1)).size() > 0) {
							System.out.println("Condition 1 met");
							WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
							String policyid=app6.getText();
							if(policyid.equals("Manual")) {
								System.out.println("policy  assign");
								Thread.sleep(3000);
								driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']")).click();
								
								Thread.sleep(5000);
								driver.findElement(By.xpath("//span[text()='"+policyname+"']")).click();
								Thread.sleep(3000);
								driver.findElement(By.cssSelector("button#btnDeletePolicyMain")).click();
								Thread.sleep(3000);
								driver.findElement(By.cssSelector("input#btnYesToDelete")).click();
								WebDriverWait wait3 = new WebDriverWait( driver, Duration.ofSeconds(30));
								wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='"+policyname+"']")));
								System.out.println("Policy:"+policyname+"deleted");
							} else {
								System.out.println("policy  assign not properly");
							}
						}else {
							System.out.println("condition not met");

						}
						
						
						
						
					}else {
						System.out.println("computer is offline");
					}
					}else {
						System.out.println("computer is online");
					}

			}else {
				System.out.println("Antivirus  not disable");
				softAssert.fail("Antivirus is not disabled.");
			}
			softAssert.assertAll();
		
	}

}
