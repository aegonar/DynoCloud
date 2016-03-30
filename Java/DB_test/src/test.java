

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class test {

	private static Scanner read = new Scanner (System.in);
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	


	public static void main (String a[]){
		
		
		
		link.Open_link();
		

			
			try{
				String query_monthly_update_date = "SELECT * FROM test";
				prep_sql = link.linea.prepareStatement(query_monthly_update_date);
				//prep_sql.setString(1, ".");
				ResultSet daily_update_date = prep_sql.executeQuery();
				System.out.println("executeQuery");
					while(daily_update_date.next()){
						//daily_update_dates[i] = daily_update_date.getString("MAX(update_date)");
						System.out.println("key: " + daily_update_date.getString("key") + " value: " + daily_update_date.getString("value"));
					}
			}catch(Exception e){

				System.out.println("Error: " + e.getMessage());
				
				link.Close_link();

			}

		link.Close_link();
		
	}
//	
//	private static void Daily(){
//		System.out.println("\t-+-Daily Process Menu-+-");
//		System.out.println("");
//		System.out.println("  -+-Daily Process");
//		System.out.println("	-+-Preprocess");
//		System.out.println("1 		Eligibility Validation");
//		System.out.println("2 		Eligibility Merge");
//		System.out.println("3 		Enrollments Validation");
//		System.out.println("4 		Enrollments Merge");
//		System.out.println("	-+-Postprocess");
//		System.out.println("5 		Daily run");
//		System.out.println("");
//		System.out.println("0	-+-Main Menu");
//		
//		System.out.println("");
//		
//		while(true){
//			
//			System.out.println("Enter Menu Option: ");
//			
//			String dailyMenuInput=null;
//			int dailyMenuInputOption = -1;
//			
//			dailyMenuInput=read.nextLine();
//			System.out.println("User input: " + dailyMenuInput);
//			
//			try {dailyMenuInputOption = Integer.parseInt(dailyMenuInput);} catch (NumberFormatException e) {}
//
//	        switch (dailyMenuInputOption){
//	            case 1: Eligibility_Validation();
//	            		break;
//	            case 2: Eligibility_Merge();
//	            		break;
//	            case 3: Enrollments_Validation();
//	                     break;
//	            case 4: Enrollments_Merge();
//	            		break;	
//	            case 5: Daily_run();
//	            		break;	
//	            case 0: main(arguments);
//        		break;
//	            
//	            default: System.out.println("Invalid input");
//	                     break;	
//	        }
//		}
//		
//	}
//	
//	private static void Enrollments_Merge(){
//		
//		System.out.println("\t-+-Enrollments Merge-+-");
//	
//			String cmd1 = "ls " + installDir + "preprocess/ | grep -i \"\\.enr$\"";
//			ProcessBuilder pb1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
//			pb1.redirectErrorStream(true);
//		        try {
//		            Process p1 = pb1.start();
//		            String s;
//		            BufferedReader stdout = new BufferedReader (
//		                new InputStreamReader(p1.getInputStream()));
//		            while ((s = stdout.readLine()) != null) {
//		            	enrFiles_list.add(s);
//		            }
//		            p1.getInputStream().close();
//		            p1.getOutputStream().close();
//		            p1.getErrorStream().close();
//		         } catch (Exception ex) {
//		            ex.printStackTrace();
//		        }
//		        
//		        if(enrFiles_list.size() == 0){
//		        	System.out.println("");
//		        	System.out.println("No Enrollments files to merge");
//		        	System.out.println("\nReseting...\n");
//		        	enrFiles_list.clear();
//		        	Daily();
//		        }
//		        
//		        String enrFiles[] = convertToStringArray(enrFiles_list.toArray());
//		        enrFiles_list.clear();
//		        
//		        System.out.println("");
//		        System.out.println("Enrollments files to merge (*.enr): ");
//		        for(int i = 0; i<enrFiles.length; i++){
//		        	System.out.println("\t " + enrFiles[i]);
//		        }
//		        
//		        System.out.println();
//		        System.out.println("Execute Enrollments merge with these files? [y/n] ");
//				String execute = read.nextLine();
//				System.out.println("User input: " + execute);
//				
//				while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//					System.out.println("Invalid input: ");
//					System.out.println("Execute Enrollments merge with these files? [y/n] ");
//					execute = read.nextLine();
//					System.out.println("User input: " + execute);
//				}
//				
//				if(execute.equalsIgnoreCase("n")){
//					Daily();
//				}
//				
//				System.out.println();
//		    	String enr_merge_date;
//				while(true){
//				System.out.println("Enter update date (yyyy-mm-dd): ");
//				enr_merge_date = read.nextLine();
//				System.out.println("User input: " + enr_merge_date);
//					if(valido.valid_date(enr_merge_date, "yyyy-MM-dd")){
//						break;
//					}else{
//						System.out.println("Invalid date format");
//					}
//				}
//				
//				String filesString = "";
//				for(int i = 0; i<enrFiles.length; i++){
//					filesString += enrFiles[i] + " ";
//				}
//				
//				filesString = filesString.substring(0, filesString.length()-1);
//				filesString = ("\"" + filesString + "\"");
//
//				String cmd2 = scriptsDir + enrollmentsMergeScript + " " + enr_merge_date + " " + filesString;
//				ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
//				
//					pb2.redirectErrorStream(true);
//				        try {
//				            Process p2 = pb2.start();
//				            String s;
//				            BufferedReader stdout = new BufferedReader (
//				                new InputStreamReader(p2.getInputStream()));
//				            while ((s = stdout.readLine()) != null) {
//				                System.out.println(s);
//				            }
//				            p2.getInputStream().close();
//				            p2.getOutputStream().close();
//				            p2.getErrorStream().close();
//				         } catch (Exception ex) {
//				            ex.printStackTrace();
//				        }
//		//System.out.println("Done.");	        
//		Daily();
//	}
//	
//	private static void Eligibility_Merge(){
//		
//		System.out.println("\t-+-Eligibility Merge-+-");
//	
//			String cmd1 = "ls " + installDir + "preprocess/ | grep -i \"^v\".*\"\\.dat$\"";
//			ProcessBuilder pb1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
//			pb1.redirectErrorStream(true);
//		        try {
//		            Process p1 = pb1.start();
//		            String s;
//		            BufferedReader stdout = new BufferedReader (
//		                new InputStreamReader(p1.getInputStream()));
//		            while ((s = stdout.readLine()) != null) {
//		            	datFiles_list.add(s);
//		            }
//		            p1.getInputStream().close();
//		            p1.getOutputStream().close();
//		            p1.getErrorStream().close();
//		         } catch (Exception ex) {
//		            ex.printStackTrace();
//		        }
//		        
//		        if(datFiles_list.size() == 0){
//		        	System.out.println("");
//		        	System.out.println("No Eligibility files to merge");
//		        	System.out.println("\nReseting...\n");
//		        	datFiles_list.clear();
//		        	Daily();
//		        }
//		        
//		        String datFiles[] = convertToStringArray(datFiles_list.toArray());
//		        datFiles_list.clear();
//		        
//		        System.out.println("");
//		        System.out.println("Eligibility files to merge (*.dat): ");
//		        for(int i = 0; i<datFiles.length; i++){
//		        	System.out.println("\t " + datFiles[i]);
//		        }
//
//		        
//		        System.out.println();
//		        System.out.println("Execute Eligibility merge with these files? [y/n] ");
//				String execute = read.nextLine();
//				System.out.println("User input: " + execute);
//				
//				while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//					System.out.println("Invalid input: ");
//					System.out.println("Execute Eligibility merge with these files? [y/n] ");
//					execute = read.nextLine();
//					System.out.println("User input: " + execute);
//				}
//				
//				if(execute.equalsIgnoreCase("n")){
//					Daily();
//				}
//				
//				System.out.println();
//		    	String elig_merge_date;
//				while(true){
//				System.out.println("Enter update date (yyyy-mm-dd): ");
//				elig_merge_date = read.nextLine();
//				System.out.println("User input: " + elig_merge_date);
//					if(valido.valid_date(elig_merge_date, "yyyy-MM-dd")){
//						break;
//					}else{
//						System.out.println("Invalid date format");
//					}
//				}
//				
//				String filesString = "";
//				for(int i = 0; i<datFiles.length; i++){
//					filesString += datFiles[i] + " ";
//				}
//				
//				filesString = filesString.substring(0, filesString.length()-1);
//				filesString = ("\"" + filesString + "\"");
//
//				String cmd2 = scriptsDir + eligibilityMergeScript + " " + elig_merge_date + " " + filesString;
//				ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
//				
//					pb2.redirectErrorStream(true);
//				        try {
//				            Process p2 = pb2.start();
//				            String s;
//				            BufferedReader stdout = new BufferedReader (
//				                new InputStreamReader(p2.getInputStream()));
//				            while ((s = stdout.readLine()) != null) {
//				                System.out.println(s);
//				            }
//				            p2.getInputStream().close();
//				            p2.getOutputStream().close();
//				            p2.getErrorStream().close();
//				         } catch (Exception ex) {
//				            ex.printStackTrace();
//				        }
//		//System.out.println("Done.");	        
//		Daily();
//	}
//	
//	private static void Eligibility_Validation(){
//		
//		System.out.println("\t-+-Eligibility Validation-+-");
//	
//			String cmd1 = "ls " + dataDir + " | grep -i \"^v\".*\"\\.ref$\"";
//			ProcessBuilder pb1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
//			pb1.redirectErrorStream(true);
//		        try {
//		            Process p1 = pb1.start();
//		            String s;
//		            BufferedReader stdout = new BufferedReader (
//		                new InputStreamReader(p1.getInputStream()));
//		            while ((s = stdout.readLine()) != null) {
//		            	refFiles_list.add(s);
//		            }
//		            p1.getInputStream().close();
//		            p1.getOutputStream().close();
//		            p1.getErrorStream().close();
//		         } catch (Exception ex) {
//		            ex.printStackTrace();
//		        }
//		        
//		        if(refFiles_list.size() == 0){
//		        	System.out.println("");
//		        	System.out.println("No Eligibility files to validate");
//		        	System.out.println("\nReseting...\n");
//		        	refFiles_list.clear();
//		        	Daily();
//		        }
//		        
//		        String refFiles[] = convertToStringArray(refFiles_list.toArray());
//		        refFiles_list.clear();
//		        
//		        System.out.println("");
//		        System.out.println("Eligibility files to validate (*.ref): ");
//		        for(int i = 0; i<refFiles.length; i++){
//		        	System.out.println("\t " + refFiles[i]);
//		        }
//		        
//		        System.out.println();
//		        System.out.println("Execute Eligibility Validation with these files? [y/n] ");
//				String execute = read.nextLine();
//				System.out.println("User input: " + execute);
//				
//				while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//					System.out.println("Invalid input: ");
//					System.out.println("Execute Eligibility Validation with these files? [y/n] ");
//					execute = read.nextLine();
//					System.out.println("User input: " + execute);
//				}
//				
//				if(execute.equalsIgnoreCase("n")){
//					Daily();
//				}
//				
//				String filesString = "";
//				for(int i = 0; i<refFiles.length; i++){
//					filesString += refFiles[i] + " ";
//				}
//				
//				filesString = filesString.substring(0, filesString.length()-1);
//				filesString = ("\"" + filesString + "\"");
//
//				String cmd2 = scriptsDir + eligibilityValidationScript + " " + filesString;
//				ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
//				
//					pb2.redirectErrorStream(true);
//				        try {
//				            Process p2 = pb2.start();
//				            String s;
//				            BufferedReader stdout = new BufferedReader (
//				                new InputStreamReader(p2.getInputStream()));
//				            while ((s = stdout.readLine()) != null) {
//				                System.out.println(s);
//				            }
//				            p2.getInputStream().close();
//				            p2.getOutputStream().close();
//				            p2.getErrorStream().close();
//				         } catch (Exception ex) {
//				            ex.printStackTrace();
//				        }
//		//System.out.println("Done.");	        
//		Daily();
//	}
//	
//	private static void Enrollments_Validation(){
//		
//		System.out.println("\t-+-Enrollments Validation-+-");
//	
//			String cmd1 = "ls " + dataDir + " | grep -i \"\\.sus$\"";
//			ProcessBuilder pb1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
//			pb1.redirectErrorStream(true);
//		        try {
//		            Process p1 = pb1.start();
//		            String s;
//		            BufferedReader stdout = new BufferedReader (
//		                new InputStreamReader(p1.getInputStream()));
//		            while ((s = stdout.readLine()) != null) {
//		            	susFiles_list.add(s);
//		            }
//		            p1.getInputStream().close();
//		            p1.getOutputStream().close();
//		            p1.getErrorStream().close();
//		         } catch (Exception ex) {
//		            ex.printStackTrace();
//		        }
//		        
//		        if(susFiles_list.size() == 0){
//		        	System.out.println("");
//		        	System.out.println("No Enrollments files to validate");
//		        	System.out.println("\nReseting...\n");
//		        	susFiles_list.clear();
//		        	Daily();
//		        }
//		        
//		        String susFiles[] = convertToStringArray(susFiles_list.toArray());
//		        susFiles_list.clear();
//		        
//		        System.out.println("");
//		        System.out.println("Enrollments files to validate (*.sus): ");
//		        for(int i = 0; i<susFiles.length; i++){
//		        	System.out.println("\t " + susFiles[i]);
//		        }
//		        
//		        System.out.println();
//		        System.out.println("Execute Enrollments Validation with these files? [y/n] ");
//				String execute = read.nextLine();
//				System.out.println("User input: " + execute);
//				
//				while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//					System.out.println("Invalid input: ");
//					System.out.println("Execute Enrollments Validation with these files? [y/n] ");
//					execute = read.nextLine();
//					System.out.println("User input: " + execute);
//				}
//				
//				if(execute.equalsIgnoreCase("n")){
//					Daily();
//				}
//				
//				String filesString = "";
//				for(int i = 0; i<susFiles.length; i++){
//					filesString += susFiles[i] + " ";
//				}
//				
//				filesString = filesString.substring(0, filesString.length()-1);
//				filesString = ("\"" + filesString + "\"");
//
//				String cmd2 = scriptsDir + enrollmentsValidationScript + " " + filesString;
//				ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
//				
//					pb2.redirectErrorStream(true);
//				        try {
//				            Process p2 = pb2.start();
//				            String s;
//				            BufferedReader stdout = new BufferedReader (
//				                new InputStreamReader(p2.getInputStream()));
//				            while ((s = stdout.readLine()) != null) {
//				                System.out.println(s);
//				            }
//				            p2.getInputStream().close();
//				            p2.getOutputStream().close();
//				            p2.getErrorStream().close();
//				         } catch (Exception ex) {
//				            ex.printStackTrace();
//				        }
//		//System.out.println("Done.");	        
//		Daily();
//	}
//	
//	private static void Daily_run(){
//		System.out.println("\t-+-Daily Run-+-");
//		System.out.println("Loading...\n");
//		
//		String region;
//		String daily_update_dates[] = new String[9];
//		
//		if(!daily_update_date_error){
//			
//			link.Open_link();
//			
//			for(int i=0; i<9; i++){
//				region=getRegionKey(i);
//				
//				try{
//					String query_monthly_update_date = "SELECT MAX(update_date) from family_eligibility where region_id = ?";
//					prep_sql = link.linea.prepareStatement(query_monthly_update_date);
//					prep_sql.setString(1, region);
//					ResultSet daily_update_date = prep_sql.executeQuery();
//						
//						while(daily_update_date.next()){
//							daily_update_dates[i] = daily_update_date.getString("MAX(update_date)");
//						}
//				}catch(Exception e){
//					String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//					String className = Thread.currentThread().getStackTrace()[1].getClassName();
//					String message = ("Error: " + e.getMessage() );
//					
//					link.Close_link();
//					stopProgramExecution(methodName, className, message);
//				}
//			if(daily_update_dates[i] != null){
//				System.out.println("Last update date: " + daily_update_dates[i] + " for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}else{
//				System.out.println("No update date found for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//			
//			}
//			link.Close_link();
//			
//		}
//		
//		System.out.println();
//		
//		String dailyDate;
//		String dailyDateTime;
//		
//		while(true){
//		System.out.println("Enter update date (yyyy-mm-dd): ");
//		dailyDate = read.nextLine();
//		System.out.println("User input: " + dailyDate);
//			if(valido.valid_date(dailyDate, "yyyy-MM-dd")){
//				break;
//			}else{
//				System.out.println("Invalid date format");
//			}
//		}
//		
//		while(true){
//			System.out.println("Enter time (hh:mm): ");
//			dailyDateTime = read.nextLine();
//			System.out.println("User input: " + dailyDateTime);
//				if(valid_time(dailyDateTime)){
//					dailyDateTime += ":00";
//					break;
//				}else{
//					System.out.println("Invalid time format");
//				}
//			}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String daily_all_regions = read.nextLine();
//		System.out.println("User input: " + daily_all_regions);
//		
//		while(!(daily_all_regions.equalsIgnoreCase("y") || daily_all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			daily_all_regions = read.nextLine();
//			System.out.println("User input: " + daily_all_regions);
//		}
//		
//		boolean[] dailyRegions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(daily_all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				dailyRegions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(dailyRegions[0] == true){System.out.println("Region already entered\n");}
//							dailyRegions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(dailyRegions[1] == true){System.out.println("Region already entered\n");}
//							dailyRegions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(dailyRegions[2] == true){System.out.println("Region already entered\n");}
//							dailyRegions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(dailyRegions[3] == true){System.out.println("Region already entered\n");}
//							dailyRegions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(dailyRegions[4] == true){System.out.println("Region already entered\n");}
//							dailyRegions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(dailyRegions[5] == true){System.out.println("Region already entered\n");}
//							dailyRegions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(dailyRegions[6] == true){System.out.println("Region already entered\n");}
//							dailyRegions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(dailyRegions[7] == true){System.out.println("Region already entered\n");}
//							dailyRegions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(dailyRegions[8] == true){System.out.println("Region already entered\n");}
//							dailyRegions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//	
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
//		
//		System.out.println("Re-run? [y/n] ");
//		String isRerun = read.nextLine();
//		System.out.println("User input: " + isRerun);
//		
//		while(!(isRerun.equalsIgnoreCase("y") || isRerun.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Re-run? [y/n] ");
//			isRerun = read.nextLine();
//			System.out.println("User input: " + isRerun);
//		}
//		
//		boolean rerun = false;
//		if(isRerun.equalsIgnoreCase("y")){
//			rerun = true;
//		}
//		
////Output
//		System.out.println();
//		System.out.println("Update date: " + dailyDate + " " + dailyDateTime);
//		System.out.println("Regions: ");
//		for(int i = 0; i<dailyRegions.length; i++){
//			if(dailyRegions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute Daily run? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute Daily run? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Daily();
//		}
//		
//		for(int i = 0; i<dailyRegions.length; i++){
//			
//			if(dailyRegions[i] == true){
//				
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				
//				try{
//
//					Date lastDate = sdf.parse(daily_update_dates[i].substring(0, 10));
//					Date updateDate = sdf.parse(dailyDate);
//					Date today = sdf.parse(DateToday);
//					
//					if(updateDate.compareTo(lastDate) == 0 && rerun==false){
//						System.out.println("\tError: Same update date as processes date for region " + getRegionKey(i) + ", must use rerun.");
//						daily_update_date_error = true;
//					}else{
//						if(updateDate.before(lastDate)){
//							System.out.println("\tError: Update Date is before last processesed date for region " + getRegionKey(i));
//							daily_update_date_error = true;
//						}else{
//							
//							if(updateDate.after(today)){
//								System.out.println("\tError: Update Date is in the future for region " + getRegionKey(i));
//								daily_update_date_error = true;
//							}
//							
//						}
//					}
//					
//				}catch(ParseException e){
//					e.printStackTrace();
//				}
//				
//			}
//		}
//		
//		if(daily_update_date_error){
//			System.out.println("\nReseting...\n");
//			daily_update_date_error = false;
//			Daily_run();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] daily_exit_code = new int[9];
//		dailyDate += "_" + dailyDateTime;
//		
//			for(int i = 0; i<dailyRegions.length; i++){
//				if(dailyRegions[i] == true){
//
//					cmd = scriptsDir + dailyScript;
//					System.out.println(cmd);
//					
//					String rerunArgument;
//					if(rerun){
//						rerunArgument = "-rerun";
//					}else{
//						rerunArgument = "";
//					}
//					
//						ProcessBuilder pb = new ProcessBuilder(cmd, dailyDate, getRegionKey(i), rerunArgument);
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            daily_exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(dailyRegions[i] == true){
//					if(daily_exit_code[i] == 0){
//						System.out.println("Daily run for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("Daily run for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Daily();
//	}
//	
//	private static void Monthly(){
//		System.out.println("\t-+-Monthly Process Menu-+-");
//		System.out.println("");
//		System.out.println("  -+-Monthly Process");
//		System.out.println("1	    End-of-Month Run");
//		System.out.println("");
//		System.out.println("0	    Main Menu");
//		
//		System.out.println("");
//		
//		while(true){
//			
//			System.out.println("Enter Menu Option: ");
//			
//			String monthlyMenuInput=null;
//			int monthlyMenuInputOption = -1;
//			
//			monthlyMenuInput=read.nextLine();
//			System.out.println("User input: " + monthlyMenuInput);
//			
//			try {monthlyMenuInputOption = Integer.parseInt(monthlyMenuInput);} catch (NumberFormatException e) {}
//
//	        switch (monthlyMenuInputOption){
//	            case 1: Monthly_run();
//	            		break;	
//	            case 0: main(arguments);
//        		break;
//	            
//	            default: System.out.println("Invalid input");
//	                     break;	
//	        }
//		}
//		
//	}
//	
//	private static void Monthly_run(){
//
//		System.out.println("\t-+-End-of-Month Run-+-");
//		System.out.println("Loading...\n");
//		
//		String region;
//		String monthly_update_dates[] = new String[9];
//		
//		if(!monthly_update_date_error){
//			
//			link.Open_link();
//			
//			for(int i=0; i<9; i++){
//				region=getRegionKey(i);
//				
//				try{
//					String query_monthly_update_date = "SELECT MAX(update_date) from family_eligibility where update_user = ? and region_id = ?";
//					prep_sql = link.linea.prepareStatement(query_monthly_update_date);
//					prep_sql.setString(1, "SYSTEX");
//					prep_sql.setString(2, region);
//					ResultSet monthly_update_date = prep_sql.executeQuery();
//						
//						while(monthly_update_date.next()){
//							monthly_update_dates[i] = monthly_update_date.getString("MAX(update_date)");
//						}
//				}catch(Exception e){
//					String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//					String className = Thread.currentThread().getStackTrace()[1].getClassName();
//					String message = ("Error: " + e.getMessage() );
//					
//					link.Close_link();
//					stopProgramExecution(methodName, className, message);
//				}
//			
//			if(monthly_update_dates[i] != null){
//				System.out.println("Last update date: " + monthly_update_dates[i] + " for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}else{
//				System.out.println("No update date found for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//			}
//			link.Close_link();
//			
//		}
//		
//		System.out.println();
//		
//		String monthlyDate;
//		
//		while(true){
//		System.out.println("Enter update date (yyyy-mm-dd): ");
//		monthlyDate = read.nextLine();
//		System.out.println("User input: " + monthlyDate);
//			if(valido.valid_date(monthlyDate, "yyyy-MM-dd")){
//				break;
//			}else{
//				System.out.println("Invalid date format");
//			}
//		}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String monthly_all_regions = read.nextLine();
//		System.out.println("User input: " + monthly_all_regions);
//		
//		while(!(monthly_all_regions.equalsIgnoreCase("y") || monthly_all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			monthly_all_regions = read.nextLine();
//			System.out.println("User input: " + monthly_all_regions);
//		}
//		
//		boolean[] monthlyRegions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(monthly_all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				monthlyRegions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(monthlyRegions[0] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(monthlyRegions[1] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(monthlyRegions[2] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(monthlyRegions[3] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(monthlyRegions[4] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(monthlyRegions[5] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(monthlyRegions[6] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(monthlyRegions[7] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(monthlyRegions[8] == true){System.out.println("Region already entered\n");}
//							monthlyRegions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//	
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
////Output
//		System.out.println();
//		System.out.println("Update date: " + monthlyDate);
//		System.out.println("Regions: ");
//		for(int i = 0; i<monthlyRegions.length; i++){
//			if(monthlyRegions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute End-of-Month run? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute End-of-Month run? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Monthly();
//		}
//		
//		for(int i = 0; i<monthlyRegions.length; i++){
//			
//			if(monthlyRegions[i] == true){
//				
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				try{
//
//					Date lastDate = sdf.parse(monthly_update_dates[i].substring(0, 10));
//					Date updateDate = sdf.parse(monthlyDate);
//					
//					if(updateDate.compareTo(lastDate) == 0){
//						System.out.println("\tError: Same update date as processes date for region " + getRegionKey(i) + ", cannot execute.");
//						monthly_update_date_error = true;
//					}else{
//						if(updateDate.before(lastDate)){
//							System.out.println("\tError: Update Date is before last processesed date for region " + getRegionKey(i));
//							System.out.println("");
//							monthly_update_date_error = true;
//						}
//					}
//					
//				}catch(ParseException e){
//					System.out.println("Error");
//				}
//				
//			}
//		}
//		
//		if(monthly_update_date_error){
//			System.out.println("\nReseting...\n");
//			monthly_update_date_error = false;
//			Monthly_run();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] monthly_exit_code = new int[9];
//		
//			for(int i = 0; i<monthlyRegions.length; i++){
//				if(monthlyRegions[i] == true){
//					
//					
//					//cmd = scriptsDir + monthlyScript + " " + monthlyDate + " " + getRegionKey(i);
//					cmd = scriptsDir + monthlyScript;
//					System.out.println(cmd);
//					
//		
//						ProcessBuilder pb = new ProcessBuilder(cmd, monthlyDate, getRegionKey(i));
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            monthly_exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(monthlyRegions[i] == true){
//					if(monthly_exit_code[i] == 0){
//						System.out.println("End-of-month run for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("End-of-month run for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Monthly();
//	}
//
//	
//	private static void Query(){
//			System.out.println("\t-+-Query Process Menu-+-");
//			System.out.println("");
//			System.out.println("  -+-Query Process");
//			System.out.println("1	    Query Run");
//			System.out.println("");
//			System.out.println("0	    Main Menu");
//			
//			System.out.println("");
//			
//			while(true){
//				
//				System.out.println("Enter Menu Option: ");
//				
//				String queryMenuInput=null;
//				int queryMenuInputOption = -1;
//				
//				queryMenuInput=read.nextLine();
//				System.out.println("User input: " + queryMenuInput);
//				
//				try {queryMenuInputOption = Integer.parseInt(queryMenuInput);} catch (NumberFormatException e) {}
//
//		        switch (queryMenuInputOption){
//		            case 1: Query_run();
//		            		break;	
//		            case 0: main(arguments);
//	        		break;
//		            
//		            default: System.out.println("Invalid input");
//		                     break;	
//		        }
//			}
//		
//	}
//	
//	private static void Query_run(){
//		System.out.println("\t-+-Query Process-+-");
//		
//		String cmd1 = "ls " + dataDir + " | grep -i .qry";
//		ProcessBuilder pb1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
//		pb1.redirectErrorStream(true);
//	        try {
//	            Process p1 = pb1.start();
//	            String s;
//	            BufferedReader stdout = new BufferedReader (
//	                new InputStreamReader(p1.getInputStream()));
//	            while ((s = stdout.readLine()) != null) {
//	            	qryFiles_list.add(s);
//	            }
//	            p1.getInputStream().close();
//	            p1.getOutputStream().close();
//	            p1.getErrorStream().close();
//	         } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//	        
//	        if(qryFiles_list.size() == 0){
//	        	System.out.println("");
//	        	System.out.println("No Query files to process");
//	        	System.out.println("\nReseting...\n");
//	        	enrFiles_list.clear();
//	        	Daily();
//	        }
//	        
//	        String qryFiles[] = convertToStringArray(qryFiles_list.toArray());
//	        qryFiles_list.clear();
//	        
//	        System.out.println("");
////	rac     System.out.println("Enrollments files to merge (*.qry): ");
///* rac */   System.out.println("Query files to process (*.qry): ");
//	        
//	        for(int i = 0; i<qryFiles.length; i++){
//	        	System.out.println("\t " + qryFiles[i]);
//	        }
//	        
//	        System.out.println();
//	    	String query_date;
//			while(true){
//// rac      System.out.println("Enter update date (yyyy-mm-dd): ");
///* rac */	System.out.println("Enter today's date (yyyy-mm-dd): ");
//			query_date = read.nextLine();
//			System.out.println("User input: " + query_date);
//				if(valido.valid_date(query_date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//			}
//	        
//	        System.out.println();
//			System.out.println("Execute Query process with these files? [y/n] ");
//			String execute = read.nextLine();
//			System.out.println("User input: " + execute);
//			
//			while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//				System.out.println("Invalid input: ");
//				System.out.println("Execute Query process with these files? [y/n] ");
//				execute = read.nextLine();
//				System.out.println("User input: " + execute);
//			}
//			
//			if(execute.equalsIgnoreCase("n")){
//				Daily();
//			}
//			
//			
//			String filesString = "";
//			for(int i = 0; i<qryFiles.length; i++){
//				filesString += qryFiles[i] + " ";
//			}
//			
//			filesString = filesString.substring(0, filesString.length()-1);
//			filesString = ("\"" + filesString + "\"");
//
//			String cmd2 = scriptsDir + query_Script + " " + query_date + " " + filesString;
//			ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
//			
//				pb2.redirectErrorStream(true);
//			        try {
//			            Process p2 = pb2.start();
//			            String s;
//			            BufferedReader stdout = new BufferedReader (
//			                new InputStreamReader(p2.getInputStream()));
//			            while ((s = stdout.readLine()) != null) {
//			                System.out.println(s);
//			            }
//			            p2.getInputStream().close();
//			            p2.getOutputStream().close();
//			            p2.getErrorStream().close();
//			         } catch (Exception ex) {
//			            ex.printStackTrace();
//			        }
//		//System.out.println("Done.");	        
//		Query();
//	}
//	
//	private static void Other(){
//		System.out.println("\t-+-Other Menu-+-");
//		System.out.println("");
//		System.out.println("  -+-Hacienda");
//		System.out.println("1	    Hacienda Master File Report");
//		System.out.println("2	    Hacienda Payment File Report");
//		System.out.println("3	    LA Discount Report");
//		System.out.println("");
//		System.out.println("  -+-CMS");
//		System.out.println("4	    CMS To DualPR Table");
//		System.out.println("5	    Medicare Extract");
//		System.out.println("");
//		System.out.println("0 -+-Main Menu");
//		System.out.println("");
//		
//		while(true){
//			
//			System.out.println("Enter Menu Option: ");
//			
//			String reportsMenuInput=null;
//			int reportsMenuInputOption = -1;
//			
//			reportsMenuInput=read.nextLine();
//			System.out.println("User input: " + reportsMenuInput);
//			
//			try {reportsMenuInputOption = Integer.parseInt(reportsMenuInput);} catch (NumberFormatException e) {}
//
//	        switch (reportsMenuInputOption){
//	            case 1: hacienda_master_file();
//	                     break;
//	            case 2: hacienda_payment_file();
//	            		break;	
//	            case 3: ela_discount();
//	            		break;	
////	            case 4: cms_update();
////    					break;
////	            case 5: medicare_extract();
////	            		break;	
//
//	            case 0: main(arguments);
//        		break;
//	            
//	            default: System.out.println("Invalid input");
//	                    break;	
//	        }
//		}
//		
//	}
//	
//	private static void Reports(){
//		System.out.println("\t-+-Reports Menu-+-");
//		System.out.println("");
//		System.out.println("1	    Medicaid Counts Eligible");
//		System.out.println("2	    Medicaid Counts Insured ");
//		System.out.println("3	    PICA Enroll Report");
//		System.out.println("4	    PAM monthly Report");
//		System.out.println("5	    MBHO Enroll Report Report");
//		System.out.println("6	    Export All");
//		System.out.println("");
//		System.out.println("0  -+-Main Menu");
//		
//		System.out.println("");
//		
//		while(true){
//			
//			System.out.println("Enter Menu Option: ");
//			
//			String reportsMenuInput=null;
//			int reportsMenuInputOption = -1;
//			
//			reportsMenuInput=read.nextLine();
//			System.out.println("User input: " + reportsMenuInput);
//			
//			try {reportsMenuInputOption = Integer.parseInt(reportsMenuInput);} catch (NumberFormatException e) {}
//
//	        switch (reportsMenuInputOption){
//	            case 1: medicaid_counts_eligible();
//	    				break;
//	            case 2: medicaid_counts_insured();
//	    				break;
//	            case 3: pica_enroll();
//	            		break;
//	            case 4: pam_monthly();
//	            		break;
//	            case 5: mbho_enroll();
//    					break;
//	            case 6: export_all();
//	            		break;	
//
//	            case 0: main(arguments);
//        		break;
//	            
//	            default: System.out.println("Invalid input");
//	                    break;	
//	        }
//		}
//		
//	}
//	
//	private static void medicaid_counts_insured(){
//		System.out.println("\t-+-Medicaid Counts Insured Report-+-");
//		System.out.println("Loading...\n");
//		
//		String Date;
//		
//		while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			Date = read.nextLine();
//			System.out.println("User input: " + Date);
//				if(valido.valid_date(Date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//		}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String all_regions = read.nextLine();
//		System.out.println("User input: " + all_regions);
//		
//		while(!(all_regions.equalsIgnoreCase("y") || all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			all_regions = read.nextLine();
//			System.out.println("User input: " + all_regions);
//		}
//		
//		boolean[] Regions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				Regions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(Regions[0] == true){System.out.println("Region already entered\n");}
//							Regions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(Regions[1] == true){System.out.println("Region already entered\n");}
//							Regions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(Regions[2] == true){System.out.println("Region already entered\n");}
//							Regions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(Regions[3] == true){System.out.println("Region already entered\n");}
//							Regions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(Regions[4] == true){System.out.println("Region already entered\n");}
//							Regions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(Regions[5] == true){System.out.println("Region already entered\n");}
//							Regions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(Regions[6] == true){System.out.println("Region already entered\n");}
//							Regions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(Regions[7] == true){System.out.println("Region already entered\n");}
//							Regions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(Regions[8] == true){System.out.println("Region already entered\n");}
//							Regions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
//		
////Output
//		System.out.println();
//		System.out.println("Update date: " + Date);
//		System.out.println("Regions: ");
//		for(int i = 0; i<Regions.length; i++){
//			if(Regions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute Medicaid Counts Insured Report? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute Medicaid Counts Insured Report? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Reports();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] exit_code = new int[9];
//		
//			for(int i = 0; i<Regions.length; i++){
//				if(Regions[i] == true){
//
//					cmd = scriptsDir + medicaid_counts_script;
//					System.out.println(cmd);
//					
//						ProcessBuilder pb = new ProcessBuilder(cmd, Date, getRegionKey(i));
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(Regions[i] == true){
//					if(exit_code[i] == 0){
//						System.out.println("Medicaid Counts Insured Report for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("Medicaid Counts Insured Report for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Reports();
//	}
//	
//	private static void medicaid_counts_eligible(){
//		System.out.println("\t-+-Medicaid Counts Eligible Report-+-");
//		System.out.println("Loading...\n");
//		
//		String Date;
//		
//		while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			Date = read.nextLine();
//			System.out.println("User input: " + Date);
//				if(valido.valid_date(Date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//		}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String all_regions = read.nextLine();
//		System.out.println("User input: " + all_regions);
//		
//		while(!(all_regions.equalsIgnoreCase("y") || all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			all_regions = read.nextLine();
//			System.out.println("User input: " + all_regions);
//		}
//		
//		boolean[] Regions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				Regions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(Regions[0] == true){System.out.println("Region already entered\n");}
//							Regions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(Regions[1] == true){System.out.println("Region already entered\n");}
//							Regions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(Regions[2] == true){System.out.println("Region already entered\n");}
//							Regions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(Regions[3] == true){System.out.println("Region already entered\n");}
//							Regions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(Regions[4] == true){System.out.println("Region already entered\n");}
//							Regions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(Regions[5] == true){System.out.println("Region already entered\n");}
//							Regions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(Regions[6] == true){System.out.println("Region already entered\n");}
//							Regions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(Regions[7] == true){System.out.println("Region already entered\n");}
//							Regions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(Regions[8] == true){System.out.println("Region already entered\n");}
//							Regions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
//		
////Output
//		System.out.println();
//		System.out.println("Update date: " + Date);
//		System.out.println("Regions: ");
//		for(int i = 0; i<Regions.length; i++){
//			if(Regions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute Medicaid Counts Eligible Report? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute Medicaid Counts Eligible Report? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Reports();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] exit_code = new int[9];
//		
//			for(int i = 0; i<Regions.length; i++){
//				if(Regions[i] == true){
//
//					cmd = scriptsDir + medicaid_counts_elig_script;
//					System.out.println(cmd);
//					
//						ProcessBuilder pb = new ProcessBuilder(cmd, Date, getRegionKey(i));
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(Regions[i] == true){
//					if(exit_code[i] == 0){
//						System.out.println("Medicaid Counts Eligible Report for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("Medicaid Counts Eligible Report for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Reports();
//	}
//	
//	private static void pica_enroll(){
//		System.out.println("\t-+-PICA Enroll Report-+-");
//		System.out.println("Loading...\n");
//		
//		String Date;
//		
//		while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			Date = read.nextLine();
//			System.out.println("User input: " + Date);
//				if(valido.valid_date(Date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//		}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String all_regions = read.nextLine();
//		System.out.println("User input: " + all_regions);
//		
//		while(!(all_regions.equalsIgnoreCase("y") || all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			all_regions = read.nextLine();
//			System.out.println("User input: " + all_regions);
//		}
//		
//		boolean[] Regions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				Regions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(Regions[0] == true){System.out.println("Region already entered\n");}
//							Regions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(Regions[1] == true){System.out.println("Region already entered\n");}
//							Regions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(Regions[2] == true){System.out.println("Region already entered\n");}
//							Regions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(Regions[3] == true){System.out.println("Region already entered\n");}
//							Regions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(Regions[4] == true){System.out.println("Region already entered\n");}
//							Regions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(Regions[5] == true){System.out.println("Region already entered\n");}
//							Regions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(Regions[6] == true){System.out.println("Region already entered\n");}
//							Regions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(Regions[7] == true){System.out.println("Region already entered\n");}
//							Regions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(Regions[8] == true){System.out.println("Region already entered\n");}
//							Regions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
//		
////Output
//		System.out.println();
//		System.out.println("Update date: " + Date);
//		System.out.println("Regions: ");
//		for(int i = 0; i<Regions.length; i++){
//			if(Regions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute PICA Enroll Report? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute PICA Enroll Report? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Reports();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] exit_code = new int[9];
//		
//			for(int i = 0; i<Regions.length; i++){
//				if(Regions[i] == true){
//
//					cmd = scriptsDir + pica_enroll_script;
//					System.out.println(cmd);
//					
//						ProcessBuilder pb = new ProcessBuilder(cmd, Date, getRegionKey(i));
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(Regions[i] == true){
//					if(exit_code[i] == 0){
//						System.out.println("PICA Enroll Report for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("PICA Enroll Report for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Reports();
//	}
//	
//	private static void pam_monthly(){
//		System.out.println("\t-+-PAM Monthly Report-+-");
//		System.out.println("Loading...\n");
//		
//		String Date;
//		
//		while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			Date = read.nextLine();
//			System.out.println("User input: " + Date);
//				if(valido.valid_date(Date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//		}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String all_regions = read.nextLine();
//		System.out.println("User input: " + all_regions);
//		
//		while(!(all_regions.equalsIgnoreCase("y") || all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			all_regions = read.nextLine();
//			System.out.println("User input: " + all_regions);
//		}
//		
//		boolean[] Regions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				Regions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(Regions[0] == true){System.out.println("Region already entered\n");}
//							Regions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(Regions[1] == true){System.out.println("Region already entered\n");}
//							Regions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(Regions[2] == true){System.out.println("Region already entered\n");}
//							Regions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(Regions[3] == true){System.out.println("Region already entered\n");}
//							Regions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(Regions[4] == true){System.out.println("Region already entered\n");}
//							Regions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(Regions[5] == true){System.out.println("Region already entered\n");}
//							Regions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(Regions[6] == true){System.out.println("Region already entered\n");}
//							Regions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(Regions[7] == true){System.out.println("Region already entered\n");}
//							Regions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(Regions[8] == true){System.out.println("Region already entered\n");}
//							Regions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
//		
////Output
//		System.out.println();
//		System.out.println("Update date: " + Date);
//		System.out.println("Regions: ");
//		for(int i = 0; i<Regions.length; i++){
//			if(Regions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute PAM Monthly Report? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute PAM Monthly Report? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Reports();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] exit_code = new int[9];
//		
//			for(int i = 0; i<Regions.length; i++){
//				if(Regions[i] == true){
//
//					cmd = scriptsDir + pam_monthly_script;
//					System.out.println(cmd);
//					
//						ProcessBuilder pb = new ProcessBuilder(cmd, Date, getRegionKey(i));
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(Regions[i] == true){
//					if(exit_code[i] == 0){
//						System.out.println("PAM Monthly Report for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("PAM Monthly Report for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Reports();
//	}
//	
//	private static void hacienda_master_file(){
//		
//		System.out.println("\t-+-Hacienda Master File Report-+-");
//		
//		String cmd1 = "ls " + installDir + "other/hacienda/ | grep -i \"^ha\".*\"\\.dat$\"";
//		ProcessBuilder pb1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
//		pb1.redirectErrorStream(true);
//	        try {
//	            Process p1 = pb1.start();
//	            String s;
//	            BufferedReader stdout = new BufferedReader (
//	                new InputStreamReader(p1.getInputStream()));
//	            while ((s = stdout.readLine()) != null) {
//	            	dat_master_Files_list.add(s);
//	            }
//	            p1.getInputStream().close();
//	            p1.getOutputStream().close();
//	            p1.getErrorStream().close();
//	         } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//	        
//	        if(dat_master_Files_list.size() == 0){
//	        	System.out.println("");
//	        	System.out.println("No Hacienda Master Files to process");
//	        	System.out.println("\nReseting...\n");
//	        	dat_master_Files_list.clear();
//	        	Reports();
//	        }
//	        
//	        String pydat_master_Files[] = convertToStringArray(dat_master_Files_list.toArray());
//	        dat_master_Files_list.clear();
//	        
//	        System.out.println("");
//	        System.out.println("Hacienda Master Files to process (ha******.dat): ");
//	        for(int i = 0; i<pydat_master_Files.length; i++){
//	        	System.out.println("\t " + pydat_master_Files[i]);
//	        }
//	        
//	        System.out.println();
//	        System.out.println("Execute Hacienda Master File report with these files? [y/n] ");
//			String execute = read.nextLine();
//			System.out.println("User input: " + execute);
//			
//			while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//				System.out.println("Invalid input: ");
//				System.out.println("Execute Hacienda Master File report with these files? [y/n] ");
//				execute = read.nextLine();
//				System.out.println("User input: " + execute);
//			}
//			
//			if(execute.equalsIgnoreCase("n")){
//				Reports();
//			}
//			
//	        System.out.println();
//	    	String hacienda_master_file_date;
//			while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			hacienda_master_file_date = read.nextLine();
//			System.out.println("User input: " + hacienda_master_file_date);
//				if(valido.valid_date(hacienda_master_file_date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//			}
//			
//			int hacienda_master_Files_exit_code[] = new int[pydat_master_Files.length];
//			String filesString = "";
//			for(int i = 0; i<pydat_master_Files.length; i++){
//				filesString = pydat_master_Files[i];
//
//				String cmd2 = scriptsDir + hacienda_master_file_script + " " + hacienda_master_file_date + " " + filesString;
//				ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
//				
//					pb2.redirectErrorStream(true);
//				        try {
//				            Process p2 = pb2.start();
//				            String s;
//				            BufferedReader stdout = new BufferedReader (
//				                new InputStreamReader(p2.getInputStream()));
//				            while ((s = stdout.readLine()) != null) {
//				                System.out.println(s);
//				            }
//				            hacienda_master_Files_exit_code[i] = p2.waitFor();
//				            p2.getInputStream().close();
//				            p2.getOutputStream().close();
//				            p2.getErrorStream().close();
//				         } catch (Exception ex) {
//				            ex.printStackTrace();
//				        }
//				}
//			System.out.println("");
//			for(int i = 0; i<pydat_master_Files.length; i++){
//					if(hacienda_master_Files_exit_code[i] == 0){
//						System.out.println("Hacienda Master File report completed successfully for file: " + pydat_master_Files[i]);
//					}else{
//						System.out.println("Hacienda Master File report failed for file: " + pydat_master_Files[i]);
//					}
//			}
//			System.out.println("");
//		Other();
//	}
//	
//	private static void hacienda_payment_file(){
//		System.out.println("\t-+-Hacienda Payment File Report-+-");
//		
//		String cmd1 = "ls " + installDir + "other/hacienda/ | grep -i \"^py\".*\"\\.dat$\"";
//		ProcessBuilder pb1 = new ProcessBuilder("/bin/bash", "-c", cmd1);
//		pb1.redirectErrorStream(true);
//	        try {
//	            Process p1 = pb1.start();
//	            String s;
//	            BufferedReader stdout = new BufferedReader (
//	                new InputStreamReader(p1.getInputStream()));
//	            while ((s = stdout.readLine()) != null) {
//	            	dat_payment_Files_list.add(s);
//	            }
//	            p1.getInputStream().close();
//	            p1.getOutputStream().close();
//	            p1.getErrorStream().close();
//	         } catch (Exception ex) {
//	            ex.printStackTrace();
//	        }
//	        
//	        if(dat_payment_Files_list.size() == 0){
//	        	System.out.println("");
//	        	System.out.println("No Hacienda Master Files to process");
//	        	System.out.println("\nReseting...\n");
//	        	dat_payment_Files_list.clear();
//	        	Reports();
//	        }
//	        
//	        String pydat_payment_Files[] = convertToStringArray(dat_payment_Files_list.toArray());
//	        dat_payment_Files_list.clear();
//	        
//	        System.out.println("");
//	        System.out.println("Hacienda Payment Files to process (py******.dat): ");
//	        for(int i = 0; i<pydat_payment_Files.length; i++){
//	        	System.out.println("\t " + pydat_payment_Files[i]);
//	        }
//	        
//	        System.out.println();
//	        System.out.println("Execute Hacienda Payment File report with these files? [y/n] ");
//			String execute = read.nextLine();
//			System.out.println("User input: " + execute);
//			
//			while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//				System.out.println("Invalid input: ");
//				System.out.println("Execute Hacienda Payment File report with these files? [y/n] ");
//				execute = read.nextLine();
//				System.out.println("User input: " + execute);
//			}
//			
//			if(execute.equalsIgnoreCase("n")){
//				Reports();
//			}
//			
//	        System.out.println();
//	    	String hacienda_payment_file_date;
//			while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			hacienda_payment_file_date = read.nextLine();
//			System.out.println("User input: " + hacienda_payment_file_date);
//				if(valido.valid_date(hacienda_payment_file_date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//			}
//			
//			int hacienda_payment_Files_exit_code[] = new int[pydat_payment_Files.length];
//			String filesString = "";
//			for(int i = 0; i<pydat_payment_Files.length; i++){
//				filesString = pydat_payment_Files[i];
//
//				String cmd2 = scriptsDir + hacienda_payment_file_script + " " + hacienda_payment_file_date + " " + filesString;
//				ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
//				
//					pb2.redirectErrorStream(true);
//				        try {
//				            Process p2 = pb2.start();
//				            String s;
//				            BufferedReader stdout = new BufferedReader (
//				                new InputStreamReader(p2.getInputStream()));
//				            while ((s = stdout.readLine()) != null) {
//				                System.out.println(s);
//				            }
//				            hacienda_payment_Files_exit_code[i] = p2.waitFor();
//				            p2.getInputStream().close();
//				            p2.getOutputStream().close();
//				            p2.getErrorStream().close();
//				         } catch (Exception ex) {
//				            ex.printStackTrace();
//				        }
//				}
//			System.out.println("");
//			for(int i = 0; i<pydat_payment_Files.length; i++){
//					if(hacienda_payment_Files_exit_code[i] == 0){
//						System.out.println("Hacienda Payment File report completed successfully for file: " + pydat_payment_Files[i]);
//					}else{
//						System.out.println("Hacienda Payment File report failed for file: " + pydat_payment_Files[i]);
//					}
//			}
//			System.out.println("");
//		Reports();
//	}
//
//	private static void ela_discount(){
//		System.out.println("\t-+-ELA Discount Report-+-");
//		System.out.println("Loading...\n");
//		
//		String Date;
//		
//		while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			Date = read.nextLine();
//			System.out.println("User input: " + Date);
//				if(valido.valid_date(Date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//		}
//
////Output
//		System.out.println();
//		System.out.println("Update date: " + Date);
//		
//		System.out.println();
//		
//		System.out.println("Execute ELA Discount Report? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute ELA Discount Report? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Reports();
//		}
//		
//		String cmd = null;
//		cmd = scriptsDir + ela_discount_script;
//		System.out.println(cmd);
//		
//			ProcessBuilder pb = new ProcessBuilder(cmd, Date);
//			pb.redirectErrorStream(true);
//		        try {
//		            Process p = pb.start();
//		            String s;
//		            BufferedReader stdout = new BufferedReader (
//		                new InputStreamReader(p.getInputStream()));
//		            while ((s = stdout.readLine()) != null) {
//		                System.out.println(s);
//		            }
//		            p.getInputStream().close();
//		            p.getOutputStream().close();
//		            p.getErrorStream().close();
//		         } catch (Exception ex) {
//		            ex.printStackTrace();
//		        }
//		//System.out.println("Done.");	        
//		Other();
//	}
//
//	private static void export_all(){
//		System.out.println("\t-+-Export All-+-");
//		System.out.println("Loading...\n");
//		
//		String Date;
//		
//		while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			Date = read.nextLine();
//			System.out.println("User input: " + Date);
//				if(valido.valid_date(Date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//		}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String all_regions = read.nextLine();
//		System.out.println("User input: " + all_regions);
//		
//		while(!(all_regions.equalsIgnoreCase("y") || all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			all_regions = read.nextLine();
//			System.out.println("User input: " + all_regions);
//		}
//		
//		boolean[] Regions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				Regions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(Regions[0] == true){System.out.println("Region already entered\n");}
//							Regions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(Regions[1] == true){System.out.println("Region already entered\n");}
//							Regions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(Regions[2] == true){System.out.println("Region already entered\n");}
//							Regions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(Regions[3] == true){System.out.println("Region already entered\n");}
//							Regions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(Regions[4] == true){System.out.println("Region already entered\n");}
//							Regions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(Regions[5] == true){System.out.println("Region already entered\n");}
//							Regions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(Regions[6] == true){System.out.println("Region already entered\n");}
//							Regions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(Regions[7] == true){System.out.println("Region already entered\n");}
//							Regions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(Regions[8] == true){System.out.println("Region already entered\n");}
//							Regions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
//		
////Output
//		System.out.println();
//		System.out.println("Update date: " + Date);
//		System.out.println("Regions: ");
//		for(int i = 0; i<Regions.length; i++){
//			if(Regions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute Export All? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute Export All? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Reports();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] exit_code = new int[9];
//		
//			for(int i = 0; i<Regions.length; i++){
//				if(Regions[i] == true){
//
//					cmd = scriptsDir + export_all_script;
//					System.out.println(cmd);
//					
//						ProcessBuilder pb = new ProcessBuilder(cmd, Date, getRegionKey(i));
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(Regions[i] == true){
//					if(exit_code[i] == 0){
//						System.out.println("Export All for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("Export All for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Other();
//	}
//	
//	private static void mbho_enroll(){
//		System.out.println("\t-+-MBHO Enroll Report-+-");
//		System.out.println("Loading...\n");
//		
//		String Date;
//		
//		while(true){
//			System.out.println("Enter update date (yyyy-mm-dd): ");
//			Date = read.nextLine();
//			System.out.println("User input: " + Date);
//				if(valido.valid_date(Date, "yyyy-MM-dd")){
//					break;
//				}else{
//					System.out.println("Invalid date format");
//				}
//		}
//		
//		System.out.println("Run all regions? [y/n] ");
//		String all_regions = read.nextLine();
//		System.out.println("User input: " + all_regions);
//		
//		while(!(all_regions.equalsIgnoreCase("y") || all_regions.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Run all regions? [y/n] ");
//			all_regions = read.nextLine();
//			System.out.println("User input: " + all_regions);
//		}
//		
//		boolean[] Regions = new boolean[9];
//		boolean hasRegions = false;
//		
//		if(all_regions.equalsIgnoreCase("y")){
//			for(int i = 0; i<9; i++){
//				Regions[i] = true;
//			}
//		}else{
//
//			System.out.println("Enter region letters to process (or enter stop to end): ");
//			String input = read.nextLine();
//			System.out.println("User input: " + input);
//			
//			while(true){
//				if(input.equalsIgnoreCase("stop") && hasRegions == false){
//					System.out.println("No regions entered");
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//				}else{
//					break;
//				}
//			}
//			
//			int regionIndex = -1;
//			while(!input.equalsIgnoreCase("stop")){
//					
//					try { //used to avoid an empty input
//					@SuppressWarnings("unused")
//					char in = (char)input.charAt(0); 
//					regionIndex = getRegionKeyIndex(input);
//					}catch(StringIndexOutOfBoundsException e) {} 
//	
//					switch (regionIndex){
//						case 0:
//							if(Regions[0] == true){System.out.println("Region already entered\n");}
//							Regions[0] = true;
//							hasRegions = true;
//							break;
//						case 1:
//							if(Regions[1] == true){System.out.println("Region already entered\n");}
//							Regions[1] = true;
//							hasRegions = true;
//							break;
//						case 2:
//							if(Regions[2] == true){System.out.println("Region already entered\n");}
//							Regions[2] = true;
//							hasRegions = true;
//							break;
//						case 3:
//							if(Regions[3] == true){System.out.println("Region already entered\n");}
//							Regions[3] = true;
//							hasRegions = true;
//							break;
//						case 4:
//							if(Regions[4] == true){System.out.println("Region already entered\n");}
//							Regions[4] = true;
//							hasRegions = true;
//							break;
//						case 5:
//							if(Regions[5] == true){System.out.println("Region already entered\n");}
//							Regions[5] = true;
//							hasRegions = true;
//							break;
//						case 6:
//							if(Regions[6] == true){System.out.println("Region already entered\n");}
//							Regions[6] = true;
//							hasRegions = true;
//							break;
//						case 7:
//							if(Regions[7] == true){System.out.println("Region already entered\n");}
//							Regions[7] = true;
//							hasRegions = true;
//							break;
//						case 8:
//							if(Regions[8] == true){System.out.println("Region already entered\n");}
//							Regions[8] = true;
//							hasRegions = true;
//							break;
//		
//					default: System.out.println("Invalid region");
//						break;
//	
//				}
//					
//					System.out.println("Enter region letters to process (or enter stop to end): ");
//					input = read.nextLine();
//					System.out.println("User input: " + input);
//					
//					if(input.equalsIgnoreCase("stop") && hasRegions == false){
//						System.out.println("No regions entered");
//						System.out.println("Enter region letters to process (or enter stop to end): ");
//						input = read.nextLine();
//						System.out.println("User input: " + input);
//					}
//		
//			}
//			
//		}
//		
////Output
//		System.out.println();
//		System.out.println("Update date: " + Date);
//		System.out.println("Regions: ");
//		for(int i = 0; i<Regions.length; i++){
//			if(Regions[i] == true){
//				System.out.println("\t" + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ")");
//			}
//		}
//		System.out.println();
//		
//		System.out.println("Execute MBHO Enroll Report? [y/n] ");
//		String execute = read.nextLine();
//		System.out.println("User input: " + execute);
//		
//		while(!(execute.equalsIgnoreCase("y") || execute.equalsIgnoreCase("n"))){
//			System.out.println("Invalid input: ");
//			System.out.println("Execute MBHO Enroll Report? [y/n] ");
//			execute = read.nextLine();
//			System.out.println("User input: " + execute);
//		}
//		
//		if(execute.equalsIgnoreCase("n")){
//			Reports();
//		}
//		
//		if(execute.equalsIgnoreCase("y")){
//		String cmd = null;
//		int[] exit_code = new int[9];
//		
//			for(int i = 0; i<Regions.length; i++){
//				if(Regions[i] == true){
//
//					cmd = scriptsDir + mbho_report_script;
//					System.out.println(cmd);
//					
//						ProcessBuilder pb = new ProcessBuilder(cmd, Date, getRegionKey(i));
//						pb.redirectErrorStream(true);
//					        try {
//					            Process p = pb.start();
//					            String s;
//					            BufferedReader stdout = new BufferedReader (
//					                new InputStreamReader(p.getInputStream()));
//					            while ((s = stdout.readLine()) != null) {
//					                System.out.println(s);
//					            }
//					            exit_code[i] = p.waitFor();
//					            p.getInputStream().close();
//					            p.getOutputStream().close();
//					            p.getErrorStream().close();
//					         } catch (Exception ex) {
//					            ex.printStackTrace();
//					        }
//				}
//			}
//			
//			System.out.println("");
//			for(int i = 0; i<9; i++){
//				if(Regions[i] == true){
//					if(exit_code[i] == 0){
//						System.out.println("MBHO Enroll Report for region " + getRegionKey(i) + " completed successfully");
//					}else{
//						System.out.println("MBHO Enroll Report for region " + getRegionKey(i) + " (" + getRegionName(getRegionKey(i)) + ") failed");
//					}
//				}
//			}
//			System.out.println("");
//			
//		}
//		//System.out.println("Done.");	        
//		Reports();
//	}
//	
//	private static boolean valid_time(String time){
//		Pattern ptrn = Pattern.compile("^[0-2][0-9][:][0-5][0-9]$");
//		Matcher p = ptrn.matcher(time);
//		return p.matches();
//	}
//	
//	private static int getRegionKeyIndex(String reg){
//		reg = reg.toUpperCase();
//		String regions = "ABEFGJPSZ";
//		return regions.indexOf(reg);
//	}
//	
//	private static String getRegionName(String regionKey){
//		String output="";
//		
//		int regionIndex = getRegionKeyIndex(regionKey);
//		
//		switch(regionIndex){
//			case 0: output = "Norte"; 
//				break;
//			case 1: output = "Metro"; 
//				break;
//			case 2: output = "Este"; 
//				break;
//			case 3: output = "Noreste"; 
//				break;
//			case 4: output = "Sureste"; 
//				break;
//			case 5: output = "San Juan"; 
//				break;
//			case 6: output = "Special"; 
//				break;
//			case 7: output = "Suroeste"; 
//				break;
//			case 8: output = "Oeste"; 
//				break;
//		}
//		return output;
//	}
//	
//	private static String getRegionKey(int region){
//		String output="";
//		
//		switch(region){
//			case 0: output = "A"; 
//				break;
//			case 1: output = "B"; 
//				break;
//			case 2: output = "E"; 
//				break;
//			case 3: output = "F"; 
//				break;
//			case 4: output = "G"; 
//				break;
//			case 5: output = "J"; 
//				break;
//			case 6: output = "P"; 
//				break;
//			case 7: output = "S"; 
//				break;
//			case 8: output = "Z"; 
//				break;
//		}
//		return output;
//	}
//	
//	private static String[] convertToStringArray(Object[] object){
//		String[] array = new String[object.length];
//		for(int i = 0; i<object.length; i++){
//			array[i]=(String) object[i];
//		}
//		return array;
//	}
//	
//	private static void stopProgramExecution(String methodName, String className, String message) {
//		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
//		StackTraceElement main = stack[stack.length - 1];
//		String programName = main.getClassName();
//		System.out.println("**************************************************************************************");
//		System.out.println("*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR**ERROR*ERROR*");
//		System.out.println("**************************************************************************************");
//		System.out.println("Error in program ---> " + programName);
//		System.out.println("Error in class   ---> " + className);
//		System.out.println("Error in method  ---> " + methodName);
//		System.out.println("Error message    ---> " + message);
//		System.out.println("(1) failed run ");
//		System.out.println("**************************************************************************************");
//		System.out.println("*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR*ERROR**ERROR*ERROR*");
//		System.out.println("**************************************************************************************");
//		
////		Mailer email = new Mailer(message);
////		email.send_Error();
//		
//		System.exit(1);
//	}
	
}
