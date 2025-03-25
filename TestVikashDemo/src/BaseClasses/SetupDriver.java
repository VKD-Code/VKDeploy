////package BaseClasses;
////
////import java.time.Duration;
////import java.util.HashMap;
////import org.openqa.selenium.WebDriver;
////import org.openqa.selenium.chrome.ChromeOptions;
////import org.openqa.selenium.edge.EdgeOptions;
////import org.openqa.selenium.firefox.FirefoxOptions;
////import org.openqa.selenium.safari.SafariOptions;
////import org.testng.ITestContext;
////import org.testng.annotations.AfterTest;
////import org.testng.annotations.BeforeClass;
////import org.testng.annotations.BeforeTest;
////import org.testng.annotations.Parameters;
////
////public class SetupDriver {
////
////    protected WebDriver driver;
////
////    @BeforeTest
////    @Parameters("browser")
////    public void setUpDriver(String browser, ITestContext context) {
////        if (browser.equalsIgnoreCase("chrome")) {
////            String downloadPath = "Download";
////            HashMap<String, Object> chromePrefs = new HashMap<>();
////            chromePrefs.put("profile.default_content_settings.popups", 0);
////            chromePrefs.put("download.default_directory", downloadPath);
////            chromePrefs.put("safebrowsing.enabled", "false");
////
////            ChromeOptions options = new ChromeOptions();
////            options.setExperimentalOption("prefs", chromePrefs);
////            driver = new org.openqa.selenium.chrome.ChromeDriver(options);  // Local ChromeDriver
////        } 
////        else if (browser.equalsIgnoreCase("firefox")) {
////            FirefoxOptions options = new FirefoxOptions();
////            driver = new org.openqa.selenium.firefox.FirefoxDriver(options);  // Local FirefoxDriver
////        } 
////        else if (browser.equalsIgnoreCase("msedge")) {
////            EdgeOptions options = new EdgeOptions();
////            driver = new org.openqa.selenium.edge.EdgeDriver(options);  // Local EdgeDriver
////        } 
////        else if(browser.equalsIgnoreCase("safari")) {
////            SafariOptions options = new SafariOptions();
////            driver = new org.openqa.selenium.safari.SafariDriver(options);  // Local SafariDriver
////        }
////
////        driver.manage().window().maximize();
////        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
////        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
////        context.setAttribute("driver", driver);  // Store driver in context for access in test methods
////    }
////
////    @BeforeClass
////    public void setDriver(ITestContext context) {
////        driver = (WebDriver) context.getAttribute("driver");  // Retrieve driver from context
////    }
////
////    @AfterTest
////    public void tearDown() {
////        if (driver != null) {
////            driver.quit();
////        }
////    }
////}
//
//package BaseClasses;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.time.Duration;
//import java.net.MalformedURLException;
//import java.util.HashMap;
//import java.util.concurrent.TimeUnit;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.edge.EdgeOptions;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.safari.SafariOptions;
//import org.testng.ITestContext;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Parameters;
//import org.openqa.selenium.Capabilities;
//
//public class SetupDriver {
//
//	protected WebDriver driver;
//
//	@BeforeTest
//	@Parameters("browser")
//	public void setUpDriver(String browser, ITestContext context) throws MalformedURLException, URISyntaxException {
//		// Use URI to create a URL object to avoid deprecation warning
//		URI gridUri = new URI("http://192.168.30.82:4444/wd/hub"); // Selenium Grid Hub URL
//		URL gridUrl = gridUri.toURL();  // Convert URI to URL
//
//		if (browser.equalsIgnoreCase("chrome")) {
//			String downloadPath = "Download";
//			HashMap<String, Object> chromePrefs = new HashMap<>();
//			chromePrefs.put("profile.default_content_settings.popups", 0);
//			chromePrefs.put("download.default_directory", downloadPath);
//			chromePrefs.put("safebrowsing.enabled", "false");
//
//			ChromeOptions options = new ChromeOptions();
//			options.setExperimentalOption("prefs", chromePrefs);
//			driver = new RemoteWebDriver(gridUrl, options);  // Use RemoteWebDriver for Grid
//		} 
//		else if (browser.equalsIgnoreCase("firefox")) {
//			FirefoxOptions options = new FirefoxOptions();
//			driver = new RemoteWebDriver(gridUrl, options);  // Use RemoteWebDriver for Grid
//		} 
//		else if (browser.equalsIgnoreCase("msedge")) {
//			EdgeOptions options = new EdgeOptions();
//			driver = new RemoteWebDriver(gridUrl, options);  // Use RemoteWebDriver for Grid
//		}
//
//		else if(browser.equalsIgnoreCase("safari")) {
//			SafariOptions options=new SafariOptions();
//			driver =new RemoteWebDriver(gridUrl, options);
//		}
//
//		driver.manage().window().maximize();
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//		context.setAttribute("driver", driver);  // Store driver in context for access in test methods
//
//		// Retrieve Capabilities and Session Information
//		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
//		String platformName = caps.getPlatformName().toString();  // Platform (e.g., macOS, WINDOWS)
//		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();  // Session ID (session-specific information)
//		String nodeIp = (String) caps.getCapability("webdriver.remote.sessionid"); // Example of session-related info
//
//		// Log or store platform and session information in the test context or reporting
//		System.out.println("Platform: " + platformName);
//		System.out.println("Session ID: " + sessionId);
//		System.out.println("Node IP: " + nodeIp);
//
//		// Store driver and additional information in the context for access in test methods
//		context.setAttribute("driver", driver);
//		context.setAttribute("platformName", platformName);
//		context.setAttribute("sessionId", sessionId);
//		context.setAttribute("nodeIp", nodeIp);
//
//	}
//
//	@BeforeClass
//	public void setDriver(ITestContext context) {
//		driver = (WebDriver) context.getAttribute("driver");  // Retrieve driver from context
//	}
//
//	@AfterTest
//	public void tearDown() {
//		if (driver != null) {
//			driver.quit();
//		}
//	}
//}


package BaseClasses;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.openqa.selenium.Capabilities;

public class SetupDriver {

	protected WebDriver driver;
	
	
	 @BeforeTest
	    @Parameters("browser")
	    public void setUpDriver(String browser, ITestContext context) {
	        if (browser.equalsIgnoreCase("chrome")) {
	            String downloadPath = "Download";
	            HashMap<String, Object> chromePrefs = new HashMap<>();
	            chromePrefs.put("profile.default_content_settings.popups", 0);
	            chromePrefs.put("download.default_directory", downloadPath);
	            chromePrefs.put("safebrowsing.enabled", "false");

	            ChromeOptions options = new ChromeOptions();
	            options.setExperimentalOption("prefs", chromePrefs);
	            driver = new org.openqa.selenium.chrome.ChromeDriver(options);  // Local ChromeDriver
	        } 
	        else if (browser.equalsIgnoreCase("firefox")) {
	            FirefoxOptions options = new FirefoxOptions();
	            driver = new org.openqa.selenium.firefox.FirefoxDriver(options);  // Local FirefoxDriver
	        } 
	        else if (browser.equalsIgnoreCase("msedge")) {
	            EdgeOptions options = new EdgeOptions();
	            driver = new org.openqa.selenium.edge.EdgeDriver(options);  // Local EdgeDriver
	        } 
	        else if(browser.equalsIgnoreCase("safari")) {
	            SafariOptions options = new SafariOptions();
	            driver = new org.openqa.selenium.safari.SafariDriver(options);  // Local SafariDriver
	        }

	        driver.manage().window().maximize();
//	        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
//	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	        context.setAttribute("driver", driver);  // Store driver in context for access in test methods
	    }

	    @BeforeClass
	    public void setDriver(ITestContext context) {
	        driver = (WebDriver) context.getAttribute("driver");  // Retrieve driver from context
	    }

	    @AfterTest
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}

