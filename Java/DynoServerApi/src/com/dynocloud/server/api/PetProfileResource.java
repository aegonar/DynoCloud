package com.dynocloud.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/profiles")
public class PetProfileResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	@Logged
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfiles(@Context HttpHeaders headers) {

	  Session session = new Session(headers);
      User currentUser = session.getUser();
        
      int userID=currentUser.getUserID();
      
      System.out.println("["+currentUser.getUserName()+"] [GET] /profiles");
	  
	  link.Open_link();
		
	  ArrayList<PetProfile> list = new ArrayList<PetProfile>();
		
		try{
			String query_getProfiles = "SELECT * FROM PetProfiles where `UserID` = ?";
			prep_sql = link.linea.prepareStatement(query_getProfiles);
			
			prep_sql.setInt(1, userID);
			
			ResultSet rs_query_getProfiles= prep_sql.executeQuery();
			
				while(rs_query_getProfiles.next()){
					
					PetProfile profile = new PetProfile();
							
					profile.setPetProfileID(rs_query_getProfiles.getString("PetProfileID"));
					profile.setUserID(rs_query_getProfiles.getInt("UserID"));
					//profile.setName(rs_query_getProfiles.getString("Name"));
					profile.setDay_Temperature_SP(rs_query_getProfiles.getFloat("Day_Temperature_SP"));
					profile.setDay_Humidity_SP(rs_query_getProfiles.getFloat("Day_Humidity_SP"));
					profile.setNight_Temperature_SP(rs_query_getProfiles.getFloat("Night_Temperature_SP"));
					profile.setNight_Humidity_SP(rs_query_getProfiles.getFloat("Night_Humidity_SP"));
					profile.setTemperature_TH(rs_query_getProfiles.getFloat("Temperature_TH"));
					profile.setHumidity_TH(rs_query_getProfiles.getFloat("Humidity_TH"));

					profile.setDayTime(rs_query_getProfiles.getString("DayTime"));
					profile.setNightTime(rs_query_getProfiles.getString("NightTime"));
					
					list.add(profile);

				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading profiles").build();
			
		}

	link.Close_link();
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonString = null;
	
	try {
		jsonString = mapper.writeValueAsString(list);
		
	} catch (JsonProcessingException e) {
		
		System.out.println("Error mapping to json: " + e.getMessage());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
	}

  return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
  
  }
  
	@Logged
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postProfile(PetProfile profile, @Context HttpHeaders headers) {
	  	  
	  Session session = new Session(headers);
      User currentUser = session.getUser();
      int userID = currentUser.getUserID(); 
        	  
      System.out.println("["+currentUser.getUserName()+"] [POST] /profiles");
      
	  link.Open_link();
			
		try{
			String query_postProfile = "INSERT INTO PetProfiles (`UserID`,`PetProfileID`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`,`DayTime`,`NightTime`) VALUES (?,?,?,?,?,?,?,?,?,?);";
			
			prep_sql = link.linea.prepareStatement(query_postProfile);
			
			prep_sql.setInt(1, userID);
			prep_sql.setString(2, profile.getPetProfileID());
			prep_sql.setFloat(3, profile.getDay_Temperature_SP());
			prep_sql.setFloat(4, profile.getDay_Humidity_SP());
			prep_sql.setFloat(5, profile.getNight_Temperature_SP());
			prep_sql.setFloat(6, profile.getNight_Humidity_SP());
			prep_sql.setFloat(7, profile.getTemperature_TH());
			prep_sql.setFloat(8, profile.getHumidity_TH());
			prep_sql.setString(8, profile.getDayTime());
			prep_sql.setString(9, profile.getNightTime());
			
			prep_sql.executeUpdate();

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.CONFLICT).entity("Profile exists").build();
			
		}

	link.Close_link();
	
	SendToCentralNode sendToCentralNode = new SendToCentralNode(profile, "POST", "profiles");
	sendToCentralNode.sendToUser(userID);
	
	return Response.status(Response.Status.OK).build();
  
  }
	
	@Logged
	@GET
	@Path("{PetProfileID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile(@PathParam("PetProfileID") String PetProfileID, @Context HttpHeaders headers) {
	  	
	  Session session = new Session(headers);
      User currentUser = session.getUser();       
      int userID=currentUser.getUserID();

      System.out.println("["+currentUser.getUserName()+"] [GET] profiles/"+PetProfileID);
      
	  link.Open_link();
			  
	  PetProfile profile = new PetProfile();
		
		try{
			//System.out.println("[GET] profiles/"+PetProfileID + "db link");
			String query_getProfiles = "SELECT * FROM PetProfiles where `UserID` = ? AND `PetProfileID` = ?";
			prep_sql = link.linea.prepareStatement(query_getProfiles);
			
			prep_sql.setInt(1, userID);
			prep_sql.setString(2, PetProfileID);
			
			ResultSet rs_query_getProfiles= prep_sql.executeQuery();
			
			if (!rs_query_getProfiles.next() ) {
				System.out.println("rs_query_getProfiles no data");
				link.Close_link();
				return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
				
			} else {
					profile.setPetProfileID(rs_query_getProfiles.getString("PetProfileID"));
					profile.setUserID(rs_query_getProfiles.getInt("UserID"));
					//profile.setName(rs_query_getProfiles.getString("Name"));
					profile.setDay_Temperature_SP(rs_query_getProfiles.getFloat("Day_Temperature_SP"));
					profile.setDay_Humidity_SP(rs_query_getProfiles.getFloat("Day_Humidity_SP"));
					profile.setNight_Temperature_SP(rs_query_getProfiles.getFloat("Night_Temperature_SP"));
					profile.setNight_Humidity_SP(rs_query_getProfiles.getFloat("Night_Humidity_SP"));
					profile.setTemperature_TH(rs_query_getProfiles.getFloat("Temperature_TH"));
					profile.setHumidity_TH(rs_query_getProfiles.getFloat("Humidity_TH"));
					
					profile.setDayTime(rs_query_getProfiles.getString("DayTime"));
					profile.setNightTime(rs_query_getProfiles.getString("NightTime"));
				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading profile").build();
				
		}

	link.Close_link();
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonString = null;
	
	try {
		jsonString = mapper.writeValueAsString(profile);
		
	} catch (JsonProcessingException e) {
		
		System.out.println("Error mapping to json: " + e.getMessage());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
	}

  return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
  
  }
	
	
	@Logged
	@DELETE
	@Path("{PetProfileID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProfile(@PathParam("PetProfileID") String PetProfileID, @Context HttpHeaders headers) {

	  Session session = new Session(headers);
      User currentUser = session.getUser();       
      int userID=currentUser.getUserID();
	  
      System.out.println("["+currentUser.getUserName()+"] [DELETE] profiles/"+PetProfileID);
      
	  link.Open_link();
		
		try{
			String query_getProfiles = "DELETE FROM PetProfiles where `UserID` = ? AND `PetProfileID` = ?";
			prep_sql = link.linea.prepareStatement(query_getProfiles);
			
			prep_sql.setInt(1, userID);
			prep_sql.setString(2, PetProfileID);
			
			int rs_query_getProfiles=prep_sql.executeUpdate();

			if (rs_query_getProfiles == 0){
				System.out.println("rs_query_getProfiles no data");
				link.Close_link();
				return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
			}	

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting profile").build();
				
		}

	link.Close_link();
	
	SendToCentralNode sendToCentralNode = new SendToCentralNode(null, "DELETE", "profiles/"+PetProfileID);
	sendToCentralNode.sendToUser(userID);

	return Response.status(Response.Status.OK).build();
  
  }
	
	@Logged
	@PUT
	@Path("{PetProfileID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProfile(@PathParam("PetProfileID") String PetProfileID, PetProfile profile, @Context HttpHeaders headers) {
	
	  Session session = new Session(headers);
	  User currentUser = session.getUser();
      	  
      System.out.println("["+currentUser.getUserName()+"] [PUT] profiles/"+PetProfileID);
	  
	  link.Open_link();
			
		try{
			String query_putProfile = "UPDATE PetProfiles SET `PetProfileID`=?,`Day_Temperature_SP`=?,`Day_Humidity_SP`=?,`Night_Temperature_SP`=?,`Night_Humidity_SP`=?,`Temperature_TH`=?,`Humidity_TH`=?,`DayTime`=?,`NightTime`=? WHERE `PetProfileID`=? AND `UserID`=?;";
			prep_sql = link.linea.prepareStatement(query_putProfile);
			
			prep_sql.setString(1, profile.getPetProfileID());
			prep_sql.setFloat(2, profile.getDay_Temperature_SP());
			prep_sql.setFloat(3, profile.getDay_Humidity_SP());
			prep_sql.setFloat(4, profile.getNight_Temperature_SP());
			prep_sql.setFloat(5, profile.getNight_Humidity_SP());
			prep_sql.setFloat(6, profile.getTemperature_TH());
			prep_sql.setFloat(7, profile.getHumidity_TH());
			prep_sql.setString(8, profile.getDayTime());
			prep_sql.setString(9, profile.getNightTime());
			prep_sql.setString(10, PetProfileID);
			prep_sql.setInt(11, currentUser.getUserID());
					
			int rs_query_putProfile=prep_sql.executeUpdate();

			if (rs_query_putProfile == 0){
				System.out.println("rs_query_putProfile no data");
				link.Close_link();
				return Response.status(Response.Status.NOT_FOUND).entity("Profile not found").build();
			}

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Profile exists").build();
			
		}

	link.Close_link();
	
	return Response.status(Response.Status.OK).build();
  
  }	

} 