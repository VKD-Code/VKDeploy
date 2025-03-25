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


public class TC0035_AntivirusInstall extends SetupDriver{
	private static WebDriverWait wait ;
	@Test(description = "Make sure AV get installed if enable AV in already assigned policy.")

	public void Antivirusinstall() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		Thread.sleep(5000);
		driver.findElement(By.xpath("//a[@id='navControlGrid']")).click();

		WebElement computerselect =wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
		computerselect.click();

		WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
		allButton.click();

		//policy

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']"))).click();

		//create policy
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='CREATE NEW POLICY']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='Windows Policy']"))).click();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String policyname=Finalread.AntiPolicyName;


		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtPolicyName']"))).sendKeys(policyname);

		//Anti-virus install
		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-target='#antiVirus']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnEnableAntiVirus']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@value='SAVE'])[2]"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Applications']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-dialog']//div[@class='dx-texteditor-input-container'])[3]"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-popup-content']//div[text()='"+policyname+"']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='policyFAIcon']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();

		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnInstallAvPolicyAssign']"))).click();

		WebElement policy = driver.findElement(By.xpath("//div[@id='dvPendingApplicationsGrid']//td[@aria-label='Column Policy']"));

		String colIndex = policy.getAttribute("aria-colindex");
		System.out.println(colIndex);
		Thread.sleep(3000);


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

				//Antivirus  page
				wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[@data-target='#dvFDAntivirus']"))).click();
				Thread.sleep(5000);
				try {
					Finalread.readStringFromExcel();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				String version=Finalread.Antiversion;

				String exp1="//tr[td[text()='"+version+"'] and td/span[1][text()='"+Window+"']]/td[5]";
				String exp2="//tr[td[@class='failed textColorBlack'] and td/span[1][text()='"+Window+"']]/td[5]";

				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(40)));
				wait.until(ExpectedConditions.or(
						ExpectedConditions.presenceOfElementLocated(By.xpath(exp1)),
						ExpectedConditions.presenceOfElementLocated(By.xpath(exp2))));
				if(driver.findElements(By.xpath(exp1)).size() > 0) {
					System.out.println("pass");
					driver.navigate().refresh();
					Thread.sleep(5000);
					WebElement status=driver.findElement(By.xpath("//tr/td[@aria-label='Column Protection Status']"));
					String stautusid=status.getAttribute("aria-colindex");

					String protect="//tr[td[text()='Protected']and td/span[text()='"+Window+"']]//td["+stautusid+"]";


					wait = new WebDriverWait( driver, Duration.ofMinutes((long)(60)));
					wait.until(
							ExpectedConditions.presenceOfElementLocated(By.xpath(protect)));
					if(driver.findElements(By.xpath(protect)).size() > 0) {
						System.out.println("computer is Protected");
					}
					else {
						System.out.println("computer is  not Protected");
						softAssert.fail("computer is  not Protected");
					}

				}else if(driver.findElements(By.xpath(exp2)).size() == 0) {
					System.out.println("failed");
					softAssert.fail("Failed.");
				}

				else {
					System.out.println("condition not met");
					softAssert.fail("condition not met.");
				}


			}
			else {
				System.out.println("policy  assign not properly");
				softAssert.fail("policy  assign not properlyt.");
			}
		}else {
			System.out.println("condition not met");
			softAssert.fail("condition not met.");

		}

		softAssert.assertAll();

	}

}
