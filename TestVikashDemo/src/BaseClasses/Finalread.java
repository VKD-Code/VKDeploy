package BaseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Finalread {
	
	private static String filePath1 = "D:\\Userinfo.xlsx";//Excel path
    public static String URL1;
    public static String URL2;
    public static String URL3;
    public static String Email1;
    public static String Email2;
    public static String Email3;
    public static String Password1;
    public static String Password2;
    public static String Password3;
    public static String Window1;
    public static String Window2;
    public static String Window3;
    public static String Groupname;
    public static String  Assigntag;
    public static String  policy;
    public static String installapp;
    public static String Uninstallapp;
    public static String batchname;
    public static String shellname;
    public static String vbname;
    public static String Apppresetname;
    public static String Apppresetappname;
    public static String Wingetappname;
    public static String cacheversion;
    public static String Adduser;
    public static String Edituser;
    public static String Addsite;
    public static String Editsite;
    public static String Ticketname;
    public static String AntiPolicyName;
    public static String Antiversion ;
 public static void readStringFromExcel() throws IOException {
	 
    	//url*******************
    	try {
    	FileInputStream file1 = new FileInputStream(new File(filePath1));
    	Workbook workbook1 = WorkbookFactory.create(file1);
    	Sheet sheet1 = workbook1.getSheet("info");

        Row row1 = sheet1.getRow(1);
        Cell cell = row1.getCell(0);

        String cellValue = cell.getStringCellValue();

        // Assign value to the static variable
        URL1 = cellValue;
    	}catch(Exception e) {
    		System.out.println("File not found");
    	}
    	try {
        	FileInputStream file1 = new FileInputStream(new File(filePath1));
        	Workbook workbook1 = WorkbookFactory.create(file1);
        	Sheet sheet1 = workbook1.getSheet("info");

            Row row1 = sheet1.getRow(2);
            Cell cell = row1.getCell(0);

            String cellValue = cell.getStringCellValue();

            // Assign value to the static variable
            URL2 = cellValue;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	try {
        	FileInputStream file1 = new FileInputStream(new File(filePath1));
        	Workbook workbook1 = WorkbookFactory.create(file1);
        	Sheet sheet1 = workbook1.getSheet("info");

            Row row1 = sheet1.getRow(3);
            Cell cell = row1.getCell(0);

            String cellValue = cell.getStringCellValue();

            // Assign value to the static variable
            URL3 = cellValue;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	//email
    	try {
    	FileInputStream file2 = new FileInputStream(new File(filePath1));
    	Workbook workbook2 = WorkbookFactory.create(file2);
    	Sheet sheet2 = workbook2.getSheet("info");

        Row row2 = sheet2.getRow(1);
        Cell cell2 = row2.getCell(1);

        String cellValue2 = cell2.getStringCellValue();

        // Assign value to the static variable
        Email1 = cellValue2;
	}catch(Exception e) {
		System.out.println("File not found");
	}
    	
    	try {
    	FileInputStream file2 = new FileInputStream(new File(filePath1));
    	Workbook workbook2 = WorkbookFactory.create(file2);
    	Sheet sheet2 = workbook2.getSheet("info");

        Row row2 = sheet2.getRow(2);
        Cell cell2 = row2.getCell(1);

        String cellValue2 = cell2.getStringCellValue();

        // Assign value to the static variable
        Email2 = cellValue2;
	}catch(Exception e) {
		System.out.println("File not found");
	}
    	
    	try {
    	FileInputStream file2 = new FileInputStream(new File(filePath1));
    	Workbook workbook2 = WorkbookFactory.create(file2);
    	Sheet sheet2 = workbook2.getSheet("info");

        Row row2 = sheet2.getRow(3);
        Cell cell2 = row2.getCell(1);

        String cellValue2 = cell2.getStringCellValue();

        // Assign value to the static variable
        Email3 = cellValue2;
	}catch(Exception e) {
		System.out.println("File not found");
	}
    	
    	//password
    	try {
    	FileInputStream file3 = new FileInputStream(new File(filePath1));
    	Workbook workbook3 = WorkbookFactory.create(file3);
    	Sheet sheet3 = workbook3.getSheet("info");

        Row row3 = sheet3.getRow(1);
        Cell cell3 = row3.getCell(2);

        String cellValue3 = cell3.getStringCellValue();

        // Assign value to the static variable
        Password1 = cellValue3;
    	}catch(Exception e) {
    		System.out.println("File not found");
    	}
    	
    	
    	
      	//password
    	try {
    	FileInputStream file3 = new FileInputStream(new File(filePath1));
    	Workbook workbook3 = WorkbookFactory.create(file3);
    	Sheet sheet3 = workbook3.getSheet("info");

        Row row3 = sheet3.getRow(2);
        Cell cell3 = row3.getCell(2);

        String cellValue3 = cell3.getStringCellValue();

        // Assign value to the static variable
        Password2 = cellValue3;
    	}catch(Exception e) {
    		System.out.println("File not found");
    	}
    	
      	//password
    	try {
    	FileInputStream file3 = new FileInputStream(new File(filePath1));
    	Workbook workbook3 = WorkbookFactory.create(file3);
    	Sheet sheet3 = workbook3.getSheet("info");

        Row row3 = sheet3.getRow(3);
        Cell cell3 = row3.getCell(2);

        String cellValue3 = cell3.getStringCellValue();

        // Assign value to the static variable
        Password3 = cellValue3;
    	}catch(Exception e) {
    		System.out.println("File not found");
    	}
    	
    	
    	
    	
    	//Window****************************
      	try {
        	FileInputStream file4 = new FileInputStream(new File(filePath1));
        	Workbook workbook4 = WorkbookFactory.create(file4);
        	Sheet sheet4 = workbook4.getSheet("info");

            Row row4 = sheet4.getRow(1);
            Cell cell4 = row4.getCell(3);

            String cellValue3 = cell4.getStringCellValue();

            // Assign value to the static variable
            Window1 = cellValue3;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
      	
      	try {
        	FileInputStream file4 = new FileInputStream(new File(filePath1));
        	Workbook workbook4 = WorkbookFactory.create(file4);
        	Sheet sheet4 = workbook4.getSheet("info");

            Row row4 = sheet4.getRow(2);
            Cell cell4 = row4.getCell(3);

            String cellValue3 = cell4.getStringCellValue();

            // Assign value to the static variable
            Window2 = cellValue3;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
      	
      	try {
        	FileInputStream file4 = new FileInputStream(new File(filePath1));
        	Workbook workbook4 = WorkbookFactory.create(file4);
        	Sheet sheet4 = workbook4.getSheet("info");

            Row row4 = sheet4.getRow(3);
            Cell cell4 = row4.getCell(3);

            String cellValue3 = cell4.getStringCellValue();

            // Assign value to the static variable
            Window3 = cellValue3;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
      	
      	//add group
      	try {
        	FileInputStream file5 = new FileInputStream(new File(filePath1));
        	Workbook workbook5 = WorkbookFactory.create(file5);
        	Sheet sheet5 = workbook5.getSheet("data");

            Row row5 = sheet5.getRow(4);
            Cell cell5 = row5.getCell(1);

            String cellValue4 = cell5.getStringCellValue();

            // Assign value to the static variable
            Groupname = cellValue4;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
      	//tag assign
      	
    	try {
        	FileInputStream file6 = new FileInputStream(new File(filePath1));
        	Workbook workbook6 = WorkbookFactory.create(file6);
        	Sheet sheet6 = workbook6.getSheet("data");

            Row row6 = sheet6.getRow(5);
            Cell cell6 = row6.getCell(1);

            String cellValue5 = cell6.getStringCellValue();

            // Assign value to the static variable
            Assigntag = cellValue5;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	//policy assign*****************************************
    	
    	try {
        	FileInputStream file7 = new FileInputStream(new File(filePath1));
        	Workbook workbook7 = WorkbookFactory.create(file7);
        	Sheet sheet7 = workbook7.getSheet("data");

            Row row7 = sheet7.getRow(6);
            Cell cell7 = row7.getCell(1);

            String cellValue6 = cell7.getStringCellValue();

            // Assign value to the static variable
            policy = cellValue6;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	//install app************
    	try {
        	FileInputStream file8 = new FileInputStream(new File(filePath1));
        	Workbook workbook8 = WorkbookFactory.create(file8);
        	Sheet sheet8 = workbook8.getSheet("data");

            Row row8 = sheet8.getRow(7);
            Cell cell8 = row8.getCell(1);

            String cellValue7 = cell8.getStringCellValue();

            // Assign value to the static variable
            installapp = cellValue7;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	//Uninstall app************
    	try {
        	FileInputStream file9 = new FileInputStream(new File(filePath1));
        	Workbook workbook9 = WorkbookFactory.create(file9);
        	Sheet sheet9 = workbook9.getSheet("data");

            Row row9 = sheet9.getRow(8);
            Cell cell9 = row9.getCell(1);

            String cellValue8 = cell9.getStringCellValue();

            // Assign value to the static variable
            Uninstallapp = cellValue8;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	
    	
    	//batch script************
    	try {
        	FileInputStream file10 = new FileInputStream(new File(filePath1));
        	Workbook workbook10 = WorkbookFactory.create(file10);
        	Sheet sheet10 = workbook10.getSheet("data");

            Row row10 = sheet10.getRow(9);
            Cell cell10 = row10.getCell(1);

            String cellValue9 = cell10.getStringCellValue();

            // Assign value to the static variable
            batchname = cellValue9;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	//shell script************
    	try {
        	FileInputStream file11 = new FileInputStream(new File(filePath1));
        	Workbook workbook11 = WorkbookFactory.create(file11);
        	Sheet sheet11 = workbook11.getSheet("data");

            Row row11 = sheet11.getRow(10);
            Cell cell11 = row11.getCell(1);

            String cellValue10 = cell11.getStringCellValue();

            // Assign value to the static variable
            shellname = cellValue10;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	//VB script************
    	try {
        	FileInputStream file12 = new FileInputStream(new File(filePath1));
        	Workbook workbook12 = WorkbookFactory.create(file12);
        	Sheet sheet12 = workbook12.getSheet("data");

            Row row12 = sheet12.getRow(11);
            Cell cell12 = row12.getCell(1);

            String cellValue11 = cell12.getStringCellValue();

            // Assign value to the static variable
            vbname = cellValue11;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	//App preset name ************
    	try {
        	FileInputStream file13 = new FileInputStream(new File(filePath1));
        	Workbook workbook13 = WorkbookFactory.create(file13);
        	Sheet sheet13 = workbook13.getSheet("data");

            Row row13 = sheet13.getRow(12);
            Cell cell13 = row13.getCell(1);

            String cellValue12 = cell13.getStringCellValue();

            // Assign value to the static variable
            Apppresetname = cellValue12;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	//App preset app name ************
    	try {
        	FileInputStream file14 = new FileInputStream(new File(filePath1));
        	Workbook workbook14 = WorkbookFactory.create(file14);
        	Sheet sheet14 = workbook14.getSheet("data");

            Row row14 = sheet14.getRow(13);
            Cell cell14 = row14.getCell(1);

            String cellValue13 = cell14.getStringCellValue();

            // Assign value to the static variable
            Apppresetappname = cellValue13;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
       	//Winget app name ************
    	try {
        	FileInputStream file15 = new FileInputStream(new File(filePath1));
        	Workbook workbook15 = WorkbookFactory.create(file15);
        	Sheet sheet15 = workbook15.getSheet("data");

            Row row15 = sheet15.getRow(14);
            Cell cell15 = row15.getCell(1);

            String cellValue14 = cell15.getStringCellValue();

            // Assign value to the static variable
            Wingetappname = cellValue14;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	//cache version ************
    	try {
        	FileInputStream file16 = new FileInputStream(new File(filePath1));
        	Workbook workbook16 = WorkbookFactory.create(file16);
        	Sheet sheet16 = workbook16.getSheet("data");

            Row row16 = sheet16.getRow(15);
            Cell cell16 = row16.getCell(1);

            String cellValue15 = cell16.getStringCellValue();

            // Assign value to the static variable
            cacheversion = cellValue15;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	
      	//user name ************
    	try {
        	FileInputStream file17 = new FileInputStream(new File(filePath1));
        	Workbook workbook17 = WorkbookFactory.create(file17);
        	Sheet sheet17 = workbook17.getSheet("data");

            Row row17 = sheet17.getRow(16);
            Cell cell17 = row17.getCell(1);

            String cellValue16 = cell17.getStringCellValue();

            // Assign value to the static variable
            Adduser = cellValue16;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	//Edit user name ************
    	try {
        	FileInputStream file18 = new FileInputStream(new File(filePath1));
        	Workbook workbook18 = WorkbookFactory.create(file18);
        	Sheet sheet18 = workbook18.getSheet("data");

            Row row18 = sheet18.getRow(17);
            Cell cell18 = row18.getCell(1);

            String cellValue17 = cell18.getStringCellValue();

            // Assign value to the static variable
            Edituser = cellValue17;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	//Add site  name ************
    	try {
        	FileInputStream file19 = new FileInputStream(new File(filePath1));
        	Workbook workbook19 = WorkbookFactory.create(file19);
        	Sheet sheet19 = workbook19.getSheet("data");

            Row row19 = sheet19.getRow(18);
            Cell cell19 = row19.getCell(1);

            String cellValue18 = cell19.getStringCellValue();

            // Assign value to the static variable
            Addsite = cellValue18;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	//Edit site  name ************
    	try {
        	FileInputStream file20 = new FileInputStream(new File(filePath1));
        	Workbook workbook20 = WorkbookFactory.create(file20);
        	Sheet sheet20 = workbook20.getSheet("data");

            Row row20 = sheet20.getRow(19);
            Cell cell20 = row20.getCell(1);

            String cellValue19 = cell20.getStringCellValue();

            // Assign value to the static variable
            Editsite = cellValue19;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    	//ticket name ************
    	try {
        	FileInputStream file21 = new FileInputStream(new File(filePath1));
        	Workbook workbook21 = WorkbookFactory.create(file21);
        	Sheet sheet21 = workbook21.getSheet("data");

            Row row21 = sheet21.getRow(20);
            Cell cell21 = row21.getCell(1);

            String cellValue20 = cell21.getStringCellValue();

            // Assign value to the static variable
            Ticketname = cellValue20;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
      	//Anti-virus policy  name ************
    	try {
        	FileInputStream file22 = new FileInputStream(new File(filePath1));
        	Workbook workbook22 = WorkbookFactory.create(file22);
        	Sheet sheet22 = workbook22.getSheet("data");

            Row row22 = sheet22.getRow(21);
            Cell cell22 = row22.getCell(1);

            String cellValue21 = cell22.getStringCellValue();

            // Assign value to the static variable
            AntiPolicyName = cellValue21;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	//Anti-virus version ************
    	try {
        	FileInputStream file23 = new FileInputStream(new File(filePath1));
        	Workbook workbook23 = WorkbookFactory.create(file23);
        	Sheet sheet23 = workbook23.getSheet("data");

            Row row23 = sheet23.getRow(22);
            Cell cell23 = row23.getCell(1);

            String cellValue22 = cell23.getStringCellValue();

            // Assign value to the static variable
            Antiversion = cellValue22;
        	}catch(Exception e) {
        		System.out.println("File not found");
        	}
    	
    
}}
