package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.io.IOException;
import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import BaseClasses.Finalread;
import BaseClasses.SetupDriver;

public class TC0046_Cleanup extends SetupDriver{
	
	private static WebDriverWait wait ;
	@Test(description = "Data cleanup.")
	public void cleanup() throws InterruptedException, AWTException {
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofSeconds(60));
		wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
		 wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();
		
		 wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
			
//			Delete Group group
		 try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		 

		 
			String Groupname=Finalread.Groupname;
			
			Thread.sleep(5000);
			 Actions ac=new Actions(driver);
			 ac.moveToElement(driver.findElement(By.xpath("//div[@id='dvCompDetailPopup']//div[@id='dvSearchGroup']"))).click().build().perform();
			
        
	       
	        Thread.sleep(5000);
	        ac.moveToElement(driver.findElement(By.xpath("//div[@class='dx-popup-content']//div[text()='"+Groupname+"']"))).build().perform();
	        Thread.sleep(3000);
	       
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-popup-content']//div[text()='"+Groupname+"']/following::i"))).click();
	        
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@onclick='deleteGroup();']"))).click();
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvCompDetailPopup']//button[@class='close']"))).click();
	        
//	        Delete Tag
	        try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
	        

			String tag=Finalread.Assigntag;
			Thread.sleep(5000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvCompDetailPopup']//input[@value='Manage Tags']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtSearchActiveTags']"))).sendKeys(tag);
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr//span[text()='"+tag+"']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='activeTagDelBtn']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Done']"))).click();
	        Thread.sleep(2000);
	        driver.navigate().refresh();
//	        App Preset Delete
	        
	        try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			String name =Finalread.Apppresetname;
			Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']"))).click();
	        Thread.sleep(4000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#dvSearchAppPreset"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Manage App Presets']"))).click();
	        
	        Thread.sleep(5000);
	        ac.moveToElement(driver.findElement(By.xpath("//div[@id='dvAppPresetsNmList']//div[text()='"+name+"']"))).build().perform();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvAppPresetsNmList']//div[text()='"+name+"']/ancestor::a/i"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#btnConfirmCommonYes"))).click();
	        Thread.sleep(4000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvBSpopup2']//button[@class='close']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvCompDetailPopup']//button[@class='close']"))).click();
//	        Delete Custom script
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='btn btnWhite updateAppButton '])[2]"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='View Custom Scripts']"))).click();
	        Thread.sleep(5000);
	       
	        String[] scriptNames = {"batch", "shell", "VB"};

     for (String script : scriptNames) {
	       //     Click on the scripts
	        	Thread.sleep(5000);
	            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='"+script+"']"))).click();
	            Thread.sleep(2000);
	        //    Perform the additional actions after clicking each Script
	            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnDeleteVCustomScripts']"))).click();
	            Thread.sleep(2000);
	            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
	            Thread.sleep(5000);
     }
     
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvBSpopup']//button[@class='close']"))).click();
	        Thread.sleep(2000);
//	        Delete Custom app
	        String Customapp="Sticky";
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@class='btn btnWhite updateAppButton '])[1]"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='View Custom Apps']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='dx-datagrid-content']//span[text()='"+Customapp+"']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnDeleteVCustomApps']"))).click();
	        Thread.sleep(2000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
	        Thread.sleep(5000);
	        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvBSpopup']//button[@class='close']"))).click();
	        Thread.sleep(2000);
//	        Delete Winget App
         Thread.sleep(5000);
	    	int time = 0;
			try {
			WebElement btn = driver.findElement(By.xpath("//td[@aria-label='Column Winget Apps']"));
			
				if(btn.isDisplayed()) {
					ac.moveToElement(btn).click().build().perform();
					time = 5000;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(time);
			try {
				Finalread.readStringFromExcel();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			String App=Finalread. Wingetappname;
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@aria-label='Column "+App+"']"))).click();
			Thread.sleep(2000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvWingetAppInfoActionToolbar']//input[@value='DELETE']"))).click();
			Thread.sleep(2000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			Thread.sleep(2000);
			
//			Enable Anti-Virus page
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='fa fa-user-circle']"))).click();
			Thread.sleep(2000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Organization Settings']"))).click();
			Thread.sleep(2000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='chkHideAntiVirus']/ancestor::label"))).click();
			Thread.sleep(2000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
			Thread.sleep(2000);
//			Enable 2FA
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='fa fa-user-circle']"))).click();
	        
	        	Thread.sleep(3000);
	    		driver.findElement(By.linkText("Organization Settings")).click();
	    		Thread.sleep(5000);
	    		WebElement value=driver.findElement(By.xpath("//label//input[@id='chkEnable2StepVerification']"));
	             String Result=value.getAttribute("value");
	             if(Result.equals("false"))
	             {
	            	 driver.findElement(By.xpath("(//div[@class='d-inline-block custom-switch checbox-switch switch-primary help-switch']/label/span)[2]")).click();
	            	 Thread.sleep(5000);
	            		try {
			    			Finalread.readStringFromExcel();
			    		} catch (IOException e1) {

			    			e1.printStackTrace();
			    		}

			    		String password=Finalread. Password1;
	            	 
	            	 driver.findElement(By.cssSelector("input#txtEnterPasswordToEnable")).sendKeys(password);
	            	 Thread.sleep(2000);
	            	 driver.findElement(By.xpath("//button[text()='Enable']")).click();
	            	System.out.println( driver.findElement(By.id("dvToastNotification")).getText());
	            	Thread.sleep(7000);
	            	wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='navControlGrid']"))).click();
	            	Thread.sleep(2000);

	    			}
	             //Uninstall app preset
	             try {
	 				Finalread.readStringFromExcel();
	 			} catch (IOException e1) {

	 				e1.printStackTrace();
	 			}
	             String Appname =Finalread.Apppresetappname;
	             wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtSearchPendingUpdates']"))).sendKeys(Appname);
	             Thread.sleep(3000);
	             WebElement app = driver.findElement(By.xpath("//td[@aria-label='Column "+Appname+"']"));

	 			String colIndex = app.getAttribute("aria-colindex");
	 			System.out.println(colIndex);
	 			Thread.sleep(3000);
	 			WebElement app3= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]"));

	 			Actions actions = new Actions(driver);
	 			actions.moveToElement(app3).click().perform();
	 			Thread.sleep(3000);
	 			WebElement UNinstallApp = driver.findElement(By.id("btnUninstallApplication"));
	 			UNinstallApp.click();
	 			Thread.sleep(3000);

	 			driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
	 			Thread.sleep(3000);
	 			WebElement app5 = driver.findElement(By.xpath("(//td/div[@class='dx-datagrid-text-content']//span[text()='"+Appname+"'])[1]"));
	 			Actions action1=new Actions(driver);
	 			action1.moveToElement(app5).click().perform();
	 			Thread.sleep(4000);

	 			WebElement close2 = driver.findElement(By.xpath("//div[@class='modal right rightPopup fade show']//div/div/button[@aria-label='Close']"));
	 			close2.click();

	 			Thread.sleep(2000);
	 			WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colIndex+"]")); 
	 			String id2=app6.getAttribute("data-productid");



	 			try {

	 				WebDriverWait wait2 = new WebDriverWait( driver, Duration.ofMinutes((long)(15))); // Waiting for a maximum of 10 seconds
	 				String condition1=  "//tr[td[@class='prod-"+id2+"'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
	 				System.out.println(condition1);
	 				String condition2=  "//tr[td[text()='Failed'] and td/span[1][text()='"+Window+"']]/td["+colIndex+"]";
	 				System.out.println(condition2);


	 				wait2.until(ExpectedConditions.or(
	 						ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
	 						ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))
	 						));

	 				System.out.println("Condition met");


	 				if (driver.findElements(By.xpath(condition1)).size() > 0) {
	 					System.out.println("Condition 1 met");
	 					System.out.println("App Uninstall");


	 				} else if (driver.findElements(By.xpath(condition2)).size() > 0) {
	 					System.out.println("Condition 2 met");

	 					WebElement fails= driver.findElement(By.xpath(condition2));
	 					fails.click(); 
	 					Thread.sleep(3000);
	 					WebElement failtext = driver.findElement(By.id("spnFailedReason"));
	 					String failedReason = failtext.getText();
	 					System.out.print(failedReason);
	 					Thread.sleep(3000);

	 					// Add version match in file

	 					WebElement clear = driver.findElement(By.xpath("//div/input[@value='CLEAR All FAILED']"));
	 					clear.click();
	 					Thread.sleep(3000);

	 					driver.navigate().refresh();
	 					Thread.sleep(3000);


	 				} else {
	 					System.out.println("Neither condition met");

	 				}

	 			} catch (TimeoutException es) {
	 				System.out.println("Condition not met: Timeout occurred");

	 			}
	
	}

}
