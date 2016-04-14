package com.dynocloud.server.api;

import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
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

@Path("/alerts")
public class AlertResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	@Logged
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlerts(@Context HttpHeaders headers) {
	  	
	  Session session = new Session(headers);
      User currentUser = session.getUser();
        
      int userID=currentUser.getUserID();
      
      System.out.println("["+currentUser.getUserName()+"] [GET] /alerts");
	  
	  link.Open_link();
		
	  ArrayList<Alert> list = new ArrayList<Alert>();
		
		try{
			String query_getAlerts = "SELECT * FROM Alerts where `UserID` = ?";
			prep_sql = link.linea.prepareStatement(query_getAlerts);
			
			prep_sql.setInt(1, userID);
			
			ResultSet rs_query_getAlerts= prep_sql.executeQuery();
			
				while(rs_query_getAlerts.next()){
					
					Alert alert = new Alert();
							
					alert.setAlertID(rs_query_getAlerts.getInt("AlertID"));
					alert.setEnclosureNodeID(rs_query_getAlerts.getInt("EnclosureNodeID"));
					alert.setCentralNodeID(rs_query_getAlerts.getInt("CentralNodeID"));
					alert.setUserID(rs_query_getAlerts.getInt("UserID"));
					alert.setDate(rs_query_getAlerts.getString("Date"));
					alert.setMessage(rs_query_getAlerts.getString("Message"));
					alert.setDestination(rs_query_getAlerts.getString("Destination"));

					list.add(alert);

				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading alerts").build();
			
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
	public Response postAlert(Alert alert, @Context HttpHeaders headers) {
	
	  Session session = new Session(headers);
      User currentUser = session.getUser();
      
      System.out.println("["+currentUser.getUserName()+"] [POST] /alerts");
      
	  link.Open_link();
			
		try{
			String query_postAlert = "INSERT INTO Alerts (`UserID`, `CentralNodeID`, `EnclosureNodeID`, `Date`, `Message`, `Destination`) VALUES (?,?,?,?,?,?);";
			prep_sql = link.linea.prepareStatement(query_postAlert);
			
			prep_sql.setInt(1, currentUser.getUserID());
			prep_sql.setInt(2, alert.getCentralNodeID());
			prep_sql.setInt(3, alert.getEnclosureNodeID());
			prep_sql.setString(4, alert.getDate());
			prep_sql.setString(5, alert.getMessage());
			prep_sql.setString(6, alert.getDestination());
			
			prep_sql.executeUpdate();

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating alert").build();
			
		}

	link.Close_link();
	
	return Response.status(Response.Status.OK).build();
  
  }
	
	@Logged
	@GET
	@Path("settings")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlertSettings(@Context HttpHeaders headers) {
	  	
	  Session session = new Session(headers);
      User currentUser = session.getUser();
        
      int userID=currentUser.getUserID();
      
      System.out.println("["+currentUser.getUserName()+"] [GET] /alerts/settings");
	  
	  link.Open_link();
		
	  ArrayList<Alert> list = new ArrayList<Alert>();
		
		try{
			String query_getAlerts = "SELECT * FROM Alerts where `UserID` = ?";
			prep_sql = link.linea.prepareStatement(query_getAlerts);
			
			prep_sql.setInt(1, userID);
			
			ResultSet rs_query_getAlerts= prep_sql.executeQuery();
			
				while(rs_query_getAlerts.next()){
					
					Alert alert = new Alert();
							
					alert.setAlertID(rs_query_getAlerts.getInt("AlertID"));
					alert.setEnclosureNodeID(rs_query_getAlerts.getInt("EnclosureNodeID"));
					alert.setCentralNodeID(rs_query_getAlerts.getInt("CentralNodeID"));
					alert.setUserID(rs_query_getAlerts.getInt("UserID"));
					alert.setDate(rs_query_getAlerts.getString("Date"));
					alert.setMessage(rs_query_getAlerts.getString("Message"));
					alert.setDestination(rs_query_getAlerts.getString("Destination"));

					list.add(alert);

				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading alerts").build();
			
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
	
} 