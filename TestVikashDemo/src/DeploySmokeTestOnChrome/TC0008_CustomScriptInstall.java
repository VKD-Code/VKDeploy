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


public class TC0008_CustomScriptInstall extends SetupDriver{
	@Test(description = "Verify custom script can be downloaded and installed from web. Verify BAT, PS1 and VB scripts.")
	public void installcustomscript() throws InterruptedException, AWTException {
		SoftAssert softAssert=new SoftAssert();
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		
			Thread.sleep(5000);
			 WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
			WebElement gridOption =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn dropdown-toggle gridOptionsCustomBtn']")));
			gridOption.click();
			

			//off group by application
			WebElement offButton =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkPendingUpdateSwitch' and @checked='checked']]")));
			offButton.click();
			
			WebElement computerselect =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/img[@id='appSearchImg']")));
			computerselect.click();
			
			WebElement allButton =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/a[text()='All']")));
			allButton.click();
			

			//click create custom script 
			WebElement customscript =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='btn btnWhite updateAppButton '])[2]")));
			customscript.click();
			

			//click  new create custom script
			WebElement newcreatecustomscript =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create Custom Script']")));
			newcreatecustomscript.click();
			
			
		
			//Add script name 
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String scriptnames=Finalread.batchname;
			WebElement scriptname =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtCustScriptAppName']")));
			scriptname.sendKeys(scriptnames);
			

			//Enter url
			String Urls="https://faronics-qa-installer.s3-us-west-2.amazonaws.com/Scripts/CreateFolder_Bat.Bat";
			WebElement enterurl =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtUrlNetworkLocation']")));
			enterurl.sendKeys(Urls);
			

			//select type of scripting
			WebElement type =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptTypes']")));
			type.click();



			String type3="Batch Script";

			WebElement selecttype =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptTypes']//option[text()='"+type3+"']")));
			selecttype.click();
			
			/*
	        //Add command line
	       String commandline="swjbn";
	       WebElement Line = driver.findElement(By.xpath(" //textarea[@id='txtCustScriptCommandLine']"));
	        Line.sendKeys(commandline);
	        Thread.sleep(3000);
			 */
			//Add Run 
			WebElement Run =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptSpecifiedUserAcc']")));
			Run.click();

			String Run1="System Account";

			String Run2="Specified User Account";
			WebElement Runopt =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptSpecifiedUserAcc']//option[text()='"+Run1+"']")));
			Runopt.click();


			//Save to grid
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div/input[@id='btnCustScriptSaveToGrid'])[2]"))).click();
			

			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@class='dx-row dx-column-lines dx-header-row']/td/div/span[text()='Custom Scripts'])[1]"))).click();
			Thread.sleep(5000);

			WebElement script=driver.findElement(By.xpath("//tr/td[@aria-label='Column "+scriptnames+"']"));
			String scriptindex=script.getAttribute("aria-colindex");
			Thread.sleep(3000);

			driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+scriptindex+"]")).click();
			


			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnRunCustomScript']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();

			WebDriverWait wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			String condition1="//tr[td/span[1][text()='"+Window+"']]//td["+scriptindex+"][text()='Script Executed']";
			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition1))));

			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				Thread.sleep(3000);
				System.out.println("Batch script created");
				Thread.sleep(3000);

				driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]//td["+scriptindex+"]//img")).click();
				Thread.sleep(3000);

				WebElement note=driver.findElement(By.xpath("//div[@id='appLogsDetails']"));
				String logs=note.getText();
				System.out.println(logs);

			}  else {
				System.out.println("Neither condition met");
				softAssert.fail("Case failed condition not met.");
			}
			driver.findElement(By.xpath("//button[text()='DOWNLOAD']")).click();
			Thread.sleep(3000);

			driver.findElement(By.xpath("(//div[@class='modal-header p-2 popupTitle']//button[text()='×'])[14]")).click();
			Thread.sleep(5000);


			//shell script***

			//click create custom script 
			WebElement customscript2 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='btn btnWhite updateAppButton '])[2]")));
			customscript2.click();
			

			//click  new create custom script
			WebElement newcreatecustomscript2 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create Custom Script']")));
			newcreatecustomscript2.click();
			

			//Add script name 
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			
			String scriptnames2=Finalread.shellname;
			WebElement scriptname2 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtCustScriptAppName']")));
			scriptname2.sendKeys(scriptnames2);
			

			//Enter url
			String Urls2="https://faronics-qa-installer.s3-us-west-2.amazonaws.com/Scripts/CreateFolder_PS.ps1";
			WebElement enterurl2 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtUrlNetworkLocation']")));
			enterurl2.sendKeys(Urls2);
			

			//select type of scripting
			WebElement type2 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptTypes']")));
			type2.click();

			String type1="PowerShell";
			//String type2="VB Script";


			WebElement selecttype2 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptTypes']//option[text()='"+type1+"']")));
			selecttype2.click();
			
			/*
	        //Add command line
	       String commandline="swjbn";
	       WebElement Line = driver.findElement(By.xpath(" //textarea[@id='txtCustScriptCommandLine']"));
	        Line.sendKeys(commandline);
	        Thread.sleep(3000);
			 */

			//Add Run 
			WebElement Run3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptSpecifiedUserAcc']")));
			Run3.click();

			String Runas1="System Account";

			String Runas2="Specified User Account";
			WebElement Runopt2 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptSpecifiedUserAcc']//option[text()='"+Runas1+"']")));
			Runopt2.click();



			//Save to grid
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div/input[@id='btnCustScriptSaveToGrid'])[2]"))).click();
			

			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@class='dx-row dx-column-lines dx-header-row']/td/div/span[text()='Custom Scripts'])[1]"))).click();
			Thread.sleep(5000);

			WebElement script2=driver.findElement(By.xpath("//tr/td[@aria-label='Column "+scriptnames2+"']"));
			String scriptindex2=script2.getAttribute("aria-colindex");


			driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+scriptindex2+"]")).click();
			


			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnRunCustomScript']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();

			WebDriverWait wait2 = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			String condition2="//tr[td/span[1][text()='"+Window+"']]//td["+scriptindex+"][text()='Script Executed']";
			wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition2)));

			if (driver.findElements(By.xpath(condition2)).size() > 0) {
				Thread.sleep(3000);
				System.out.println("powersell script created");
				Thread.sleep(3000);
				driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]//td["+scriptindex2+"]//img")).click();
				Thread.sleep(3000);
				WebElement note=driver.findElement(By.xpath("//div[@id='appLogsDetails']"));
				String logs=note.getText();
				System.out.println(logs);

			}  else {
				System.out.println("Neither condition met");
				softAssert.fail("Case failed condition not met.");
			}
			driver.findElement(By.xpath("//button[text()='DOWNLOAD']")).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='modal-header p-2 popupTitle']//button[text()='×'])[14]"))).click();
			Thread.sleep(5000);

			//VB script***

			//click create custom script 
			WebElement customscript3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='btn btnWhite updateAppButton '])[2]")));
			customscript3.click();
			

			//click  new create custom script
			WebElement newcreatecustomscript3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Create Custom Script']")));
			newcreatecustomscript3.click();
			

			//Add script name 
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String scriptnames3=Finalread.vbname;
			WebElement scriptname3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtCustScriptAppName']")));
			scriptname3.sendKeys(scriptnames3);
			

			//Enter url
			String Urls3="https://faronics-qa-installer.s3-us-west-2.amazonaws.com/Scripts/CreateFolder_VB.vbs";
			WebElement enterurl3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtUrlNetworkLocation']")));
			enterurl3.sendKeys(Urls3);
			

			//select type of scripting
			WebElement types3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptTypes']")));
			types3.click();


			String types2="VB Script";


			WebElement selecttype3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptTypes']//option[text()='"+types2+"']")));
			selecttype3.click();
			
			/*
		        //Add command line
		       String commandline="swjbn";
		       WebElement Line = driver.findElement(By.xpath(" //textarea[@id='txtCustScriptCommandLine']"));
		        Line.sendKeys(commandline);
		        Thread.sleep(3000);
			 */

			//Add Run 
			WebElement Run4 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptSpecifiedUserAcc']")));
			Run4.click();

			String Runasopt1="System Account";

			String Runasopt2="Specified User Account";
			WebElement Runopt3 =  wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='ddCustScriptSpecifiedUserAcc']//option[text()='"+Runasopt1+"']")));
			Runopt3.click();



			//Save to grid
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div/input[@id='btnCustScriptSaveToGrid'])[2]"))).click();
			Thread.sleep(5000);

			driver.findElement(By.xpath("(//tr[@class='dx-row dx-column-lines dx-header-row']/td/div/span[text()='Custom Scripts'])[1]")).click();
			Thread.sleep(5000);

			WebElement script3=driver.findElement(By.xpath("//tr/td[@aria-label='Column "+scriptnames3+"']"));
			String scriptindex3=script3.getAttribute("aria-colindex");


			driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+scriptindex3+"]")).click();
			


			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnRunCustomScript']"))).click();
			
			 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();

			WebDriverWait wait3 = new WebDriverWait( driver, Duration.ofMinutes((long)(10)));

			String condition3="//tr[td/span[1][text()='"+Window+"']]//td["+scriptindex3+"][text()='Script Executed']";
			wait3.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition3))));

			if (driver.findElements(By.xpath(condition3)).size() > 0) {
				Thread.sleep(3000);
				System.out.println("VBsell script created");
				Thread.sleep(3000);
				driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]//td["+scriptindex3+"]//img")).click();
				Thread.sleep(3000);
				WebElement note=driver.findElement(By.xpath("//div[@id='appLogsDetails']"));
				String logs=note.getText();
				System.out.println(logs);

			}  else {
				System.out.println("Neither condition met");
				softAssert.fail("Case failed condition not met.");
			}
			driver.findElement(By.xpath("//button[text()='DOWNLOAD']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("(//div[@class='modal-header p-2 popupTitle']//button[text()='×'])[14]")).click();
			Thread.sleep(5000);
		driver.navigate().refresh();
		softAssert.assertAll();
	}
	
}
