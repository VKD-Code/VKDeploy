package DeploySmokeTestOnChrome;





import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import BaseClasses.SetupDriver;
import BaseClasses.Finalread;

public class TC0002_InstallAgentForChrome extends SetupDriver{
	private static WebDriverWait wait ;
	private static final String VBOX_MANAGE_PATH = "C:\\Program Files\\Oracle\\VirtualBox\\VBoxManage.exe";

	@Test(description="Make sure agent can be downloaded and install on wksn.")
	public  void agentinstall() throws InterruptedException, IOException
	{
	
		try {
			Finalread.readStringFromExcel();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String Window=Finalread.Window1;
	
Thread.sleep(5000);
			//download agent
			driver.findElement(By.xpath("//a[@id='optiondropdownMenu']")).click();
			Thread.sleep(3000);

			//download agent button
			driver.findElement(By.xpath("//input[@id='btnDownloadNow']")).click();
			Thread.sleep(Duration.ofMinutes(3).toMillis());

			// Assuming default download directory for a Windows user
			String downloadFolderPath = System.getProperty("user.home") + "\\Downloads";
			File downloadFolder = new File(downloadFolderPath);

			// List all files in the download folder
			File[] files = downloadFolder.listFiles();

			if (files != null && files.length > 0) {
				// Example: Find the most recent file
				File latestFile = Arrays.stream(files)
						.filter(file -> file.isFile() && file.getName().endsWith(".exe")) // Filter by .exe extension
						.max(Comparator.comparingLong(File::lastModified)) // Get the file with the latest modification time
						.orElse(null);

				if (latestFile != null) {
					// Print the name of the latest .exe file found
					System.out.println("Latest .exe file: " + latestFile.getName());
					// Now you can do something with latestFile, like copying or moving it
				} else {
					System.out.println("No .exe files found in the download folder.");
				}



				// VM and user details
				 String vmName = "Win10-21H2";
			        String username = "Administrator";
			        String password1 = "aloha"; // Replace with guest VM password

				// File paths
				String hostFilePath = downloadFolderPath + "\\" + latestFile.getName(); // Source file path on host
				String guestFilePath =  "C:\\Users\\Administrator\\Desktop\\"; // Destination directory on guest VM
				String guestFilePathForExecution = guestFilePath+latestFile.getName()+"";
				
        //open vm machine
        ProcessBuilder pb = new ProcessBuilder(VBOX_MANAGE_PATH, "startvm", vmName, "--type", "gui");
        Process p = pb.start();
        p.waitFor();
				
				// Construct the VBoxManage command to copy the file
				String[] copyFileCommand = {
						VBOX_MANAGE_PATH, "guestcontrol", vmName, "copyto",
						"--username", username,
						"--password", password1,
						"--target-directory", guestFilePath, hostFilePath
				};

				// Construct the VBoxManage command to execute the file using cmd.exe with elevated privileges
				// Construct the VBoxManage command to execute the file using cmd.exe with elevated privileges
				String[] executeCmdCommand = {
						VBOX_MANAGE_PATH,
						"guestcontrol", vmName,
						"--username", username,
						"--password", password1,
						"run", "--exe", "cmd.exe", "--",
						"/c", guestFilePathForExecution, " /S"
				};


				// Execute the command using ProcessBuilder to copy the file
				ProcessBuilder processBuilder1 = new ProcessBuilder(copyFileCommand);
				processBuilder1.redirectErrorStream(true);
				Thread.sleep(Duration.ofMinutes(3).toMillis());
				try {
					Process process1 = processBuilder1.start();
					int exitCode = process1.waitFor();

					// Output the result of the file copy
					try (BufferedReader reader1 = new BufferedReader(new InputStreamReader(process1.getInputStream()))) {
						String line;
						while ((line = reader1.readLine()) != null) {
							System.out.println("OUTPUT: " + line);
						}
					}

					// Check if there is any error output
					try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process1.getErrorStream()))) {
						String line1;
						while ((line1 = errorReader.readLine()) != null) {
							System.err.println("ERROR: " + line1);
						}
					}

					if (exitCode == 0) {
						System.out.println("File copied successfully from host to guest VM.");
						// Execute the command using ProcessBuilder to run the file with elevated privileges
						processBuilder1.command(executeCmdCommand);
						System.out.println("starting....");

						process1 = processBuilder1.start();
						System.out.println("started....");
						exitCode=process1.waitFor();
						System.out.println("completed....");

						// Output the result of the file execution
						try (BufferedReader reader = new BufferedReader(new InputStreamReader(process1.getInputStream()))) {
							String line;
							while ((line = reader.readLine()) != null) {
								System.out.println("OUTPUT: " + line);
							}
						}

						if (exitCode == 0) {
							System.out.println("File executed successfully on the guest VM.");
							driver.navigate().refresh();


							wait = new WebDriverWait( driver, Duration.ofMinutes((long)(5))); // Waiting for a maximum of 10 seconds
							String condition1="(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']";
							System.out.println(condition1);
							wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(condition1)));

							if (driver.findElements(By.xpath(condition1)).size() > 0) {
								System.out.println("Condition 1 met");
								System.out.println("computer added");

								Thread.sleep(Duration.ofMinutes(2).toMillis());
								//waiting for online
								driver.navigate().refresh();

								Thread.sleep(5000);
								wait = new WebDriverWait( driver, Duration.ofMinutes((long)(10))); // Waiting for a maximum of 10 seconds

								String condition2="(//div[@id='dvPendingApplicationsGrid']//table)[4]//tr//td//span[text()='"+Window+"']/preceding-sibling::div[@title='Offline']";
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(condition2)));

								if (driver.findElements(By.xpath(condition2)).size() == 0) {
									System.out.println("Condition 1 met");

									System.out.println("computer is online");
									Thread.sleep(5000);


								} else {
									System.out.println("computer is offline");

								}
							} else {
								System.out.println("computer not  added");
							}

						} else {
							System.out.println("Failed to execute file on the guest VM. Exit code: " + exitCode);
						}


					} else {
						System.out.println("Failed to copy file from host to guest VM.");
						return;
					}

				} catch (IOException | InterruptedException es) {
					es.printStackTrace();
				}

			} else {
				System.out.println("Download folder is empty or does not exist.");
			}



		}
	}

