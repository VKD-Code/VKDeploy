package DeploySmokeTestOnChrome;

import java.io.IOException;
import java.time.Duration;

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

public class TC0043_Inventoryrefresh  extends SetupDriver {
	private static WebDriverWait wait ;
	@Test(description = "Make sure that details should be refreshed after clicking on 'Refresh details' button from the inventory page.")

	public void Remoteinstall() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		WebDriverWait wait1=new WebDriverWait(driver, Duration.ofMinutes(1));
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	
			Thread.sleep(5000);
			driver.findElement(By.xpath("//a[@data-target='#dvFDInvertory']")).click();
			
			WebDriverWait wt=new WebDriverWait(driver, Duration.ofMinutes(1));
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class,'gridOptionsCustomBtn')]"))).click();
			wt.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@id='chkToggelGridCheckboxes']]"))).click();

			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[1]/td/span[text()='"+Window+"'])[2]"))).click();
			
			WebElement refresh=driver.findElement(By.xpath("//input[@id='btnRefreshInvDetails']"));
			Actions action=new Actions(driver);
			Thread.sleep(3000);
			action.moveToElement(refresh).click().perform();
			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnConfirmCommonYes']"))).click();

			//task status
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@id='liTaskHistory']"))).click();
		Thread.sleep(5000);
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
			System.out.println("Inventory page get refresh");
			
		}	else if(driver.findElements(By.xpath(exp2)).size() > 0) {
			
			System.out.println("Task not completed");
			System.out.println("Inventory page not  get refresh");
			softAssert.fail("Inventory page not  get refresh");
		}
		softAssert.assertAll();
		}
}
