package DeploySmokeTestOnChrome;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;

public class TC0044_MsiDownload  extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = " Make sure MSI details should be downloaded from actiontoolbar on the Inventory page.")

	public void msiDownload() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	;
	Thread.sleep(5000);
	driver.findElement(By.cssSelector("a#navControlGrid")).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-target='#dvFDInvertory']"))).click();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[1]/td/span[text()='"+Window+"'])[2]"))).click();
			
			Thread.sleep(5000);
			WebElement Msi=driver.findElement(By.xpath("//input[@id='btnGetMSSysInfo']"));
			Actions action=new Actions(driver);
			Thread.sleep(3000);
			action.moveToElement(Msi).click().perform();
			Thread.sleep(5000);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();
			Thread.sleep(3000);
			String exp="//a[@id='aMsSysInfoFile']";
			wait = new WebDriverWait( driver, Duration.ofMinutes(5));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(exp)));
			
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aMsSysInfoFile']"))).click();
				Thread.sleep(Duration.ofMinutes(1).toMillis());
				driver.findElement(By.xpath("//div[@id='dvGetMSSysInfoPopup']//input[@value='CANCEL']")).click();
				// Assuming default download directory for a Windows user
				String downloadFolderPath = System.getProperty("user.home") + "\\Downloads";
				File downloadFolder = new File(downloadFolderPath);

				// List all files in the download folder
				File[] files = downloadFolder.listFiles();

				if (files != null && files.length > 0) {
					// Example: Find the most recent file
					File latestFile = Arrays.stream(files)
							.filter(file -> file.isFile() && file.getName().endsWith(".zip")) // Filter by .zip extension
							.max(Comparator.comparingLong(File::lastModified)) // Get the file with the latest modification time
							.orElse(null);

					if (latestFile != null) {
						// Print the name of the latest .exe file found
						System.out.println("Latest .zip  file: " + latestFile.getName());
						// Now you can do something with latestFile, like copying or moving it
					} else {
						System.out.println("No .zip  files found in the download folder.");
						softAssert.fail("No .zip  files found in the download folder.");
					}
				} else {
					System.out.println("Download folder is empty or does not exist.");
					softAssert.fail("Download folder is empty or does not exist.");
				}
				softAssert.assertAll();
	
		}}

