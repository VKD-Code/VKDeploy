package AllApps;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.Finalread;
import BaseClasses.SetupDriver;



public class WingetAllApps  extends SetupDriver {
	public static WebDriverWait wait;

	@Test(priority=2, description = "Verify all winget apps can be added, installed, uninstalled and deleted one by one.")
	
	public void wingetapp() throws InterruptedException {
		SoftAssert softassert = new SoftAssert();

		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
		try {
			Finalread.readStringFromExcel(); // Read initial data once
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		driver.get(Finalread.URL1); // Navigate to URL

		// Enter email
		WebElement emailField = driver.findElement(By.xpath("//input[@id='txtLoginEmail']"));
		emailField.sendKeys(Finalread.Email1);

		// Enter password
		WebElement passwordField = driver.findElement(By.xpath("//input[@id='Password']"));
		passwordField.sendKeys(Finalread.Password1);

		// Wait for the Sign In button to be clickable
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Sign in']"))).click();
		
		System.out.println("Login successful");

		// Wait for the next page to load
		Thread.sleep(3000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='Grid Options']"))).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[input[@id='chkPendingUpdateSwitch' and @checked='checked']]"))).click();
		driver.navigate().refresh(); // Refresh to load updated data
		Thread.sleep(5000);
		WebElement index=driver.findElement(By.xpath("//div[@id='dvPendingApplicationsGrid']//tr/td[@aria-label='Column Winget Status']"));
		String colindex=index.getAttribute("aria-colindex");
		//System.out.println(colindex);

		WebElement index1=driver.findElement(By.xpath("(//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"])[1]"));
		String text=index1.getText();
		System.out.println(text);

		if(text.equals("v1.9")) {
			System.out.println("Winget is already installed, so no need to install.");
		}
		else {


			Thread.sleep(5000);
			driver.findElement(By.xpath("//tr[td/span[1][text()='"+Window+"']]/td["+colindex+"]")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//button[@id='btnInstallWinget']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//input[@id='txtWAInstallDisplayName']")).sendKeys(Window);
			driver.findElement(By.xpath("//input[@id='txtWAInstallUserName']")).sendKeys("Administrator");
			driver.findElement(By.xpath("//input[@id='txtWAInstallPassword']")).sendKeys("aloha");
			driver.findElement(By.xpath("//input[@id='installWingetPopupBtn']")).click();
			WebDriverWait  waits = new WebDriverWait( driver, Duration.ofMinutes((long)(40))); // Waiting for a maximum of 10 seconds
			String condition1=  "//tr[td[text()='v1.9'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
			//System.out.println(condition1);
			String condition2=  "//tr[td[text()='Failed'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
			//System.out.println(condition2);

			waits.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
					ExpectedConditions.presenceOfElementLocated(By.xpath(condition2))

					));

			if (driver.findElements(By.xpath(condition1)).size() > 0) {
				// System.out.println("Condition 1 met");
				System.out.println("winget  Install");
			}else if (driver.findElements(By.xpath(condition2)).size() > 0) {
				//System.out.println("Condition 2 met");
				System.out.println("winget failed to install");
				Assert.fail("winget failed to install");
				Thread.sleep(5000);
			}


		}


		driver.navigate().refresh();
		Thread.sleep(5000);
		// Access Winget Apps section
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Winget Apps']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='aAddWingetApps']"))).click();
		Thread.sleep(5000);

		// Click Next to proceed
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dvBsPopupEx_content']//button[text()='NEXT']"))).click();
		Thread.sleep(20000);

		// Get all rows from the table
		List<WebElement> tr = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("(//div[@id='dvWingetAppsGrid']//table)[2]//tr")));
		List<String> rowno = new ArrayList<>();
		for (WebElement row : tr) {
			String rowindex = row.getAttribute("aria-rowindex");
			rowno.add(rowindex);
		}

		int totalrow = rowno.size();

		Thread.sleep(3000);

		List<String> application = new ArrayList<>();
		for (int i = 1; i < totalrow - 1; i++) {
			WebElement row = driver.findElement(By.xpath("(//div[@id='dvWingetAppsGrid']//table)[2]//tr[" + i + "]"));
			Actions act = new Actions(driver);
			act.moveToElement(row).build().perform();

			WebElement app = driver.findElement(By.xpath("(//div[@id='dvWingetAppsGrid']//table)[2]//tr[" + i + "]//td[1]"));
			String apps = app.getText();
			application.add(apps);
//			System.out.println(apps);
		}
		int totalapps = application.size();
		System.out.println("Total apps found: "+totalapps);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='btnCloseBSPopupEx']"))).click();

		for (String app : application) {
			Reporter.log(app + "<br>");
			System.out.println(app);

			// Click to add application from Winget dialog
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Winget Apps']"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='aAddWingetApps']"))).click();
			Thread.sleep(3000);

			WebElement app1 = driver.findElement(By.xpath("//div[@id='dvWingetAppsGrid']//td[text()='" + app + "']"));
			Actions act1 = new Actions(driver);
			act1.moveToElement(app1).build().perform();
			Thread.sleep(2000);

			act1.moveToElement(app1).click().perform();
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='btnSaveWingetAppToGrid']"))).click();
			Thread.sleep(2000);

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='btnCloseBSPopupEx']"))).click();
			Thread.sleep(5000);
			driver.navigate().refresh();
			Thread.sleep(5000);

			String Element = "//div[@id='dvPendingUpdateList']//span[@title='Winget App']//ancestor::td[@aria-label='Column "+app+"']";

			// Wait for the app to appear in the Pending Updates list
			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofMinutes(1));
			wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Element)));
			if (driver.findElements(By.xpath(Element)).size() > 0) {
				System.out.println(app + "After adding from winget dialog app is visible on the grid.");

				WebElement application1 = driver.findElement(By.xpath("//div[@id='dvPendingUpdateList']//span[@title='Winget App']//ancestor::td[@aria-label='Column "+app+"']"));
				Actions act2 = new Actions(driver);
				act2.moveToElement(application1).build().perform();

				String index2 = application1.getAttribute("aria-colindex");
				//System.out.println(index2);
				Thread.sleep(1000);
				act2.moveToElement(application1).click().perform();
				Thread.sleep(5000);

				// Print version info
				WebElement currerntversion = driver.findElement(By.xpath("//span[@id='spnWACurrentVersion']"));
				String textContent = currerntversion.getText();
				System.out.println(app+": Expected version: "+textContent);
				
				Thread.sleep(3000);
				WebElement close2=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvWingetAppInfoActionToolbar']//button[@aria-label='Close']")));
				
				close2.click();
				Thread.sleep(5000);

				// Install winget app
				String cellLocator = "//tr[td/span[1][text()='" + Window + "']]/td[" + index2 + "]";
				WebElement app3 = driver.findElement(By.xpath(cellLocator));
				String pdID=app3.getAttribute("data-productid");
				//  System.out.println(pdID);
				Thread.sleep(2000);
				Actions actions1 = new Actions(driver);
				actions1.moveToElement(app3).click().perform();
				Thread.sleep(3000);

				WebElement installApp = driver.findElement(By.id("btnInstallApplication"));

				installApp.click();
				Thread.sleep(5000);

				driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
				Thread.sleep(5000);


				String condition1="//tr[td[@class='prod-"+pdID+" textColorBlack'] and td/span[1][text()='"+Window+"']]/td["+index2+"]";
				String condition2="//tr[td[@class='prod-"+pdID+" textColorBlack failed'] and td/span[1][text()='"+Window+"']]/td["+index2+"]";
				String conditions=  "//tr[td[@class='prod-"+pdID+" outdated'] and td/span[1][text()='"+Window+"']]/td["+colindex+"]";
				WebDriverWait 	wait2 = new WebDriverWait( driver, Duration.ofMinutes((long)(60)));

				wait2.until(ExpectedConditions.or(
						ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)),
						ExpectedConditions.presenceOfElementLocated(By.xpath(condition2)),
						ExpectedConditions.presenceOfElementLocated(By.xpath(conditions))
						));
				//System.out.println("Condition met");

				if (driver.findElements(By.xpath(condition1)).size() > 0) {

					System.out.println(app+": Installtion Pass.");
					WebElement Actual= driver.findElement(By.xpath("//div[@id='dvPendingUpdateList']//tr[td/span[1][text()='"+Window+"']]/td["+index2+"]"));
					String ActualResult=Actual.getText(); 
					System.out.println("Actual version after "+app+" app installation: "+ActualResult);
					Thread.sleep(3000);

					if(ActualResult.equals(textContent)) {
						System.out.println(app+" install with correct version.");	
						wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='dvPendingUpdateList']//tr[td/span[1][text()='"+Window+"']]/td["+index2+"]"))).click();
						Thread.sleep(5000);

						WebElement UNinstallApp1 = driver.findElement(By.id("btnUninstallApplication"));

						Actions actions = new Actions(driver);
						actions.moveToElement(UNinstallApp1).click().perform();
						Thread.sleep(5000);

						driver.findElement(By.xpath("//input[@id='btnConfirmCommonYes']")).click();
						Thread.sleep(3000);



						// Define the XPath conditions
						String condition3 = "//tr[td[@class='prod-"+pdID+"'] and td/span[1][text()='"+Window+"']]/td["+index2+"]";
						String condition4 = "//tr[td[@class='prod-"+pdID+" textColorBlack failed'] and td/span[1][text()='"+Window+"']]/td["+index2+"]";

						// Set up the WebDriverWait
						WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofMinutes(10));

						boolean conditionMet = false;
						int refreshCount = 0;
						int maxRetries = 6; // Retry up to 6 times (max 30 minutes)

						while (!conditionMet && refreshCount < maxRetries) {
							try {
								// Wait until either condition1 or condition2 is present
								wait3.until(ExpectedConditions.or(
										ExpectedConditions.presenceOfElementLocated(By.xpath(condition3)),
										ExpectedConditions.presenceOfElementLocated(By.xpath(condition4))
										));

								//System.out.println("Condition met");

								// Check if condition1 is present
								if (driver.findElements(By.xpath(condition3)).size() > 0) {
									//System.out.println("Condition 1 met");
									// Perform action for condition 1
									conditionMet = true;
								} 
								// Check if condition2 is present
								else if (driver.findElements(By.xpath(condition4)).size() > 0) {
									//System.out.println("Condition 2 met");
									// Perform action for condition 2
									conditionMet = true;
								}

							} catch (TimeoutException e) {
								// Timeout if neither condition is met within the time frame
								//System.out.println("Conditions not met, refreshing the page...");
								driver.navigate().refresh();  // Refresh the page
								Thread.sleep(5000); 
								refreshCount++; // Increment retry count 
							}
						}

						if (conditionMet) {

							//System.out.println("Condition successfully met.");

							WebDriverWait wait4 = new WebDriverWait( driver, Duration.ofMinutes((long)(5))); // Waiting for a maximum of 10 seconds
							String condition5=  "//tr[td[@class='prod-"+pdID+"'] and td/span[1][text()='"+Window+"']]/td["+index2+"]";
							//System.out.println(condition5);
							String condition6=  "//tr[td[@class='prod-"+pdID+" textColorBlack failed'] and td/span[1][text()='"+Window+"']]/td["+index2+"]";
							//System.out.println(condition6);


							wait4.until(ExpectedConditions.or(
									ExpectedConditions.presenceOfElementLocated(By.xpath(condition5)),
									ExpectedConditions.presenceOfElementLocated(By.xpath(condition6))
									));

							//	System.out.println("Condition met");



							if (driver.findElements(By.xpath(condition3)).size() > 0) {
								//System.out.println("Condition 1 met");
								System.out.println(app+ " App Uninstalled.");
								Thread.sleep(3000);
								WebElement application2 = driver.findElement(By.xpath("//div[@id='dvPendingUpdateList']//span[@title='Winget App']//ancestor::td[@aria-label='Column "+app+"']"));
								actions.moveToElement(application2).click().perform();
								Thread.sleep(2000);

								wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dvWingetAppInfoActionToolbar']//input[@value='DELETE']"))).click();
								Thread.sleep(5000);
								wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();

								Thread.sleep(5000);
								String Element1="//div[@id='dvPendingUpdateList']//td[@aria-label='Column "+app+"']";
								WebDriverWait wait5 = new WebDriverWait( driver, Duration.ofMinutes((long)(1)));
								wait5.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Element)));
								if (driver.findElements(By.xpath(Element1)).size() == 0) {
									System.out.println(app+" app is deleted");
									//Reporter.log(""+app+" app is  deleted.<br>");
									Thread.sleep(5000);

								}else {

									System.out.println(app+" app is not deleted");

									softassert.fail(app+" app is not deleted");

								}
							}	else if (driver.findElements(By.xpath(condition4)).size() > 0) {
								driver.navigate().refresh();
								Thread.sleep(5000);
								System.out.println(app+ " App Uninstall failed.");


								softassert.fail(app+" App Uninstall failed.");

							}


						} else {
							System.out.println("Max retries reached, condition not found.");
						}



					}else {
						System.out.println(app+" install with incorrect version");	
						softassert.fail(app+" install with incorrect version");
					}


				}else if(driver.findElements(By.xpath(condition2)).size() > 0) {


						System.out.println(app+ " App Uninstall fail");


					softassert.fail(app+" App Uninstall fail");

				}else if(driver.findElements(By.xpath(conditions)).size() > 0) {
					Thread.sleep(3000);
					WebElement Actual= driver.findElement(By.xpath("//div[@id='dvPendingUpdateList']//tr[td/span[1][text()='"+Window+"']]/td["+index2+"]"));
					String ActualResult=Actual.getText(); 
					//					System.out.println("This is Outdated version: "+ActualResult);
										System.out.println(app+ " install with Oudated version: "+ActualResult);
					softassert.fail(app+ " App install with Oudated version: "+ActualResult);




				}else {

					System.out.println(app+ " is not installed on specified time.");
					softassert.fail(app+" is not installed on specified time.");




				}



			}else {

				System.out.println(application+" is not visible");
				softassert.fail(application+" is not visible");
			}

			driver.navigate().refresh();
			Thread.sleep(5000);
		}

		softassert.assertAll();
	}

}