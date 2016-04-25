package com.dynocloud.node.api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//import com.dynocloud.server.api.Overview;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/overview")
public class OverviewResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOverview() {
      
      System.out.println("[GET] /overview");
	  
//	  link.Open_link();
//		
//	  ArrayList<Overview> OverviewList = new ArrayList<Overview>();
//		
//		try{
//			String query_getEnclosures = "SELECT * FROM EnclosureNode";
//			prep_sql = link.linea.prepareStatement(query_getEnclosures);
//			
//			ResultSet rs_query_getEnclosures= prep_sql.executeQuery();
//			
//				while(rs_query_getEnclosures.next()){
//	
//					//System.out.println("EnclosureNodeIDs: " + rs_query_getEnclosures.getInt("EnclosureNodeID"));
//						
//					Overview overview = new Overview();
//					
//					try{
//						String query_getOverview= "SELECT Telemetry.EnclosureNodeID, max(DateTime) as DateTime, TEMP, RH, "
//								+ "EnclosureNode.OPTIONAL_LOAD as OPTIONAL_LOAD_TYPE, HEAT_LOAD, UV_STATUS, HUM_STATUS, HEAT_STATUS, OPTIONAL_STATUS, HUM_OR, HEAT_OR, UV_OR, OPTIONAL_OR, EnclosureNode.Name as EnclosureName, EnclosureNode.PetProfileID, "
//								+ "Online, Telemetry.OPTIONAL_LOAD FROM Telemetry, EnclosureNode, PetProfiles "
//								+ "WHERE EnclosureNode.EnclosureNodeID=Telemetry.EnclosureNodeID "
//								+ "AND EnclosureNode.PetProfileID=PetProfiles.PetProfileID AND Telemetry.EnclosureNodeID=? AND Telemetry.DateTime=DateTime GROUP BY DateTime;";
//						
//						
////						SELECT *
////						FROM Telemetry t
////						INNER JOIN
////						    (SELECT EnclosureNodeID, MAX(DateTime) AS MaxDateTime
////						    FROM Telemetry WHERE EnclosureNodeID=2) latest 
////						ON t.EnclosureNodeID = latest.EnclosureNodeID 
////						AND t.DateTime = latest.MaxDateTime
//						
//						prep_sql = link.linea.prepareStatement(query_getOverview);
//						
//						prep_sql.setInt(1, rs_query_getEnclosures.getInt("EnclosureNodeID"));
//																		
//						ResultSet rs_query_getOverview= prep_sql.executeQuery();
//						
//						if (!rs_query_getOverview.next() ) {
//							System.out.println("rs_query_getOverview no data");
//							link.Close_link();
//							return Response.status(Response.Status.FORBIDDEN).entity("Module not found").build();	
//						} else {
//							
//							
//							
//							overview.setTEMP(rs_query_getOverview.getFloat("TEMP"));
//							overview.setRH(rs_query_getOverview.getFloat("RH"));
//							overview.setOPTIONAL_LOAD(rs_query_getOverview.getFloat("OPTIONAL_LOAD"));
//							overview.setHEAT_LOAD(rs_query_getOverview.getFloat("HEAT_LOAD"));
//							overview.setHUM_STATUS(rs_query_getOverview.getInt("HUM_STATUS"));
//							overview.setUV_STATUS(rs_query_getOverview.getInt("UV_STATUS"));
//							
//							
////							overview.setTemperature(rs_query_getOverview.getFloat("Temperature"));
////							overview.setHumidity(rs_query_getOverview.getFloat("Humidity"));
////							overview.setLoad_IR(rs_query_getOverview.getFloat("Load_IR"));
////							overview.setLoad_IC(rs_query_getOverview.getFloat("Load_IC"));
//							
//							overview.setEnclosureNodeID(rs_query_getOverview.getInt("EnclosureNodeID"));
//							overview.setOnline(rs_query_getOverview.getBoolean("Online"));
//							
////							overview.setState_UV(rs_query_getOverview.getInt("State_UV"));
////							overview.setState_HUM(rs_query_getOverview.getInt("State_HUM"));
//							overview.setPetProfileID(rs_query_getOverview.getString("PetProfileID"));
//
//							overview.setEnclosureName(rs_query_getOverview.getString("EnclosureName"));
////							overview.setProfileName(rs_query_getOverview.getString("ProfileName"));
//							
//							
//							overview.setEnclosureName(rs_query_getOverview.getString("Name"));
//							overview.setOPTIONAL_LOAD_TYPE(rs_query_getOverview.getInt("OPTIONAL_LOAD_TYPE"));
//
//							overview.setHUM_OR(rs_query_getOverview.getInt("HUM_OR"));
//							overview.setHEAT_OR(rs_query_getOverview.getInt("HEAT_OR"));
//							overview.setUV_OR(rs_query_getOverview.getInt("UV_OR"));
//							overview.setOPTIONAL_OR(rs_query_getOverview.getInt("OPTIONAL_OR"));
//							
//
//							overview.setHEAT_STATUS(rs_query_getOverview.getInt("HEAT_STATUS"));
//							overview.setOPTIONAL_STATUS(rs_query_getOverview.getInt("OPTIONAL_STATUS"));
//							
//							
//							Timestamp myTimestamp = rs_query_getOverview.getTimestamp("DateTime");
//							String S = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(myTimestamp);			
//							overview.setDateTime(S);
//							
//							
//							OverviewList.add(overview);
//						}
//						
//					}catch(Exception e){
//
//						System.out.println("Error: " + e.getMessage());
//						
//						link.Close_link();
//						
//						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();
//						
//					}
//
//				}
//		}catch(Exception e){
//
//			System.out.println("Error: " + e.getMessage());
//			
//			link.Close_link();
//			
//			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();
//			
//		}
//
//	link.Close_link();
//	
//	ObjectMapper mapper = new ObjectMapper();
//	String jsonString = null;
//	
//	try {
//		jsonString = mapper.writeValueAsString(OverviewList);
//		
//	} catch (JsonProcessingException e) {
//		
//		System.out.println("Error mapping to json: " + e.getMessage());
//		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
//	}
//
//  return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
//  
//  }
      
      link.Open_link();
		
	  ArrayList<Overview> OverviewList = new ArrayList<Overview>();
		
		try{
			String query_getEnclosures = "SELECT * FROM EnclosureNode;";// WHERE UserID=? AND CentralNodeID=?";
			prep_sql = link.linea.prepareStatement(query_getEnclosures);
			
//			prep_sql.setInt(1, userID);
//			prep_sql.setInt(2, CentralNodeID);
		
			ResultSet rs_query_getEnclosures= prep_sql.executeQuery();
			
				while(rs_query_getEnclosures.next()){
	
					//System.out.println("EnclosureNodeIDs: " + rs_query_getEnclosures.getInt("EnclosureNodeID"));
						
					Overview overview = new Overview();
					
					try{
						String query_getLatest = "SELECT * FROM Telemetry t "
								+ "INNER JOIN "
								+ "(SELECT EnclosureNodeID, MAX(DateTime) AS MaxDateTime "
								//+ "FROM Telemetry WHERE EnclosureNodeID=? AND CentralNodeID=? AND UserID=?) latest "
								+ "FROM Telemetry WHERE EnclosureNodeID=?) latest "
								+ "ON t.EnclosureNodeID = latest.EnclosureNodeID "
								+ "AND t.DateTime = latest.MaxDateTime;";
						
						prep_sql = link.linea.prepareStatement(query_getLatest);
						
						prep_sql.setInt(1, rs_query_getEnclosures.getInt("EnclosureNodeID"));
//						prep_sql.setInt(2, CentralNodeID);
//						prep_sql.setInt(3, userID);
																		
						ResultSet rs_query_getLatest= prep_sql.executeQuery();
						
						if (!rs_query_getLatest.next() ) {
							System.out.println("rs_query_getOverview no data");
							link.Close_link();
							return Response.status(Response.Status.FORBIDDEN).entity("Module not found").build();	
						} else {
							
							
							overview.setTEMP(rs_query_getLatest.getFloat("TEMP"));
							overview.setRH(rs_query_getLatest.getFloat("RH"));
							overview.setOPTIONAL_LOAD(rs_query_getLatest.getFloat("OPTIONAL_LOAD"));
							overview.setHEAT_LOAD(rs_query_getLatest.getFloat("HEAT_LOAD"));
							overview.setHUM_STATUS(rs_query_getLatest.getInt("HUM_STATUS"));
							overview.setUV_STATUS(rs_query_getLatest.getInt("UV_STATUS"));
							
//							overview.setCentralNodeID(rs_query_getLatest.getInt("CentralNodeID"));
//							overview.setUserID(rs_query_getLatest.getInt("UserID"));
							overview.setEnclosureNodeID(rs_query_getLatest.getInt("EnclosureNodeID"));
							
							overview.setOnline(rs_query_getEnclosures.getBoolean("Online"));
							
							overview.setEnclosureName(rs_query_getEnclosures.getString("Name"));
							overview.setOPTIONAL_LOAD_TYPE(rs_query_getEnclosures.getInt("OPTIONAL_LOAD"));

							overview.setHUM_OR(rs_query_getLatest.getInt("HUM_OR"));
							overview.setHEAT_OR(rs_query_getLatest.getInt("HEAT_OR"));
							overview.setUV_OR(rs_query_getLatest.getInt("UV_OR"));
							overview.setOPTIONAL_OR(rs_query_getLatest.getInt("OPTIONAL_OR"));
							

							overview.setHEAT_STATUS(rs_query_getLatest.getInt("HEAT_STATUS"));
							overview.setOPTIONAL_STATUS(rs_query_getLatest.getInt("OPTIONAL_STATUS"));

							Timestamp myTimestamp = rs_query_getLatest.getTimestamp("DateTime");
							String S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myTimestamp);			
							overview.setDateTime(S);
							
							
							OverviewList.add(overview);
						}
						
						
						//try{
							String query_getProfile= "SELECT * FROM PetProfiles WHERE PetProfileID=?;";// AND UserID=?;";
							
							prep_sql = link.linea.prepareStatement(query_getProfile);
							
							prep_sql.setString(1, rs_query_getEnclosures.getString("PetProfileID"));
							//prep_sql.setInt(2, userID);
																			
							ResultSet rs_query_getProfile= prep_sql.executeQuery();
							
							if (!rs_query_getProfile.next() ) {
								System.out.println("rs_query_getProfile no data");
								link.Close_link();
								return Response.status(Response.Status.FORBIDDEN).entity("Profile not found").build();	
							} else {
								overview.setPetProfileID(rs_query_getProfile.getString("PetProfileID"));
								//overview.setProfileName(rs_query_getProfile.getString("Name"));
								
								overview.setDay_Temperature_SP(rs_query_getProfile.getFloat("day_Temperature_SP"));
								overview.setDay_Humidity_SP(rs_query_getProfile.getFloat("day_Humidity_SP"));
								overview.setNight_Temperature_SP(rs_query_getProfile.getFloat("night_Temperature_SP"));
								overview.setNight_Humidity_SP(rs_query_getProfile.getFloat("night_Humidity_SP"));
								overview.setTemperature_TH(rs_query_getProfile.getFloat("temperature_TH"));
								overview.setHumidity_TH(rs_query_getProfile.getFloat("humidity_TH"));		
								
							}																
							
					}catch(Exception e){

						System.out.println("Error: " + e.getMessage());
						
						link.Close_link();
						
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();
						
					}

				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();
			
		}

	link.Close_link();
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonString = null;
	
	try {
		jsonString = mapper.writeValueAsString(OverviewList);
		
	} catch (JsonProcessingException e) {
		
		System.out.println("Error mapping to json: " + e.getMessage());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
	}

  return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
  
  }

} 