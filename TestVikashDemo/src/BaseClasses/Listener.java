package BaseClasses;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.opencsv.CSVWriter;

public class Listener implements ITestListener {
	private ExtentSparkReporter htmlReporter;
	private ExtentReports reports;
	private ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private String reportFolder;
	private Map<String, String> browserDetails = new HashMap<>();
	private Map<String, AtomicInteger> testCaseCount = new HashMap<>();
	private Map<String, ExtentTest> parentTests = new HashMap<>();
	private Set<String> loggedBrowsers = new HashSet<>();
	private Set<String> loggedTestCaseCount = new HashSet<>();
	private String systemInfo;
	private PrintStream originalPrintStream;
	private ByteArrayOutputStream testOutputStream;
	private SoftAssert softAssert = new SoftAssert();
	private List<TestRecord> testRecords = new ArrayList<>();
	private StringBuilder currentLogs = new StringBuilder();
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
	private Date suiteStartTime;

	// Class to store test information for grid report
	private static class TestRecord {
		String testName;
		String description;
		String browser;
		String startTime;
		String endTime;
		String status = "PENDING";  // Initialize with a default status
		StringBuilder logs = new StringBuilder();
		String screenshotPath;
		long duration;
		String error;
		String className;
		String browserVersion;
		String platform;
		// Add system information
		String userName = System.getProperty("user.name");
		String osName = System.getProperty("os.name");
		String computerName = System.getProperty("computername");
	}

	// Custom PrintStream to capture console output
	private class ConsoleCapture extends PrintStream {
		private final ExtentTest extentTest;
		private final TestRecord testRecord;

		public ConsoleCapture(OutputStream out, ExtentTest test, TestRecord record) {
			super(out, true);
			this.extentTest = test;
			this.testRecord = record;
		}

		@Override
		public void println(String message) {
			// Write to original console
			originalPrintStream.println(message);

			// Format time
			String time = timeFormat.format(new Date());

			// Add to ExtentReport
			if (extentTest != null) {
				extentTest.log(Status.INFO, "[" + time + "] " + message);
			}

			// Add to test record for grid report
			if (testRecord != null) {
				testRecord.logs.append("[").append(time).append("] ")
				.append(message).append("\n");
			}
		}

		@Override
		public void println(Object x) {
			println(String.valueOf(x));
		}
	}

	private void configureReports(String suiteName) {
		String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		reportFolder = "Test_Reports_" + suiteName + "_" + timestamp;

		// Create report directory
		File folder = new File(reportFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}

		// Configure ExtentReport
		String reportFile = reportFolder + File.separator + "ExtentReport_" + timestamp + ".html";
		htmlReporter = new ExtentSparkReporter(reportFile);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);

		htmlReporter.config().setDocumentTitle("Test Automation Report");
		htmlReporter.config().setReportName(suiteName);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");

		// Custom CSS for better formatting
		htmlReporter.config().setCss(".test-log { margin-left: 10px; }");
		htmlReporter.config().setCss(".test-status { font-weight: bold; }");
	}

	private void generateGridReport() {
	    try {
	        // Generate CSV report
	        String csvFile = reportFolder + File.separator + "TestResults.csv";
	        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
	            // Updated header with system information
	            writer.writeNext(new String[]{
	                    "Test Name", "Class Name", "Description", "Browser", "Version", 
	                    "Platform", "Start Time", "End Time", "Duration (sec)", 
	                    "Status", "Error", "Screenshot", "OS Name", "User Name", 
	                    "Computer Name", "Logs"
	            });

	            // Write test records with system information
	            for (TestRecord record : testRecords) {
	                writer.writeNext(new String[]{
	                        record.testName,
	                        record.className,
	                        record.description,
	                        record.browser,
	                        record.browserVersion,
	                        record.platform,
	                        record.startTime,
	                        record.endTime,
	                        String.format("%.2f", record.duration / 1000.0),
	                        record.status,
	                        record.error != null ? record.error : "",
	                        record.screenshotPath != null ? record.screenshotPath : "",
	                        record.osName,
	                        record.userName,
	                        record.computerName != null ? record.computerName : "N/A",
	                        record.logs.toString()
	                });
	            }
	        }

	        // Generate HTML grid report
	        String htmlFile = reportFolder + File.separator + "GridReport.html";
	        try (PrintWriter writer = new PrintWriter(new FileWriter(htmlFile))) {
	            writer.println("<!DOCTYPE html>");
	            writer.println("<html><head>");
	            writer.println("<style>");
	            writer.println("body { font-family: Arial, sans-serif; font-size: 10px; }");
	            writer.println("h2 { font-size: 14px; }");
	            writer.println("table { border-collapse: collapse; width: 100%; font-size: 10px; }");
	            writer.println("th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
	            writer.println("th { background-color: #4CAF50; color: white; }");
	            writer.println("tr:nth-child(even) { background-color: #f2f2f2; }");
	            writer.println(".PASS { color: green; }");
	            writer.println(".FAIL { color: red; }");
	            writer.println(".SKIP { color: orange; }");
	            writer.println(".PENDING { color: gray; }");
	            writer.println(".system-info { font-size: 10px; margin-bottom: 10px; }");
	            writer.println("pre { font-size: 10px; margin: 0; }");
	            writer.println(".screenshot { max-width: 200px; cursor: pointer; }");
	            writer.println(".log-content { display: none; }");
	            writer.println(".log-button { background-color: #4CAF50; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 3px; }");
	            writer.println(".log-button:hover { background-color: #45a049; }");
	            writer.println("</style>");

	            // Add JavaScript for toggle functionality and image popup
	            writer.println("<script>");
	            writer.println("function toggleLogs(buttonId, logId) {");
	            writer.println("    var logContent = document.getElementById(logId);");
	            writer.println("    var button = document.getElementById(buttonId);");
	            writer.println("    if (logContent.style.display === 'none') {");
	            writer.println("        logContent.style.display = 'block';");
	            writer.println("        button.innerHTML = 'Hide Logs';");
	            writer.println("    } else {");
	            writer.println("        logContent.style.display = 'none';");
	            writer.println("        button.innerHTML = 'Show Logs';");
	            writer.println("    }");
	            writer.println("}");
	            writer.println("function showImage(src) {");
	            writer.println("    window.open(src, '_blank', 'width=800,height=600');");
	            writer.println("}");
	            writer.println("</script>");
	            
	            writer.println("</head><body>");

	            writer.println("<h2>Test Execution Results</h2>");
	            
	            // System info in single line
	            writer.println("<div class='system-info'>");
	            writer.printf("OS: %s | User: %s | Computer: %s", 
	                System.getProperty("os.name"),
	                System.getProperty("user.name"),
	                System.getProperty("COMPUTERNAME") != null ? System.getProperty("COMPUTERNAME") : "N/A");
	            writer.println("</div>");

	            writer.println("<table>");
	            writer.println("<tr><th>Test Name</th><th>Result</th><th>Time</th>" +
	                    "<th>Browser</th><th>Duration</th><th>Logs</th><th>Screenshot</th></tr>");

	            int logCounter = 1;
	            for (TestRecord record : testRecords) {
	                writer.println("<tr>");
	                writer.printf("<td>%s</td>", record.testName);
	                writer.printf("<td class='%s'>%s</td>", record.status, record.status);
	                writer.printf("<td>%s</td>", record.startTime);
	                writer.printf("<td>%s %s</td>", record.browser, record.browserVersion);
	                writer.printf("<td>%.2f sec</td>", record.duration / 1000.0);
	                
	                // Logs column with toggle button
	                writer.println("<td>");
	                writer.printf("<button id='btn_%d' class='log-button' onclick='toggleLogs(\"btn_%d\", \"log_%d\")'>Show Logs</button>", 
	                    logCounter, logCounter, logCounter);
	                writer.printf("<pre id='log_%d' class='log-content'>%s</pre>", 
	                    logCounter, record.logs.toString());
	                writer.println("</td>");

	                // Screenshot column
	                writer.println("<td>");
	                if ("FAIL".equals(record.status) && record.screenshotPath != null) {
	                    writer.printf("<img src='file:///%s' class='screenshot' onclick='showImage(this.src)' alt='Failed Test Screenshot'/>",
	                            record.screenshotPath.replace("\\", "/"));
	                }
	                writer.println("</td>");
	                
	                writer.println("</tr>");
	                logCounter++;
	            }

	            writer.println("</table></body></html>");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Generate a detailed HTML report for test results with smaller font size
	 */
	private void generateDetailedHTMLReport() {
	    String reportPath = reportFolder + File.separator + "Deploy Automation Report.html";
	    try (PrintWriter writer = new PrintWriter(new FileWriter(reportPath))) {
	        // Calculate various statistics
	        int totalTests = testRecords.size();
	        int passedTests = (int) testRecords.stream().filter(r -> "PASS".equals(r.status)).count();
	        int failedTests = (int) testRecords.stream().filter(r -> "FAIL".equals(r.status)).count();
	        int skippedTests = (int) testRecords.stream().filter(r -> "SKIP".equals(r.status)).count();
	        
	        // Calculate pass rate
	        double passRate = totalTests > 0 ? (passedTests * 100.0 / totalTests) : 0;
	        
	        // Calculate total duration in minutes and seconds
	        long totalDuration = 0;
	        for (TestRecord record : testRecords) {
	            totalDuration += record.duration;
	        }
	        long minutes = totalDuration / (60 * 1000);
	        long seconds = (totalDuration % (60 * 1000)) / 1000;
	        String formattedDuration = String.format("%dm %ds", minutes, seconds);
	        
	        // Format the date
	        String formattedDate = new SimpleDateFormat("dd MMMM yyyy").format(suiteStartTime);
	        
	        // Build the HTML
	        writer.println("<!DOCTYPE html>");
	        writer.println("<html lang=\"en\">");
	        writer.println("<head>");
	        writer.println("    <meta charset=\"UTF-8\">");
	        writer.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
	        writer.println("    <title>Test Report</title>");
	        writer.println("    <style>");
	        writer.println("        :root {");
	        writer.println("            --primary-color: #4a6da7;");
	        writer.println("            --success-color: #28a745;");
	        writer.println("            --danger-color: #dc3545;");
	        writer.println("            --warning-color: #ffc107;");
	        writer.println("            --light-color: #f8f9fa;");
	        writer.println("            --dark-color: #343a40;");
	        writer.println("        }");
	        writer.println("        body { font-family: 'Segoe UI', Roboto, Arial, sans-serif; margin: 0; padding: 0; background-color: #f5f5f5; color: #333; font-size: 12px; }");
	        writer.println("        .container { max-width: 1200px; margin: 0 auto; padding: 10px; }");
	        writer.println("        .header { background-color: var(--primary-color); color: white; padding: 10px; border-radius: 5px 5px 0 0; }");
	        writer.println("        .header h1 { margin: 0; font-size: 18px; }");
	        writer.println("        .info-bar { background-color: white; padding: 8px; display: flex; justify-content: space-between; border-bottom: 1px solid #ddd; }");
	        writer.println("        .info-item { font-size: 12px; }");
	        writer.println("        .summary { display: flex; justify-content: space-between; margin: 15px 0; }");
	        writer.println("        .summary-card { flex: 1; max-width: 24%; background-color: white; border-radius: 5px; padding: 10px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); text-align: center; }");
	        writer.println("        .summary-card.total { border-top: 3px solid var(--primary-color); }");
	        writer.println("        .summary-card.passed { border-top: 3px solid var(--success-color); }");
	        writer.println("        .summary-card.failed { border-top: 3px solid var(--danger-color); }");
	        writer.println("        .summary-card.skipped { border-top: 3px solid var(--warning-color); }");
	        writer.println("        .summary-value { font-size: 28px; font-weight: bold; margin: 8px 0; }");
	        writer.println("        .summary-label { color: #666; font-size: 12px; }");
	        writer.println("        .card { background-color: white; border-radius: 5px; margin-bottom: 15px; box-shadow: 0 1px 3px rgba(0,0,0,0.1); }");
	        writer.println("        .card-header { padding: 10px; border-bottom: 1px solid #ddd; display: flex; justify-content: space-between; align-items: center; }");
	        writer.println("        .card-title { margin: 0; font-size: 16px; color: var(--dark-color); }");
	        writer.println("        .card-body { padding: 10px; }");
	        writer.println("        .tabs { display: flex; margin-bottom: 10px; border-bottom: 1px solid #ddd; }");
	        writer.println("        .tab { padding: 6px 12px; cursor: pointer; border-radius: 4px 4px 0 0; margin-right: 3px; font-size: 12px; }");
	        writer.println("        .tab.active { background-color: var(--primary-color); color: white; }");
	        writer.println("        .tab:not(.active) { background-color: #eee; }");
	        writer.println("        .tab:hover:not(.active) { background-color: #ddd; }");
	        writer.println("        table { width: 100%; border-collapse: collapse; font-size: 11px; }");
	        writer.println("        th { padding: 8px; text-align: left; background-color: #f8f9fa; border-bottom: 1px solid #ddd; font-weight: 600; }");
	        writer.println("        td { padding: 6px 8px; border-bottom: 1px solid #eee; }");
	        writer.println("        tr:hover { background-color: #f5f5f5; }");
	        writer.println("        .badge { padding: 3px 6px; border-radius: 3px; font-size: 11px; font-weight: 600; }");
	        writer.println("        .badge-success { background-color: #e8f5e9; color: var(--success-color); }");
	        writer.println("        .badge-danger { background-color: #ffebee; color: var(--danger-color); }");
	        writer.println("        .badge-warning { background-color: #fff8e1; color: #f57c00; }");
	        writer.println("        .details-btn { background-color: var(--primary-color); color: white; border: none; padding: 3px 8px; border-radius: 3px; cursor: pointer; font-size: 11px; }");
	        writer.println("        .details-btn:hover { background-color: #3c5a8a; }");
	        writer.println("        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); }");
	        writer.println("        .modal-content { background-color: white; margin: 5% auto; padding: 15px; border-radius: 5px; width: 80%; max-width: 900px; max-height: 80vh; overflow-y: auto; position: relative; }");
	        writer.println("        .close-btn { position: absolute; top: 5px; right: 10px; font-size: 20px; font-weight: bold; cursor: pointer; color: #888; }");
	        writer.println("        .close-btn:hover { color: #333; }");
	        writer.println("        .test-info { margin-bottom: 15px; }");
	        writer.println("        .test-info h3 { margin-top: 0; border-bottom: 1px solid #eee; padding-bottom: 8px; font-size: 16px; }");
	        writer.println("        .test-info p { margin: 4px 0; line-height: 1.4; font-size: 12px; }");
	        writer.println("        .test-logs { background-color: #f5f5f5; border: 1px solid #ddd; border-radius: 4px; padding: 8px; height: 250px; overflow-y: auto; font-family: monospace; font-size: 11px; white-space: pre-wrap; }");
	        writer.println("        .screenshot-area { margin-top: 15px; text-align: center; }");
	        writer.println("        .screenshot-area img { max-width: 100%; max-height: 350px; border: 1px solid #ddd; border-radius: 4px; cursor: pointer; }");
	        writer.println("        .no-data { text-align: center; padding: 20px; color: #666; font-style: italic; font-size: 12px; }");
	        writer.println("        .search-box { padding: 0 5px; display: flex; }");
	        writer.println("        .search-input { flex: 1; padding: 4px 8px; border: 1px solid #ddd; border-radius: 3px; margin-right: 5px; font-size: 12px; }");
	        writer.println("        .search-btn { background-color: var(--primary-color); color: white; border: none; padding: 4px 10px; border-radius: 3px; cursor: pointer; font-size: 12px; }");
	        writer.println("        .search-btn:hover { background-color: #3c5a8a; }");
	        writer.println("        .progress-container { height: 4px; width: 100%; background-color: #eee; margin-top: 5px; border-radius: 2px; overflow: hidden; }");
	        writer.println("        .progress-bar { height: 100%; background-color: var(--success-color); border-radius: 2px; }");
	        writer.println("    </style>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("    <div class=\"container\">");
	        writer.println("        <div class=\"info-bar\">");
	        writer.println("            <div class=\"info-item\">");
	        writer.println("                <strong>Date:</strong> " + formattedDate);
	        writer.println("            </div>");
	        writer.println("            <div class=\"info-item\">");
	        writer.println("                <strong>Duration:</strong> " + formattedDuration);
	        writer.println("            </div>");
	        writer.println("            <div class=\"info-item\">");
	        writer.println("                <strong>OS:</strong> " + System.getProperty("os.name"));
	        writer.println("            </div>");
	        writer.println("            <div class=\"info-item\">");
	        writer.println("                <strong>User:</strong> " + System.getProperty("user.name"));
	        writer.println("            </div>");
	        writer.println("        </div>");
	        
	        // Progress bar for pass rate
	        writer.println("        <div class=\"progress-container\">");
	        writer.printf("            <div class=\"progress-bar\" style=\"width: %.1f%%;\"></div>", passRate);
	        writer.println("        </div>");
	        
	        // Summary cards
	        writer.println("        <div class=\"summary\">");
	        
	        // Total Tests card
	        writer.println("            <div class=\"summary-card total\">");
	        writer.println("                <div class=\"summary-label\">Total Tests</div>");
	        writer.println("                <div class=\"summary-value\">" + totalTests + "</div>");
	        writer.println("            </div>");
	        
	        // Passed Tests card
	        writer.println("            <div class=\"summary-card passed\">");
	        writer.println("                <div class=\"summary-label\">Passed</div>");
	        writer.println("                <div class=\"summary-value\">" + passedTests + "</div>");
	        writer.printf("                <div>%.1f%%</div>", passRate);
	        writer.println("            </div>");
	        
	        // Failed Tests card
	        writer.println("            <div class=\"summary-card failed\">");
	        writer.println("                <div class=\"summary-label\">Failed</div>");
	        writer.println("                <div class=\"summary-value\">" + failedTests + "</div>");
	        writer.printf("                <div>%.1f%%</div>", totalTests > 0 ? (failedTests * 100.0 / totalTests) : 0);
	        writer.println("            </div>");
	        
	        // Skipped Tests card
	        writer.println("            <div class=\"summary-card skipped\">");
	        writer.println("                <div class=\"summary-label\">Skipped</div>");
	        writer.println("                <div class=\"summary-value\">" + skippedTests + "</div>");
	        writer.printf("                <div>%.1f%%</div>", totalTests > 0 ? (skippedTests * 100.0 / totalTests) : 0);
	        writer.println("            </div>");
	        
	        writer.println("        </div>"); // End of summary
	        
	        // Test details card
	        writer.println("        <div class=\"card\">");
	        writer.println("            <div class=\"card-header\">");
	        writer.println("                <h2 class=\"card-title\">Test Execution Details</h2>");
	        writer.println("                <div class=\"search-box\">");
	        writer.println("                    <input type=\"text\" id=\"searchInput\" class=\"search-input\" placeholder=\"Search test names...\">");
	        writer.println("                    <button class=\"search-btn\" onclick=\"searchTests()\">Search</button>");
	        writer.println("                </div>");
	        writer.println("            </div>");
	        writer.println("            <div class=\"card-body\">");
	        
	        // Tabs for filtering
	        writer.println("                <div class=\"tabs\">");
	        writer.println("                    <div class=\"tab active\" onclick=\"filterTests('all')\">All Tests (" + totalTests + ")</div>");
	        writer.println("                    <div class=\"tab\" onclick=\"filterTests('pass')\">Passed (" + passedTests + ")</div>");
	        writer.println("                    <div class=\"tab\" onclick=\"filterTests('fail')\">Failed (" + failedTests + ")</div>");
	        writer.println("                    <div class=\"tab\" onclick=\"filterTests('skip')\">Skipped (" + skippedTests + ")</div>");
	        writer.println("                </div>");
	        
	        // Table of tests
	        writer.println("                <table id=\"testTable\">");
	        writer.println("                    <thead>");
	        writer.println("                        <tr>");
	        writer.println("                            <th>Test Name</th>");
	        writer.println("                            <th>Browser</th>");
	        writer.println("                            <th>Duration</th>");
	        writer.println("                            <th>Status</th>");
	        writer.println("                            <th>Actions</th>");
	        writer.println("                        </tr>");
	        writer.println("                    </thead>");
	        writer.println("                    <tbody>");
	        
	        // Generate test rows
	        for (TestRecord record : testRecords) {
	            String statusClass;
	            String statusLabel;
	            String dataStatus;
	            
	            switch (record.status) {
	                case "PASS":
	                    statusClass = "badge-success";
	                    statusLabel = "Passed";
	                    dataStatus = "pass";
	                    break;
	                case "FAIL":
	                    statusClass = "badge-danger";
	                    statusLabel = "Failed";
	                    dataStatus = "fail";
	                    break;
	                case "SKIP":
	                    statusClass = "badge-warning";
	                    statusLabel = "Skipped";
	                    dataStatus = "skip";
	                    break;
	                default:
	                    statusClass = "";
	                    statusLabel = record.status;
	                    dataStatus = "other";
	            }
	            
	            writer.println("                        <tr class=\"test-row\" data-status=\"" + dataStatus + "\">");
	            writer.println("                            <td>" + record.testName + "</td>");
	            writer.println("                            <td>" + record.browser + " " + record.browserVersion + "</td>");
	            writer.println("                            <td>" + String.format("%.2fs", record.duration / 1000.0) + "</td>");
	            writer.println("                            <td><span class=\"badge " + statusClass + "\">" + statusLabel + "</span></td>");
	            writer.println("                            <td><button class=\"details-btn\" onclick=\"showTestDetails('" + 
	                           record.testName.replace("'", "\\'") + "')\">View Details</button></td>");
	            writer.println("                        </tr>");
	        }
	        
	        writer.println("                    </tbody>");
	        writer.println("                </table>");
	        
	        // No results message (hidden by default)
	        writer.println("                <div id=\"noResults\" class=\"no-data\" style=\"display: none;\">No matching tests found</div>");
	        
	        writer.println("            </div>"); // End of card-body
	        writer.println("        </div>"); // End of card
	        
	        // Test details modal
	        writer.println("        <div id=\"testDetailsModal\" class=\"modal\">");
	        writer.println("            <div class=\"modal-content\">");
	        writer.println("                <span class=\"close-btn\" onclick=\"closeModal()\">&times;</span>");
	        writer.println("                <div class=\"test-info\">");
	        writer.println("                    <h3 id=\"modalTestName\">Test Details</h3>");
	        writer.println("                    <p><strong>Status:</strong> <span id=\"modalStatus\"></span></p>");
	        writer.println("                    <p><strong>Browser:</strong> <span id=\"modalBrowser\"></span></p>");
	        writer.println("                    <p><strong>Duration:</strong> <span id=\"modalDuration\"></span></p>");
	        writer.println("                    <p><strong>Start Time:</strong> <span id=\"modalStartTime\"></span></p>");
	        writer.println("                    <p><strong>End Time:</strong> <span id=\"modalEndTime\"></span></p>");
	        writer.println("                    <p id=\"modalErrorContainer\" style=\"display: none;\"><strong>Error:</strong> <span id=\"modalError\"></span></p>");
	        writer.println("                </div>");
	        writer.println("                <h4>Test Logs:</h4>");
	        writer.println("                <div id=\"modalLogs\" class=\"test-logs\"></div>");
	        writer.println("                <div id=\"modalScreenshot\" class=\"screenshot-area\"></div>");
	        writer.println("            </div>");
	        writer.println("        </div>"); // End of modal
	        
	        writer.println("    </div>"); // End of container
	        
	        // JavaScript for functionality
	        writer.println("    <script>");
	        writer.println("        // Store test data for details modal");
	        writer.println("        const testData = {");
	        
	        // Generate JavaScript object with test data
	        for (TestRecord record : testRecords) {
	            writer.println("            '" + record.testName.replace("'", "\\'") + "': {");
	            writer.println("                status: '" + record.status + "',");
	            writer.println("                browser: '" + record.browser + " " + record.browserVersion + "',");
	            writer.println("                platform: '" + record.platform + "',");
	            writer.println("                className: '" + record.className + "',");
	            writer.println("                duration: '" + String.format("%.2fs", record.duration / 1000.0) + "',");
	            writer.println("                startTime: '" + record.startTime + "',");
	            writer.println("                endTime: '" + record.endTime + "',");
	            writer.println("                logs: `" + record.logs.toString().replace("`", "\\`") + "`,");
	            
	            if (record.error != null && !record.error.isEmpty()) {
	                writer.println("                error: `" + record.error.replace("`", "\\`") + "`,");
	            } else {
	                writer.println("                error: null,");
	            }
	            
	            if ("FAIL".equals(record.status) && record.screenshotPath != null) {
	                writer.println("                screenshot: 'file:///" + record.screenshotPath.replace("\\", "/") + "'");
	            } else {
	                writer.println("                screenshot: null");
	            }
	            
	            // Check if this is the last record to avoid trailing comma which causes syntax error
	            if (record.equals(testRecords.get(testRecords.size() - 1))) {
	                writer.println("            }");
	            } else {
	                writer.println("            },");
	            }
	        }
	        
	        writer.println("        };");
	        
	        // Functions for handling test filtering and modal display
	        writer.println("        // Wait for document to be fully loaded");
	        writer.println("        document.addEventListener('DOMContentLoaded', function() {");
	        writer.println("            // Initialize global variables");
	        writer.println("            window.modal = document.getElementById('testDetailsModal');");
	        writer.println("            window.searchInput = document.getElementById('searchInput');");
	        writer.println("            window.testRows = document.querySelectorAll('.test-row');");
	        writer.println("            window.noResults = document.getElementById('noResults');");
	        writer.println("            ");
	        writer.println("            // Add event listener for search input");
	        writer.println("            if (window.searchInput) {");
	        writer.println("                window.searchInput.addEventListener('keyup', function(event) {");
	        writer.println("                    if (event.key === 'Enter') {");
	        writer.println("                        searchTests();");
	        writer.println("                    }");
	        writer.println("                });");
	        writer.println("            }");
	        writer.println("            ");
	        writer.println("            // Add event listener for closing modal when clicking outside");
	        writer.println("            window.onclick = function(event) {");
	        writer.println("                if (event.target === window.modal) {");
	        writer.println("                    closeModal();");
	        writer.println("                }");
	        writer.println("            };");
	        writer.println("        });");
	        writer.println("");
	        writer.println("        // Filter tests by status");
	        writer.println("        function filterTests(status) {");
	        writer.println("            console.log('Filtering by status: ' + status);");
	        writer.println("            // Update active tab");
	        writer.println("            document.querySelectorAll('.tab').forEach(tab => {");
	        writer.println("                tab.classList.remove('active');");
	        writer.println("                if ((status === 'all' && tab.innerText.toLowerCase().includes('all')) ||");
	        writer.println("                    (status === 'pass' && tab.innerText.toLowerCase().includes('passed')) ||");
	        writer.println("                    (status === 'fail' && tab.innerText.toLowerCase().includes('failed')) ||");
	        writer.println("                    (status === 'skip' && tab.innerText.toLowerCase().includes('skipped'))) {");
	        writer.println("                    tab.classList.add('active');");
	        writer.println("                }");
	        writer.println("            });");
	        writer.println("");
	        writer.println("            // Filter table rows");
	        writer.println("            let visibleCount = 0;");
	        writer.println("            document.querySelectorAll('.test-row').forEach(row => {");
	        writer.println("                const rowStatus = row.getAttribute('data-status');");
	        writer.println("                console.log('Row status: ' + rowStatus);");
	        writer.println("                const searchText = document.getElementById('searchInput').value.toLowerCase();");
	        writer.println("                const testName = row.cells[0].innerText.toLowerCase();");
	        writer.println("                const matchesSearch = searchText === '' || testName.includes(searchText);");
	        writer.println("");
	        writer.println("                if ((status === 'all' || rowStatus === status) && matchesSearch) {");
	        writer.println("                    row.style.display = '';");
	        writer.println("                    visibleCount++;");
	        writer.println("                } else {");
	        writer.println("                    row.style.display = 'none';");
	        writer.println("                }");
	        writer.println("            });");
	        writer.println("");
	        writer.println("            // Show/hide no results message");
	        writer.println("            document.getElementById('noResults').style.display = visibleCount === 0 ? 'block' : 'none';");
	        writer.println("        }");
	        writer.println("");
	        writer.println("        // Search function");
	        writer.println("        function searchTests() {");
	        writer.println("            const activeTab = document.querySelector('.tab.active');");
	        writer.println("            let status = 'all';");
	        writer.println("            if (activeTab) {");
	        writer.println("                if (activeTab.innerText.toLowerCase().includes('passed')) status = 'pass';");
	        writer.println("                else if (activeTab.innerText.toLowerCase().includes('failed')) status = 'fail';");
	        writer.println("                else if (activeTab.innerText.toLowerCase().includes('skipped')) status = 'skip';");
	        writer.println("            }");
	        writer.println("            filterTests(status);");
	        writer.println("        }");
	        writer.println("");
	        writer.println("        // Show test details in modal");
	        writer.println("        function showTestDetails(testName) {");
	        writer.println("            const test = testData[testName];");
	        writer.println("            if (!test) return;");
	        writer.println("");
	        writer.println("            // Set modal content");
	        writer.println("            document.getElementById('modalTestName').textContent = testName;");
	        writer.println("            ");
	        writer.println("            // Set status with appropriate styling");
	        writer.println("            const statusSpan = document.getElementById('modalStatus');");
	        writer.println("            let statusClass = '';");
	        writer.println("            let statusText = '';");
	        writer.println("            ");
	        writer.println("            switch (test.status) {");
	        writer.println("                case 'PASS':");
	        writer.println("                    statusClass = 'badge-success';");
	        writer.println("                    statusText = 'Passed';");
	        writer.println("                    break;");
	        writer.println("                case 'FAIL':");
	        writer.println("                    statusClass = 'badge-danger';");
	        writer.println("                    statusText = 'Failed';");
	        writer.println("                    break;");
	        writer.println("                case 'SKIP':");
	        writer.println("                    statusClass = 'badge-warning';");
	        writer.println("                    statusText = 'Skipped';");
	        writer.println("                    break;");
	        writer.println("                default:");
	        writer.println("                    statusText = test.status;");
	        writer.println("            }");
	        writer.println("            ");
	        writer.println("            statusSpan.innerHTML = `<span class=\"badge ${statusClass}\">${statusText}</span>`;");
	        writer.println("            ");
	        writer.println("            // Set other test details");
	        writer.println("            document.getElementById('modalBrowser').textContent = test.browser;");
	        writer.println("            document.getElementById('modalDuration').textContent = test.duration;");
	        writer.println("            document.getElementById('modalStartTime').textContent = test.startTime;");
	        writer.println("            document.getElementById('modalEndTime').textContent = test.endTime;");
	        writer.println("            ");
	        writer.println("            // Handle error message if present");
	        writer.println("            const errorContainer = document.getElementById('modalErrorContainer');");
	        writer.println("            if (test.error) {");
	        writer.println("                document.getElementById('modalError').textContent = test.error;");
	        writer.println("                errorContainer.style.display = 'block';");
	        writer.println("            } else {");
	        writer.println("                errorContainer.style.display = 'none';");
	        writer.println("            }");
	        writer.println("            ");
	        writer.println("            // Set logs");
	        writer.println("            document.getElementById('modalLogs').innerHTML = test.logs.replace(/\\n/g, '<br>');");
	        writer.println("            ");
	        writer.println("            // Handle screenshot if available");
	        writer.println("            const screenshotArea = document.getElementById('modalScreenshot');");
	        writer.println("            if (test.screenshot) {");
	        writer.println("                screenshotArea.innerHTML = `");
	        writer.println("                    <h4>Screenshot:</h4>");
	        writer.println("                    <img src=\"${test.screenshot}\" onclick=\"window.open(this.src)\" alt=\"Test Screenshot\">");
	        writer.println("                `;");
	        writer.println("            } else {");
	        writer.println("                screenshotArea.innerHTML = '';");
	        writer.println("            }");
	        writer.println("            ");
	        writer.println("            // Show modal");
	        writer.println("            document.getElementById('testDetailsModal').style.display = 'block';");
	        writer.println("        }");
	        writer.println("        ");
	        writer.println("        // Close modal");
	        writer.println("        function closeModal() {");
	        writer.println("            document.getElementById('testDetailsModal').style.display = 'none';");
	        writer.println("        }");
	        writer.println("    </script>");
	        writer.println("</body>");
	        writer.println("</html>");
	        
	        System.out.println("Detailed HTML report generated at: " + reportPath);
	    } catch (IOException e) {
	        System.err.println("Error generating detailed HTML report: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	

	@Override
	public void onStart(ITestContext context) {
		 // Clear old reports first
	    try {
	        File reportsDir = new File("Test_Reports_" + context.getSuite().getName() + "_*");
	        if (reportsDir.exists()) {
	            FileUtils.deleteDirectory(reportsDir);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		configureReports(context.getSuite().getName());
		testCaseCount.clear();
		loggedBrowsers.clear();
		testRecords.clear();
		 parentTests.clear();  // Add this line
		    test.remove(); 
		    suiteStartTime = new Date();
	}

	@Override
	public void onTestStart(ITestResult result) {
//		  System.out.println("Test Description: " + result.getMethod().getDescription());
		WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
		if (driver == null) {
			throw new IllegalStateException("WebDriver not found in ITestContext");
		}

		// Create test record with proper initialization
		TestRecord record = new TestRecord();
		record.testName = result.getMethod().getDescription() != null ? 
				result.getMethod().getDescription() : 
					result.getMethod().getMethodName();
		record.description = result.getMethod().getDescription();
		record.className = result.getTestClass().getName();
		record.startTime = timeFormat.format(new Date());
		record.status = "IN_PROGRESS";  // Set initial status

		// Store the test start time in milliseconds for duration calculation
		result.setAttribute("startTimeMillis", System.currentTimeMillis());

		// Get browser details
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		record.browser = caps.getBrowserName();
		record.browserVersion = caps.getBrowserVersion();
		record.platform = caps.getPlatformName().toString();

		// Add system information
		record.userName = System.getProperty("user.name");
		record.osName = System.getProperty("os.name");
		record.computerName = System.getProperty("COMPUTERNAME");

		String browserKey = String.format("%s %s on %s", 
				record.browser, record.browserVersion, record.platform);

		// Update browser details with system information
		if (!browserDetails.containsKey(browserKey)) {
			browserDetails.put(browserKey, String.format(
					"Machine: %s, OS: %s, User: %s, Platform: %s",
					record.computerName,
					record.osName,
					record.userName,
					record.platform
					));
		}

		// Create ExtentReport test
		if (!parentTests.containsKey(browserKey)) {
			ExtentTest parent = reports.createTest(browserKey + " Tests");
			parentTests.put(browserKey, parent);
		}
		ExtentTest extentTest = parentTests.get(browserKey)
				.createNode(record.description != null ? record.description : record.testName);
		test.set(extentTest);

		// Setup console capture
		originalPrintStream = System.out;
		testOutputStream = new ByteArrayOutputStream();
		System.setOut(new ConsoleCapture(testOutputStream, extentTest, record));

		// Store test record
		testRecords.add(record);
		result.setAttribute("testRecord", record);

		// Log system info in report
		if (!loggedBrowsers.contains(browserKey)) {
			reports.setSystemInfo(browserKey, browserDetails.get(browserKey));
			reports.setSystemInfo("Operating System", record.osName);
			reports.setSystemInfo("User", record.userName);

			loggedBrowsers.add(browserKey);
		}

		testCaseCount.computeIfAbsent(browserKey, k -> new AtomicInteger(0)).incrementAndGet();
	}


	@Override
	public void onTestSuccess(ITestResult result) {
		TestRecord record = (TestRecord) result.getAttribute("testRecord");
		if (record != null) {
			record.status = "PASS";
			record.endTime = timeFormat.format(new Date());

			// Calculate duration
			Long startTime = (Long) result.getAttribute("startTimeMillis");
			if (startTime != null) {
				record.duration = System.currentTimeMillis() - startTime;
			}
		}

		System.setOut(originalPrintStream);
		test.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Restore original System.out first
		System.setOut(originalPrintStream);

		// Update test record
		TestRecord record = (TestRecord) result.getAttribute("testRecord");
		if (record != null) {
			record.status = "FAIL";
			record.endTime = timeFormat.format(new Date());

			// Calculate duration
			Long startTime = (Long) result.getAttribute("startTimeMillis");
			if (startTime != null) {
				record.duration = System.currentTimeMillis() - startTime;
			}

			record.error = result.getThrowable() != null ? 
					result.getThrowable().getMessage() : "Test failed";
		}

		if (test.get() != null) {
			WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");
			if (driver != null) {
				try {
					File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					String screenshotPath = "D:\\New Machine\\FailedTestImage\\" 
							+ result.getName() + ".png";
					FileUtils.copyFile(screenshot, new File(screenshotPath));
					test.get().log(Status.FAIL, "Screenshot captured for failed test.")
					.addScreenCaptureFromPath(screenshotPath);

					if (record != null) {
						record.screenshotPath = screenshotPath;
					}
				} catch (IOException e) {
					test.get().log(Status.FAIL, "Error saving screenshot: " + e.getMessage());
					if (record != null) {
						record.error += "\nError saving screenshot: " + e.getMessage();
					}
				}

				try {
					driver.navigate().refresh();
				} catch (Exception e) {
					test.get().log(Status.FAIL, "Error refreshing the page: " + e.getMessage());
					if (record != null) {
						record.error += "\nError refreshing the page: " + e.getMessage();
					}
				}
			}

			test.get().log(Status.FAIL, MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
			test.get().log(Status.FAIL, result.getThrowable());
		} else {
			System.err.println("ExtentTest is null for test failure: " + result.getName());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		TestRecord record = getCurrentTestRecord(result);
		if (record != null) {
			record.status = "SKIP";
			record.endTime = timeFormat.format(new Date());
			record.duration = result.getEndMillis() - result.getStartMillis();
			record.error = result.getThrowable() != null ? 
					result.getThrowable().getMessage() : "Test skipped";
		}

		// Restore console output
		System.setOut(originalPrintStream);
		test.get().log(Status.SKIP, "Test Skipped");
		if (result.getThrowable() != null) {
			test.get().log(Status.SKIP, result.getThrowable());
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		// Generate reports
		generateGridReport();
		generateDetailedHTMLReport();

		// Log test counts
		for (Map.Entry<String, AtomicInteger> entry : testCaseCount.entrySet()) {
			if (!loggedTestCaseCount.contains(entry.getKey())) {
				reports.setSystemInfo(entry.getKey() + " Tests", 
						String.valueOf(entry.getValue().get()));
				loggedTestCaseCount.add(entry.getKey());
			}
		}

		// Check soft assertions
		try {
			softAssert.assertAll();
		} catch (Throwable t) {
			if (test.get() != null) {
				test.get().log(Status.FAIL, "Soft assertions failed: " + t.getMessage());
			}
		}

		// Restore console output and flush reports
		System.setOut(originalPrintStream);
		if (reports != null) {
			reports.flush();
		}
		
	}

	
	private TestRecord getCurrentTestRecord(ITestResult result) {
		return testRecords.stream()
				.filter(r -> r.testName.equals(result.getName()))
				.findFirst()
				.orElse(null);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Not implemented
	}
}