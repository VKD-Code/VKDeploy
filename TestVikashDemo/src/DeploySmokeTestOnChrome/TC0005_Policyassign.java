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


public class TC0005_Policyassign  extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Make sure policy can be assigned to single or multiple wks. Verify policy changes on wks.")

	public void policyassign() throws InterruptedException, AWTException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
		WebDriverWait wait2=new WebDriverWait(driver, Duration.ofSeconds(60));
			Thread.sleep(5000);
			WebElement computerselect = driver.findElement(By.xpath("//div/img[@id='appSearchImg']"));
			computerselect.click();
			WebElement allButton1=wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			
			allButton1.click();
			


			//policy

			driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']")).click();
			
			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='CREATE NEW POLICY']"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='Windows Policy']"))).click();
			
			Thread.sleep(5000);
			
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String policyname=Finalread.policy;
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtPolicyName']"))).sendKeys(policyname);
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@value='SAVE'])[2]"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Applications']"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-dialog']//div[@class='dx-texteditor-input-container'])[3]"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-popup-content']//div[text()='"+policyname+"']"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='policyFAIcon']"))).click();
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			
			WebElement policy = driver.findElement(By.xpath("//td[@aria-label='Column Policy']"));

			String colIndex = policy.getAttribute("aria-colindex");
			System.out.println(colIndex);
			Thread.sleep(5000);

			String condition1 ="//tr[td[text()='"+policyname+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
			System.out.println(condition1);

			wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));
			System.out.println("Condition met");


			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				System.out.println("Condition 1 met");
				WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
				String policyid=app6.getText();
				if(policyid.equals(policyname)) {
					System.out.println("policy  assign");
					
					driver.findElement(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']")).click();
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-dialog']//div[@class='dx-texteditor-input-container'])[3]"))).click();
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-popup-content']//div[text()='Manual']"))).click();
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='policyFAIcon']"))).click();
					wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
					
					Thread.sleep(5000);
					WebElement policy1 = driver.findElement(By.xpath("//td[@aria-label='Column Policy']"));

					String colIndex1 = policy1.getAttribute("aria-colindex");
					System.out.println(colIndex1);
					Thread.sleep(5000);

					String condition3 ="//tr[td[text()='Manual'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
					System.out.println(condition3);

					wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition3)));
					System.out.println("Condition met");


					if (driver.findElements(By.xpath(condition3)).size() > 0) {
						System.out.println("Condition 1 met");
						WebElement app5= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]")); 
						String policyid1=app5.getText();
						if(policyid1.equals("Manual")) {
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
							softAssert.fail("Policy is not assigned properly.");
						}
					}else {
						System.out.println("condition not met");
						softAssert.fail("Policy is not assigned properly.");

					}

				} else {
					System.out.println("policy  assign not properly");
					softAssert.fail("Policy is not assigned properly.");
				}
			}else {
				System.out.println("condition not met");
				softAssert.fail("Policy is not assigned properly.");

			}
			softAssert.assertAll();

	
	}

}
