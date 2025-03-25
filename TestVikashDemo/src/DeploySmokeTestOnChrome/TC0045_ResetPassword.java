package DeploySmokeTestOnChrome;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;

public class TC0045_ResetPassword  extends SetupDriver{
@Test(description="Verify reset password wroks properly.")
	public  void Resetpassword() throws IOException, InterruptedException, AWTException {
	SoftAssert softAssert = new SoftAssert();
	
		// TODO Auto-generated method stub
		
		WebDriverWait wait = new WebDriverWait( driver, Duration.ofMinutes((long)(2)));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@class='fa fa-user-circle']")).click();
    	Thread.sleep(3000);
    	
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//a[text()='User Management'])[1]")).click();
		Thread.sleep(5000);
		//File scr=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//FileUtils.copyFile(scr,new File("D:\\Screenshot.png"));
		String user1 = "ue966z";
		driver.findElement(By.cssSelector("input#txtUserDxSearch")).sendKeys(user1);
		Thread.sleep(1000);
		// Select user
		driver.findElement(By.xpath("//*[text()='Deploy user']")).click();
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("button#btnEditManageUser")).click();
		Thread.sleep(2000);
		// Click on reset link button
		driver.findElement(By.cssSelector("button#btnUserManagePasswordReset")).click();
		WebElement toastMessage = driver.findElement(By.cssSelector("div#dvToastNotification"));

		System.out.println(toastMessage.getText());
		Thread.sleep(5000);

		String url="https://www.guerrillamail.com/inbox";
		driver.navigate().to(url);
		Thread.sleep(5000);
		WebElement ib=driver.findElement(By.xpath("//span[@id='inbox-id']"));
		ib.click();
		
         
         WebElement Rename = driver.findElement(By.xpath("//span[@id='inbox-id']/input"));
         Rename.sendKeys("nqxyrtfp");
         Thread.sleep(3000);
		
		driver.findElement(By.xpath("//button[@class='save button small']")).click();
		Thread.sleep(5000);
	
//		WebElement mail=driver.findElement(By.xpath("(//td[@class='td2'])[1]"));
		Thread.sleep(5000);
		Actions a=new Actions(driver);
//		a.moveToElement(mail).click().build().perform();
		
		Thread.sleep(10000);
		 WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMinutes(1));
		 wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td[@class='td2'])[1]")));
         
         
         Thread.sleep(5000);
       
         a.moveToElement(driver.findElement(By.xpath("(//td[@class='td2'])[1]"))).click().build().perform();
	        Thread.sleep(5000);
	       
	       
		WebElement link=driver.findElement(By.xpath("//a[contains(@href,'ResetPassword')]"));
		link.click();

		Thread.sleep(5000);
		 Set<String> windowHandles = driver.getWindowHandles();
	        Iterator<String> it = windowHandles.iterator();
	        String Signup = it.next();
	        String mailid = it.next();
	        driver.switchTo().window(mailid);

		
		Thread.sleep(5000);
		String password1="Aloha@123";
		driver.findElement(By.id("txtUserpwd")).sendKeys(password1);
		driver.findElement(By.id("ConfirmPassword")).sendKeys(password1);
		driver.findElement(By.xpath("//input[@value='RESET PASSWORD']")).click();
		Thread.sleep(5000);

		System.out.println("User logged in successfully with new password");
		
	
        }
		
		

	}


