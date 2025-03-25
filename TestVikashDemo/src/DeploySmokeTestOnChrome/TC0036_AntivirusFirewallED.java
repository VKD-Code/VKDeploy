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


public class TC0036_AntivirusFirewallED extends SetupDriver{
	private static WebDriverWait wait ;
	@Test(description = "Enable/Disable FW and  make sure status changed on wks and also on console.")
	public void AntivirusFWenabledisable() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
		Thread.sleep(5000);

		
			
			//anti-virus page 
			driver.findElement(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[@data-target='#dvFDAntivirus']")).click();
			
			WebElement computerselect = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			Thread.sleep(5000);
			WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			
			WebDriverWait wt=new WebDriverWait(driver, Duration.ofMinutes(1));
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
			driver.navigate().refresh();
		
			Thread.sleep(15000);
			Thread.sleep(5000);
			WebElement FW = driver.findElement(By.xpath("//td[@aria-label='Column Firewall Protection']"));

			String colIndex1 = FW.getAttribute("aria-colindex");
			System.out.println(colIndex1);
			Thread.sleep(3000);

			WebElement firewall=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"));
			String result=firewall.getText();
			Thread.sleep(3000);

			//enable********************************

			if(result.equals("Enabled")) {
				System.out.println("Already Enabled");
				

				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVFirewall']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aDisableFW']"))).click();
			
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition1 ="//tr[td[text()='Disabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition1);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition1)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("firewall disable");
					Thread.sleep(5000);

				}else {
					System.out.println("firewall not disable");
					softAssert.fail("firewall not disable.");
				}

			}else {
				System.out.println("not Enable");
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVFirewall']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aEnableFW']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition2 ="//tr[td[text()='Enabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition2);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition2)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition2)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("firewall enabled");
					Thread.sleep(5000);
				} else {
					System.out.println("firewall not enable");
					softAssert.fail("firewall not disable.");
				}
			}
			driver.navigate().refresh();
			Thread.sleep(3000);

			//disable***************************************************

			WebElement activeprotection2=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"));
			String result2=activeprotection2.getText();
			Thread.sleep(3000);

			System.out.println(result2);

			if(result2.equals("Disabled")) {
				System.out.println("Already Disabled");
				

				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				

				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVFirewall']"))).click();
				

				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aEnableFW']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition3 ="//tr[td[text()='Enabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition3);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition3)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition3)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("firewall enabled");
					Thread.sleep(5000);
				} else {
					System.out.println("firewall not enable");
					softAssert.fail("firewall not disable.");
				}
			}else {

				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex1+"]"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnAVFirewall']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aDisableFW']"))).click();
				
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
				String condition4 ="//tr[td[text()='Disabled'] and td/span[1][text()='"+Window+"']]/td["+colIndex1+"]";
				System.out.println(condition4);

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition4)));
				System.out.println("Condition met");
				if (driver.findElements(By.xpath(condition4)).size() > 0) {
					System.out.println("Condition 1 met");
					System.out.println("firewall disable");
					Thread.sleep(5000);

				}else {
					System.out.println("firewall not disable");
					softAssert.fail("firewall not disable.");
				}


			}
			softAssert.assertAll();

		
	}

}
