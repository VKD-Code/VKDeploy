package DeploySmokeTestOnChrome;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClasses.SetupDriver;

public class TC0001a_Visiallpages extends SetupDriver{

    @Test(description = "Visit All Deploy Pages.")
    public  void visitpages() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
       
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.navigate().refresh();

        try {
            
           

            // Main Pages Navigation
            JavascriptExecutor js = (JavascriptExecutor) driver;
            long navigationStart = (Long) js.executeScript("return window.performance.timing.navigationStart;");
            long loadEventEnd = (Long) js.executeScript("return window.performance.timing.loadEventEnd;");
            long pageLoadTime = loadEventEnd - navigationStart;
            long totalTimeinMS = (long) (pageLoadTime);
            Thread.sleep(3000);
            

            try {
                List<WebElement> pages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[@id='dvDeploySidebar']//li/a")));

                for (int i = 0; i < pages.size(); i++) {
                    pages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("//div[@id='dvDeploySidebar']//li/a")));

                    WebElement pageElement = pages.get(i);
                    String pageName = "";

                    try {
                        pageName = pageElement.getText();
                        System.out.println("Visiting page: " + pageName);

                        pageElement.click();
                        Thread.sleep(2000);

                        wait.until(ExpectedConditions.or(
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='controlGridMainDiv']")),
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnImgSetupCancel']")),
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aMDMSetupBackButton']")),
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='aAVSetupBackButton']")),
                                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='btnLoadDashboardData']"))
                        ));

                        System.out.println(pageName + " Page Load Time: " + totalTimeinMS + " ms");

                        String[] buttons = {
                                "//button[@id='btnImgSetupCancel']",
                                "//a[@id='aMDMSetupBackButton']",
                                "//a[@id='aAVSetupBackButton']",
                                "//button[@id='btnLoadDashboardData']"
                        };

                        for (String buttonXPath : buttons) {
                            try {
                                List<WebElement> elements = driver.findElements(By.xpath(buttonXPath));
                                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                                    elements.get(0).click();
                                    Thread.sleep(1000);
                                    break;
                                }
                            } catch (Exception e) {
                                softAssert.fail("Error handling button: " + buttonXPath + ". " + e.getMessage());
                                continue;
                            }
                        }

                    } catch (StaleElementReferenceException e) {
                        System.out.println("Stale element encountered for page: " + pageName);
                        i--;
                        continue;
                    } catch (Exception e) {
                        System.out.println("Error processing page " + pageName + ": " + e.getMessage());
                        softAssert.fail("Error processing page " + pageName + ": " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("Error in visitMainPages: " + e.getMessage());
                softAssert.fail("Error in visitMainPages: " + e.getMessage());
            }

            // Analytics Page
            WebElement analytics = driver.findElement(By.xpath("//a[@id='navAnalytics']"));
    		String pgana = analytics.getText();
    		analytics.click();

    		System.out.println(pgana + " Page Load Time: " + totalTimeinMS + " ms");

    		// Wait for the main menu to load
    		List<WebElement> analyticsPages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='navFDMainMenu']//li")));

    		for (int i = 0; i < analyticsPages.size(); i++) {
    			// Re-fetch the list of analytics pages to avoid stale references
    			analyticsPages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='navFDMainMenu']//li")));
    			WebElement anapage = analyticsPages.get(i);
    			String anpagename = anapage.getText();

    			// Click the page and handle dropdown
    			try {
    				anapage.click();
    			}
    			catch (StaleElementReferenceException e)
    			{
    				analyticsPages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='navFDMainMenu']//li")));
    				anapage = analyticsPages.get(i);
    				anapage.click();
    			}
    			boolean isDropdownVisible = driver.findElements(By.xpath("//div[@class='dropdown-menu customDropdownNav show']")).size() > 0;


    			if (isDropdownVisible || driver.findElements(By.xpath("//div[@class='dropdown-menu customDropdownNav show']")).size() > 0) {
    				List<WebElement> allApppages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='dropdown-menu customDropdownNav show']//a")));

    				for (int j = 0; j < allApppages.size(); j++) {
    					try {
    						// Re-fetch the dropdown menu links to avoid stale references
    						allApppages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='dropdown-menu customDropdownNav show']//a")));
    						WebElement allpage = allApppages.get(j);

    						String pageName = allpage.getText(); // Get the text before clicking
    						allpage.click();

    						// Handle the "Cancel" button if present
    						List<WebElement> cancelButtons = driver.findElements(By.xpath("//div[@id='dvBSpopup']//button[text()='CANCEL']"));
    						if (!cancelButtons.isEmpty() && cancelButtons.get(0).isDisplayed()) {
    							Thread.sleep(2000);
    							wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='dvBSpopup']//button[text()='CANCEL']")));
    							Thread.sleep(3000);

   							cancelButtons.get(0).click();
    						}

    						// Wait for the main grid to load
    						String vis = "//div[@id='controlGridMainDiv']";
    						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(vis)));

    						System.out.println(pageName + " Page Load Time: " + totalTimeinMS + " ms");

    						// Click dropdown header after each iteration to make the dropdown menu visible again
    						try {
    							anapage.click();
    						}
    						catch (StaleElementReferenceException e)
    						{
    							analyticsPages = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@id='navFDMainMenu']//li")));
    							anapage = analyticsPages.get(i);
    							anapage.click();
    						}

    					} catch (Exception e) {
    						System.out.println("Error interacting with an app page.");
    						e.printStackTrace();
    						softAssert.fail("Error interacting with page. "+ e.getMessage());
    					}
    				}
    			} else {
    				System.out.println(anpagename + " Page Load Time: " + totalTimeinMS + " ms");
    			}    		}

        } catch (Exception e) {
            System.out.println("Test failed with error: " + e.getMessage());
            e.printStackTrace();
            softAssert.fail("Test failed with error: " + e.getMessage());
        }
        softAssert.assertAll();
    }
}
