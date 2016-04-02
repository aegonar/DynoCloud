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
	  	
	System.out.println("[GET] profiles");

	  Session session = new Session(headers);
      User currentUser = session.getUser();
        
      int userID=currentUser.getUserID();
	  
	  link.Open_link();
		
	  ArrayList<PetProfile> list = new ArrayList<PetProfile>();
		
		try{
			String query_getProfiles = "SELECT * FROM PetProfiles where `UserID` = ?";
			prep_sql = link.linea.prepareStatement(query_getProfiles);
			
			prep_sql.setInt(1, userID);
			
			ResultSet rs_query_getProfiles= prep_sql.executeQuery();
			
				while(rs_query_getProfiles.next()){
					
					PetProfile profile = new PetProfile();
							
					profile.setID(rs_query_getProfiles.getInt("ID"));
					profile.setUserID(rs_query_getProfiles.getInt("UserID"));
					profile.setName(rs_query_getProfiles.getString("Name"));
					profile.setDay_Temperature_SP(rs_query_getProfiles.getFloat("Day_Temperature_SP"));
					profile.setDay_Humidity_SP(rs_query_getProfiles.getFloat("Day_Humidity_SP"));
					profile.setNight_Temperature_SP(rs_query_getProfiles.getFloat("Night_Temperature_SP"));
					profile.setNight_Humidity_SP(rs_query_getProfiles.getFloat("Night_Humidity_SP"));
					profile.setTemperature_TH(rs_query_getProfiles.getFloat("Temperature_TH"));
					profile.setHumidity_TH(rs_query_getProfiles.getFloat("Humidity_TH"));

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
	  	  
	System.out.println("[POST] profiles");
	
	  Session session = new Session(headers);
      User currentUser = session.getUser();
        	  
	  link.Open_link();
			
		try{
			String query_postProfile = "INSERT INTO PetProfiles (`UserID`,`Name`,`Day_Temperature_SP`,`Day_Humidity_SP`,`Night_Temperature_SP`,`Night_Humidity_SP`,`Temperature_TH`,`Humidity_TH`) VALUES (?,?,?,?,?,?,?,?);";
			prep_sql = link.linea.prepareStatement(query_postProfile);
			
			prep_sql.setInt(1, currentUser.getUserID());
			prep_sql.setString(2, profile.getName());
			prep_sql.setFloat(3, profile.getDay_Temperature_SP());
			prep_sql.setFloat(4, profile.getDay_Humidity_SP());
			prep_sql.setFloat(5, profile.getNight_Temperature_SP());
			prep_sql.setFloat(6, profile.getNight_Humidity_SP());
			prep_sql.setFloat(7, profile.getTemperature_TH());
			prep_sql.setFloat(8, profile.getHumidity_TH());
			
			prep_sql.executeUpdate();

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating profile").build();
			
		}

	link.Close_link();
	
	return Response.status(Response.Status.OK).build();
  
  }
	
	@Logged
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile(@PathParam("id") int id, @Context HttpHeaders headers) {
	  	
	System.out.println("[GET] profiles/"+id);

	  Session session = new Session(headers);
      User currentUser = session.getUser();       
      int userID=currentUser.getUserID();
	  
	  link.Open_link();
			  
	  PetProfile profile = new PetProfile();
		
		try{
			String query_getProfiles = "SELECT * FROM PetProfiles where `UserID` = ? AND `ID` = ?";
			prep_sql = link.linea.prepareStatement(query_getProfiles);
			
			prep_sql.setInt(1, userID);
			prep_sql.setInt(2, id);
			
			ResultSet rs_query_getProfiles= prep_sql.executeQuery();
			
			if (!rs_query_getProfiles.next() ) {
				System.out.println("rs_query_getProfiles no data");
				return Response.status(Response.Status.FORBIDDEN).entity("Private profile").build();
				
			} else {
					profile.setID(rs_query_getProfiles.getInt("ID"));
					profile.setUserID(rs_query_getProfiles.getInt("UserID"));
					profile.setName(rs_query_getProfiles.getString("Name"));
					profile.setDay_Temperature_SP(rs_query_getProfiles.getFloat("Day_Temperature_SP"));
					profile.setDay_Humidity_SP(rs_query_getProfiles.getFloat("Day_Humidity_SP"));
					profile.setNight_Temperature_SP(rs_query_getProfiles.getFloat("Night_Temperature_SP"));
					profile.setNight_Humidity_SP(rs_query_getProfiles.getFloat("Night_Humidity_SP"));
					profile.setTemperature_TH(rs_query_getProfiles.getFloat("Temperature_TH"));
					profile.setHumidity_TH(rs_query_getProfiles.getFloat("Humidity_TH"));
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
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProfile(@PathParam("id") int id, @Context HttpHeaders headers) {
	  	
	System.out.println("[DELETE] profiles/"+id);

	  Session session = new Session(headers);
      User currentUser = session.getUser();       
      int userID=currentUser.getUserID();
	  
	  link.Open_link();
		
		try{
			String query_getProfiles = "DELETE FROM PetProfiles where `UserID` = ? AND `ID` = ?";
			prep_sql = link.linea.prepareStatement(query_getProfiles);
			
			prep_sql.setInt(1, userID);
			prep_sql.setInt(2, id);
			
			int rs_query_getProfiles=prep_sql.executeUpdate();

			if (rs_query_getProfiles == 0){
				System.out.println("rs_query_getProfiles no data");
				return Response.status(Response.Status.FORBIDDEN).entity("Cannot delete profile").build();
			}	

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading profile").build();
				
		}

	link.Close_link();

	return Response.status(Response.Status.OK).build();
  
  }
	
	@Logged
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateProfile(@PathParam("id") int id, PetProfile profile, @Context HttpHeaders headers) {
	  	
	System.out.println("[PUT] profiles/"+id);
	
	  Session session = new Session(headers);
	  User currentUser = session.getUser();
      	  
	  link.Open_link();
			
		try{
			String query_putProfile = "UPDATE PetProfiles SET `Name`=?,`Day_Temperature_SP`=?,`Day_Humidity_SP`=?,`Night_Temperature_SP`=?,`Night_Humidity_SP`=?,`Temperature_TH`=?,`Humidity_TH`=? WHERE `ID`=? AND `UserID`=?;";
			prep_sql = link.linea.prepareStatement(query_putProfile);
			
			prep_sql.setString(1, profile.getName());
			prep_sql.setFloat(2, profile.getDay_Temperature_SP());
			prep_sql.setFloat(3, profile.getDay_Humidity_SP());
			prep_sql.setFloat(4, profile.getNight_Temperature_SP());
			prep_sql.setFloat(5, profile.getNight_Humidity_SP());
			prep_sql.setFloat(6, profile.getTemperature_TH());
			prep_sql.setFloat(7, profile.getHumidity_TH());
			prep_sql.setInt(8, id);
			prep_sql.setInt(9, currentUser.getUserID());
					
			int rs_query_putProfile=prep_sql.executeUpdate();

			if (rs_query_putProfile == 0){
				System.out.println("rs_query_putProfile no data");
				return Response.status(Response.Status.FORBIDDEN).entity("Cannot update profile").build();
			}

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting profile").build();
			
		}

	link.Close_link();
	
	return Response.status(Response.Status.OK).build();
  
  }

} 