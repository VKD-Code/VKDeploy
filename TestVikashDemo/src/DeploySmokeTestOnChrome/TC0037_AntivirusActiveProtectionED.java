package DeploySmokeTestOnChrome;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;


public class TC0037_AntivirusActiveProtectionED extends SetupDriver{
	private static WebDriverWait wait ;
	@Test(description = "Enable/Disable AP and make sure status changed on wks and also on console.")
	public void AntivirusAPenabledisable() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;


			//anti-virus page 
			driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[@data-target='#dvFDAntivirus']")).click();
			
			WebElement computerselect = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			
			WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			
			WebElement AP = driver.findElement(By.xpath("//td[@aria-label='Column Active Protection']"));

			String colIndex1 = AP.getAttribute("aria-colindex");
			System.out.println(colIndex1);
			Thread.sleep(3000);

			WebElement activeprotection=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"));
			String result=activeprotection.getText();
			Thread.sleep(3000);
			//enable
			if(result.equals("Enabled")) {
				System.out.println("Already Enabled");
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVActiveProtection']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu ddMenuFont show']//a[text()='Disable']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition1 ="//tr[td[text()='Disabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition1);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition1)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("Active-protection disable");
					Thread.sleep(5000);

				}else {
					System.out.println("Active-protection not disable");
					softAssert.fail("Active-protection is not disabled.");
				}
			}else {
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVActiveProtection']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu ddMenuFont show']//a[text()='Enable']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition2 ="//tr[td[text()='Enabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition2);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition2)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition2)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("Active-protection enabled");
					Thread.sleep(5000);

				} else {
					System.out.println("Active-protection not enable");
					softAssert.fail("Active-protection is not enabled.");
					
				}


			}
			//disable
			driver.navigate().refresh();
			Thread.sleep(3000);
			WebElement activeprotection2=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"));
			String result2=activeprotection2.getText();
			Thread.sleep(3000);


			if(result2.equals("Disabled")) {
				System.out.println("Already Disabled");
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVActiveProtection']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu ddMenuFont show']//a[text()='Enable']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition3 ="//tr[td[text()='Enabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition3);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition3)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition3)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("Active-protection enabled");
					Thread.sleep(5000);

				} else {
					System.out.println("Active-protection not enable");
					softAssert.fail("Active-protection is not enabled.");
				}
			}else {

				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVActiveProtection']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dropdown-menu ddMenuFont show']//a[text()='Disable']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition4 ="//tr[td[text()='Disabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition4);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition4)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition4)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("Active-protection disable");
					Thread.sleep(5000);

				}else {
					System.out.println("Active-protection not disable");
					softAssert.fail("Active-protection is not disabled.");
				}

			}
			softAssert.assertAll();

	}
}
