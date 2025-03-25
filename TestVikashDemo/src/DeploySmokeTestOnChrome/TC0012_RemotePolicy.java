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

public class TC0012_RemotePolicy extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Make sure Remote get installed if enable remote in already assigned policy.")

	public void Remoteinstall() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
			
		
			Thread.sleep(5000);
			 WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
			//policy
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']"))).click();
			
			//create policy
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='CREATE NEW POLICY']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='Windows Policy']"))).click();
			

			String policyname="Remote";

			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtPolicyName']"))).sendKeys(policyname);
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-target='#remotePro']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Enable Remote Pro']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnPopupInstallRCP']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvBSpopup']//input[@value='SAVE']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvFDMainMenuParent']//a[text()='Applications']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-dialog']//div[@class='dx-texteditor-input-container'])[3]"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-popup-content']//div[text()='"+policyname+"']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='policyFAIcon']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			


					//task status
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='liTaskHistory']"))).click();
					
					WebDriverWait wt=new WebDriverWait(driver, Duration.ofMinutes(1));
					wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
					wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
					Thread.sleep(10000);
				
				WebElement Status =driver.findElement(By.xpath("//tr/td[@aria-label='Column Status']"));
				String statusid=Status.getAttribute("aria-colindex");
				System.out.println( statusid);
			
				String exp1 ="//tr[1][td/span[text()='Completed'] and td/span[1][text()='"+Window+"']]/td["+statusid+"]";
				System.out.println(exp1);
				String exp2 ="//tr[1][td/span[@style='color:red;'] and td/span[1][text()='"+Window+"']]/td["+statusid+"]";
				System.out.println(exp2);
				wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));
				wait.until(ExpectedConditions.or(
						ExpectedConditions.presenceOfElementLocated(By.xpath(exp1)),
						ExpectedConditions.presenceOfElementLocated(By.xpath(exp2))
						));
				if(driver.findElements(By.xpath(exp1)).size() > 0) {
					System.out.println("Task completed");
					
					 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
					 Thread.sleep(5000);

					WebElement policy = driver.findElement(By.xpath("//div[@id='dvPendingApplicationsGrid']//td[@aria-label='Column Policy']"));

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
							System.out.println("Condition met");
							
							//policy
							 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[text()='Policies']"))).click();
							Thread.sleep(5000);
							WebElement policy1 = driver.findElement(By.xpath("//td[@aria-label='Column Remote Pro']"));

							String colIndex1 = policy1.getAttribute("aria-colindex");
							System.out.println(colIndex1);
							Thread.sleep(5000);
							String conditions1 ="//tr[td[text()='Enabled'] and td/span[1][text()='"+policyname+"']]/td["+colIndex1+"]";
							System.out.println(conditions1);

							wait = new WebDriverWait( driver, Duration.ofMinutes((long)(15)));
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(conditions1)));
							System.out.println("Condition met");
							if (driver.findElements(By.xpath(conditions1)).size() > 0) {
								System.out.println("Condition 1 met");
								System.out.println("Remote Enable");
							

							}else {
								System.out.println("Remote  not Enable");
								softAssert.fail("Remote not enable.");
							}

						}else {
						System.out.println("policy not  assign");
						softAssert.fail("policy not  assign.");
					}
				}
					
				}else if(driver.findElements(By.xpath(exp2)).size() > 0){
					System.out.println("Task failed");
					softAssert.fail("Task Failed");
				}
				else {
					System.out.println("neither condition met");
					softAssert.fail("Condition mismatched.");
				}
			
					
				softAssert.assertAll();
				
	
			}
		
	}


