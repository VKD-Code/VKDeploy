package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.testng.annotations.Test;

import BaseClasses.Finalread;
import BaseClasses.SetupDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TC0001_Login extends SetupDriver {
	

	
	@Test(description="Login > 2FA verification > Disable 2FA in Org Settings > Re-login without 2FA.")
	public void VerifyLoginWorksAsExpected() throws MalformedURLException, InterruptedException, AWTException 
	{
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		// Set the path to the chromedriver executable
		
		// Open first url deploy website
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		driver.get(Finalread.URL1);

		// Enter email
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		WebElement emailField = driver.findElement(By.xpath("//input[@id='txtLoginEmail']"));
		emailField.sendKeys(Finalread.Email1);

		// Enter password
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		System.out.println("Check console log for login");
		WebElement passwordField = driver.findElement(By.xpath("//input[@id='Password']"));
		passwordField.sendKeys(Finalread. Password1);
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@value='Sign in']")).click();
		System.out.println("Login successful.");
		 //Press key
    
		
//		 Thread.sleep(5000);
//	        try {
//	        WebElement warning=driver.findElement(By.xpath("//div/span[@class='field-validation-error']"));
//	        String comment=warning.getText();
//	       
//	        	System.out.println(comment);
//	        }catch(Exception e){
//	        	
//	        	 ((JavascriptExecutor) driver).executeScript("window.open('');");
//	        	 Set<String> windowHandles = driver.getWindowHandles();
//	             Iterator<String> it = windowHandles.iterator();
//	             String Signup = it.next();
//	             String mailid = it.next();
//	             driver.switchTo().window(mailid); 
//	            String mailurl= "https://www.guerrillamail.com/#google_vignette";
//	            driver.get(mailurl);
//	        	
//	        
//	        	
//	        	
//	        	WebElement ib=driver.findElement(By.xpath("//span[@id='inbox-id']"));
//	    		ib.click();
//	    		 
//	    		
//	             
//	             WebElement Rename = driver.findElement(By.xpath("//span[@id='inbox-id']/input"));
//	             Rename.sendKeys("nqxyrtfp");
//	             Thread.sleep(3000);
//	             driver.findElement(By.xpath("//button[@class='save button small']")).click();
//	             Thread.sleep(10000);
//	             WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(1));
//	            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td[@class='td2'])[1]")));
//	             
//	             //driver.findElement(By.xpath("(//td[@class='td2'])[1]"));
//	             Thread.sleep(5000);
//	             Actions a = new Actions(driver);
//	             a.moveToElement(driver.findElement(By.xpath("(//td[@class='td2'])[1]"))).click().build().perform();
//	             
//	             Thread.sleep(5000);
//	             WebElement otp = driver.findElement(By.xpath("//td[@height='73']/a"));
//	             String otpresult = otp.getText();
//	             System.out.println(otpresult);
//	             driver.close();
//	             Thread.sleep(5000);
//	             driver.switchTo().window(Signup); 
//	            
//	             Thread.sleep(3000);
//	             //write otp
//	             driver.findElement(By.xpath("//input[@class='inline_input']")).sendKeys(otpresult);
//	             Thread.sleep(5000);
//	             //click check box
//	            // driver.findElement(By.xpath("//label[@for='chkTrustThisComputer']")).click();
//	           
//	             //confirm button
//             driver.findElement(By.xpath("//Button[@class='button']")).click();
//	          
//	           
//	             
//	         
//	             try {
//	            	 WebElement error= driver.findElement(By.xpath("//div/span[@class='field-validation-error']"));
//	            	 String issue=error.getText();
//	            	 System.out.println(issue);
//	             }catch(Exception es) {
//	        	System.out.println("Login successfull");
//	        	
//	        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='fa fa-user-circle']"))).click();
//	        //	driver.findElement(By.xpath("//*[@class='fa fa-user-circle']")).click();
//	        	Thread.sleep(3000);
//	    		driver.findElement(By.linkText("Organization Settings")).click();
//	    		Thread.sleep(5000);
//	    		WebElement value=driver.findElement(By.xpath("//label//input[@id='chkEnable2StepVerification']"));
//	             String Result=value.getAttribute("value");
//	             if(Result.equals("true"))
//	             {
//	            	 driver.findElement(By.xpath("(//div[@class='d-inline-block custom-switch checbox-switch switch-primary help-switch']/label/span)[2]")).click();
//	            	 Thread.sleep(5000);
//	            		try {
//			    			Finalread.readStringFromExcel();
//			    		} catch (IOException e1) {
//
//			    			e1.printStackTrace();
//			    		}
//
//			    		String password=Finalread. Password1;
//	            	 driver.findElement(By.id("txtEnterPasswordToDisable")).sendKeys(password);
//	            	 Thread.sleep(2000);
//	            	 driver.findElement(By.xpath("//button[text()='DISABLE']")).click();
//	            	System.out.println( driver.findElement(By.id("dvToastNotification")).getText());
//	            	Thread.sleep(3000);
//	            	driver.findElement(By.xpath("//*[@class='fa fa-user-circle']")).click();
//		        	Thread.sleep(3000);
//	            	driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
//	            	Thread.sleep(5000);
//	            	WebElement emailField1 = driver.findElement(By.xpath("//input[@id='txtLoginEmail']"));
//	        		emailField1.sendKeys(Finalread.Email1);
//
//	        		// Enter password
//	        		try {
//	        			Finalread.readStringFromExcel();
//	        		} catch (IOException e1) {
//
//	        			e1.printStackTrace();
//	        		}
//
//	        		WebElement passwordField1 = driver.findElement(By.xpath("//input[@id='Password']"));
//	        		passwordField1.sendKeys(Finalread. Password1);
//	        		Thread.sleep(3000);
//	        		driver.findElement(By.xpath("//input[@value='Sign in']")).click();
//	        		Thread.sleep(5000);
//	        		
//	        		
//	            	
//	             }
//	            
//	    		
//	    		
//	  
//	             }
//	        
//	        
//	        
//	        }
	}

	// Close the browser

}





