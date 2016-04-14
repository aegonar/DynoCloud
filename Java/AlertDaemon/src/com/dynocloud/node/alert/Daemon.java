package com.dynocloud.node.alert;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Daemon {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;	
	
	public static void main (String[] args) {

		link.Open_link();
					
			try{
				String query_getEnclosures = "SELECT * FROM EnclosureNode;";
				prep_sql = link.linea.prepareStatement(query_getEnclosures);			
			
				ResultSet rs_query_getEnclosures= prep_sql.executeQuery();
				
					while(rs_query_getEnclosures.next()){
		
						System.out.println("EnclosureNodeID: " + rs_query_getEnclosures.getInt("EnclosureNodeID"));		
						int EnclosureNodeID = rs_query_getEnclosures.getInt("EnclosureNodeID");
						
						try{
							String query_getProfile= "SELECT * FROM EnclosureNode, PetProfiles "
									+ "WHERE EnclosureNode.PetProfileID=PetProfiles.PetProfileID AND EnclosureNode.EnclosureNodeID=?;";
							
							prep_sql = link.linea.prepareStatement(query_getProfile);
														
							prep_sql.setInt(1, EnclosureNodeID);
				
							ResultSet rs_query_getProfile= prep_sql.executeQuery();
							
							if (!rs_query_getProfile.next() ) {
								System.out.println("rs_query_getProfile no data");
								link.Close_link();	
							} else {
								
								int PetProfileID = rs_query_getProfile.getInt("PetProfileID");
								float temp = rs_query_getProfile.getFloat("Temperature_TH");
								float hum = rs_query_getProfile.getFloat("Humidity_TH");
								float dayTemp = rs_query_getProfile.getFloat("Day_Temperature_SP");
								float dayHum = rs_query_getProfile.getFloat("Day_Humidity_SP");
								
								System.out.println("\tPetProfileID: " + PetProfileID + " temp: " + temp + " hum " + hum + " dayTemp: " + dayTemp + " dayHum: " + dayHum);
								
								try{
									  LocalDateTime now = LocalDateTime.now();;		
								      LocalDateTime past = now.minus(1, ChronoUnit.MINUTES);
								      LocalDateTime nowInc = now.plus(1, ChronoUnit.MINUTES);
									
									String query_metrics = "SELECT * FROM Telemetry where `EnclosureNodeID` = ? AND `DateTime`  >=  ?  AND `DateTime` < ?;";
									prep_sql = link.linea.prepareStatement(query_metrics);
									
									prep_sql.setInt(1, EnclosureNodeID);
									prep_sql.setString(2, past+"");
									prep_sql.setString(3, nowInc+"");
									
									ResultSet rs_query_metrics= prep_sql.executeQuery();
									
									float tempAvg=0;
									float humAvg=0;
									int count=0;
									
									while(rs_query_metrics.next()){
										tempAvg += rs_query_metrics.getFloat("Temperature");
										humAvg += rs_query_metrics.getFloat("Humidity");
										count++;										
									} 
									
									System.out.println("\tRecords: " + count);
									
									tempAvg /= count;
									humAvg /= count;
//									System.out.println("tempAvg: " + tempAvg);
//									System.out.println("humAvg: " + humAvg);
									
									if(tempAvg >= temp*1.03 || tempAvg < temp*0.97){
										System.out.println("\tAlert! Temperate out of range: " + tempAvg);
									}else{
										System.out.println("\tTemperate ok: " + tempAvg);
									}
									
									if(humAvg >= temp*1.03 || humAvg < temp*0.97){
										System.out.println("\tAlert! Humidity out of range: " + tempAvg);
									}else{
										System.out.println("\tHumidity ok: " + tempAvg);
									}
								
									
								}catch(Exception e){

									System.out.println("Error: " + e.getMessage());
									
									link.Close_link();
				
								}

							}
							
						}catch(Exception e){

							System.out.println("Error: " + e.getMessage());
							
							link.Close_link();
		
						}

					}
			}catch(Exception e){

				System.out.println("Error: " + e.getMessage());
				
				link.Close_link();
	
			}

		link.Close_link();
	  }

}
