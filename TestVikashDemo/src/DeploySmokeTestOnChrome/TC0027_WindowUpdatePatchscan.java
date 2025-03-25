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


public class TC0027_WindowUpdatePatchscan extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Verify patch scan should be performed and pending updates should be reported to WU page.")

	public void Patchscan() throws InterruptedException
	{
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	

			Thread.sleep(5000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
			
			WebElement computerselect = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			
			WebElement allButton = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			Thread.sleep(5000);
			WebElement cmpid=driver.findElement(By.xpath("(//td[span[text()='"+Window+"']]//div)[2]"));//computer id 
			String computerid=cmpid.getAttribute("id");
			System.out.println(computerid);
			
			//window update page

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='nav nav-tabs noBorder']//li/a[@data-target='#dvFDWindowsUpdate']"))).click();
			
			WebDriverWait wt=new WebDriverWait(driver, Duration.ofMinutes(1));
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
		
			
			WebElement computerselect1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div/img[@id='appSearchImg'])[1]")));
			computerselect1.click();
			
			WebElement allButton1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton1.click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div//table//tr//td//span[text()='"+Window+"'])[2]"))).click();
			
			//patch scan button
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnPatchScan']"))).click();
			

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();//confirmation box 
			

			String exp1="(//td[span[text()='"+Window+"']]/div[@class='verticalBand bgorange winUpdatesWks-"+computerid+" commonClassWithCompId-"+computerid+"'])[2]";
			System.out.println(exp1);
			String exp2="(//td[span[text()='"+Window+"']]/div[@class='verticalBand bggreen winUpdatesWks-"+computerid+" commonClassWithCompId-"+computerid+"'])[2]";
			System.out.println(exp2);
			wait = new WebDriverWait(driver, Duration.ofMinutes(20));

			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(exp1)),
					ExpectedConditions.presenceOfElementLocated(By.xpath(exp2))
					));
			System.out.println("Condition met");
			if (driver.findElements(By.xpath(exp1)).size() > 0) {
				System.out.print("expression1  met");

				//check status
				WebElement status=driver.findElement(By.xpath("//tr/td[@aria-label='Column Status']"));
				String index=status.getAttribute("aria-colindex");


				WebElement statusresult= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+index+"]//span"));
				String Finalstatus=statusresult.getText();

				System.out.print("Finalstatus:"+Finalstatus);
				Thread.sleep(5000);
				//total update
				WebElement TPU=driver.findElement(By.xpath("//tr/td[@aria-label='Column Pending Windows Updates']"));
				String index1=TPU.getAttribute("aria-colindex");

				WebElement pending=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+index1+"]//span"));
				String Pendingupdate=pending.getText();

				System.out.print("total Pendingupdate:"+Pendingupdate);

			} else if (driver.findElements(By.xpath(exp2)).size() > 0) {

				System.out.print("expression2  met");

				//check status
				WebElement status=driver.findElement(By.xpath("//tr/td[@aria-label='Column Status']"));
				String index=status.getAttribute("aria-colindex");


				WebElement statusresult= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+index+"]//span"));
				String Finalstatus=statusresult.getText();

				System.out.print("Finalstatus:"+Finalstatus);
				WebElement TPU=driver.findElement(By.xpath("//tr/td[@aria-label='Column Pending Windows Updates']"));
				String index1=TPU.getAttribute("aria-colindex");
				Thread.sleep(5000);

				WebElement pending=driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+index1+"]//span"));
				String Pendingupdate=pending.getText();

				System.out.print("total Pendingupdate:"+Pendingupdate);

			}else {
				System.out.print("expression not met");
				softAssert.fail("Patch scan is failed.");
			}
			softAssert.assertAll();
	}
}
