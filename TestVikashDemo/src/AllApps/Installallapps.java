package AllApps;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.Finalread;
import BaseClasses.Listener;
import BaseClasses.SetupDriver;


public class Installallapps extends Listener{
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static String Window ;
	List<String> apps = new ArrayList<>();
	@Test
	public void InstallAndUninstall() throws InterruptedException {
		
		WebDriver driver=new ChromeDriver();


		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		 Window=Finalread.Window1;
		try {
			Finalread.readStringFromExcel(); // Read initial data once
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		//driver.get(Finalread.URL1); // Navigate to URL
		driver.get("https://deployqa.deepfreeze.com/");

		// Enter email
		WebElement emailField = driver.findElement(By.xpath("//input[@id='txtLoginEmail']"));
		emailField.sendKeys(Finalread.Email1);

		// Enter password
		WebElement passwordField = driver.findElement(By.xpath("//input[@id='Password']"));
		passwordField.sendKeys(Finalread.Password1);

		// Wait for the Sign In button to be clickable
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Sign in']"))).click();

		// Wait for the next page to load
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Grid Options']"))).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkPendingUpdateSwitch' and @checked='checked']]"))).click();
		driver.navigate().refresh(); // Refresh to load updated data
		Thread.sleep(5000);

		WebElement firstapp = driver.findElement(By.xpath("(//tr/td[@aria-label='Column Google Chrome'])[1]"));
		Actions a=new Actions(driver);
		a.moveToElement(firstapp).build().perform();
		String id = firstapp.getAttribute("aria-colindex");

		int id2 = Integer.parseInt(id);
		System.out.println(id2);

		WebElement lastapp = driver.findElement(By.xpath("(//tr/td[@aria-label='Column Zoom'])[1]"));
		a.moveToElement(lastapp).build().perform();
		String Lid = lastapp.getAttribute("aria-colindex");

		int Lid2 = Integer.parseInt(Lid);

		System.out.println(Lid2);
driver.navigate().refresh();
Thread.sleep(5000);

		for (int i =id2; i < 25+1; i++) {

			WebElement app=driver.findElement(By.xpath("(//div[@id='dvPendingUpdateList']//table//tr/td["+i+"]//div//span)[1]"));
			Actions actions = new Actions(driver);
			actions.moveToElement(app).build().perform();
			String appname=app.getAttribute("title");
			apps.add(appname);
			System.out.println(appname);
			Thread.sleep(3000);
		}
		driver.navigate().refresh();
		
		for(String app:apps){
			prcessapp(app);
		}
	}
		@Test
	public void prcessapp(String app) throws InterruptedException {
	
		System.out.println(app);
		
			if(app.equalsIgnoreCase("Fusion360")){
				System.out.println("can't install this application");
				
			}else {
				Thread.sleep(5000);
			
				WebElement app5 = driver.findElement(By.xpath("//div[@id='dvPendingApplicationsGrid']//td[@aria-label='Column "+app+"']"));
				Actions a=new Actions(driver);
				a.moveToElement(app5).build().perform();
				String i = app5.getAttribute("aria-colindex");
		        Thread.sleep(2000);
				a.moveToElement(app5).click().perform();
				Thread.sleep(4000);
				WebElement currerntversion = driver.findElement(By.id("spnCurrentVersion"));
				String Expected = currerntversion.getText();
				System.out.println("Expected version:"+Expected);
				Thread.sleep(3000);

				WebElement close2 = driver.findElement(By.xpath("//div[@class='modal right rightPopup fade show']//div/div/button[@aria-label='Close']"));
				close2.click();
				Thread.sleep(5000);

				WebElement app3= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+i+"]"));

				String currentresult = app3.getText();
				System.out.println("cuurrent result:"+currentresult);


				if(currentresult.equals(Expected)){

					System.out.println(app+" app already install");



				}else if(currentresult.equals("Failed")){



					System.out.println(app+" app already failed");


				}else {


					WebElement app4= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+i+"]"));
					Actions actions1 = new Actions(driver);
					actions1.moveToElement(app4).click().perform();
					Thread.sleep(3000);
					
					WebElement installApp = driver.findElement(By.id("btnInstallApplication"));
		try {
					installApp.click();
					Thread.sleep(5000);
					driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
					Thread.sleep(3000);

					WebElement app6= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+i+"]")); 
					String pdID=app6.getAttribute("data-productid");
					String condition1="//tr[td[@class='prod-"+pdID+" textColorBlack'] and td/span[1][text()='"+Window+"']]/td["+i+"]";
					String condition2="//tr[td[@class='prod-"+pdID+" textColorBlack failed'] and td/span[1][text()='"+Window+"']]/td["+i+"]";
					String condition3="//tr[td[@class='prod-"+pdID+" outdated'] and td/span[1][text()='"+Window+"']]/td["+i+"]";


					WebDriverWait	wait1 = new WebDriverWait( driver, Duration.ofMinutes((long)(60)));

					wait1.until(ExpectedConditions.or(
							ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
							ExpectedConditions.presenceOfElementLocated(By.xpath(condition2)),
							ExpectedConditions.presenceOfElementLocated(By.xpath(condition3))
							));


					if (driver.findElements(By.xpath(condition1)).size() > 0) {
						System.out.println("Condition 1 met");


						WebElement Actual= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+i+"]"));
						String Result1=Actual.getText(); 
						System.out.println("This is actual result:"+Result1);


						// VERSION MATCH
						Thread.sleep(4000);


						if (Result1 != null && Expected != null && Result1.equals(Expected)) {
							System.out.println("version correct");

							Thread.sleep(5000);


							WebElement app1= driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+i+"]"));

							Actions action1 = new Actions(driver);
							action1.moveToElement(app1).click().perform();
							Thread.sleep(3000);
							try{
								WebElement UNinstallApp = driver.findElement(By.id("btnUninstallApplication"));
								UNinstallApp.click();
								Thread.sleep(3000);

								driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
								Thread.sleep(3000);
								WebDriverWait wait4 = new WebDriverWait( driver, Duration.ofMinutes((long)(5))); // Waiting for a maximum of 10 seconds
								String condition4=  "//tr[td[@class='prod-"+pdID+"'] and td/span[1][text()='"+Window+"']]/td["+i+"]";
								System.out.println(condition4);
								String condition5="//tr[td[@class='prod-"+pdID+" textColorBlack failed'] and td/span[1][text()='"+Window+"']]/td["+i+"]";
								System.out.println(condition5);


								wait4.until(ExpectedConditions.or(
										ExpectedConditions.presenceOfElementLocated(By.xpath(condition4)),
										ExpectedConditions.presenceOfElementLocated(By.xpath(condition5))
										));

								System.out.println("Condition met");


								if (driver.findElements(By.xpath(condition4)).size() > 0) {
									System.out.println("Condition 1 met");

									System.out.println("App Uninstall");



								} else if (driver.findElements(By.xpath(condition5)).size() > 0) {

									WebElement fails= driver.findElement(By.xpath(condition4));
									fails.click(); 
									Thread.sleep(3000);
									WebElement failtext = driver.findElement(By.id("spnFailedReason"));
									String failedReason = failtext.getText();
									System.out.print("Failed reason:"+failedReason);
									Thread.sleep(3000);

									// Add version match in file

									WebElement clear = driver.findElement(By.xpath("//div[@id='dvFailedAppPopup']//button[@class='close']"));
									clear.click();
									Thread.sleep(3000);
									System.out.println("App Uninstall fail");


				



								} 

							}catch(Exception  es){
								System.out.println("Unistall notsupport");
								


							}
						}else {
							System.out.println("version Incorrect");

				

						}
					}else if(driver.findElements(By.xpath(condition2)).size() > 0) {

						WebElement fails= driver.findElement(By.xpath(condition2));
						fails.click(); 
						Thread.sleep(3000);
						WebElement failtext = driver.findElement(By.id("spnFailedReason"));
						String failedReason = failtext.getText();
						System.out.print("Failed reason:"+failedReason);
						Thread.sleep(3000);

						// Add version match in file

						WebElement clear = driver.findElement(By.xpath("//div[@id='dvFailedAppPopup']//button[@class='close']"));
						clear.click();
						Thread.sleep(3000);
						System.out.println("App Uninstall fail");



					}else if(driver.findElements(By.xpath(condition3)).size() > 0) {
						Thread.sleep(3000);
						WebElement Actual= driver.findElement(By.xpath("//div[@id='dvPendingUpdateList']//tr[td/span[1][text()='"+Window+"']]/td["+i+"]"));
						String ActualResult=Actual.getText(); 
				
						System.out.println("App install with Oudated version"+ActualResult+"");
			




					}else {

						System.out.println("Condition not met during specific time");
	




					}




				}catch(Exception  es){
					System.out.println("Install button not visible");
					
	

				}
				}

			
			driver.navigate().refresh();
		}
	

		}
}
