package com.dynocloud.server.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/metrics")
public class MetricsResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
		
	@Logged
	@GET
	@Path("{CentralNodeID}/{EnclosureNodeID}/{Timeframe}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMetrics(@PathParam("CentralNodeID") int CentralNodeID, @PathParam("EnclosureNodeID") int EnclosureNodeID, 
							@PathParam("Timeframe") int Timeframe, @Context HttpHeaders headers) {
	  	
	  Session session = new Session(headers);
      User currentUser = session.getUser();       
      int userID=currentUser.getUserID();
      
      System.out.println("["+currentUser.getUserName()+"] [GET] /metrics/"+CentralNodeID+"/"+EnclosureNodeID);

	  link.Open_link();
			  
	  ArrayList<Metrics> list = new ArrayList<Metrics>();
	  
	   //Get the current date
	  LocalDateTime now = LocalDateTime.now();
      System.out.println("Current date: " + now);
		
      //add 1 month to the current date
      LocalDateTime warped = now.minus(Timeframe, ChronoUnit.DAYS);
      System.out.println("Next month: " + warped);
      
      System.out.println("now: " + now);
	  
		
		try{
			String query_metrics = "SELECT * FROM Telemetry where `UserID` = ? AND `CentralNodeID` = ? AND `EnclosureNodeID` = ? `DateTime`  >=  ? AND `DateTime` < ?;";
			prep_sql = link.linea.prepareStatement(query_metrics);
			
			prep_sql.setInt(1, userID);
			prep_sql.setInt(2, CentralNodeID);
			prep_sql.setInt(3, EnclosureNodeID);
			prep_sql.setString(4, warped+"");
			prep_sql.setString(5, now+"");
			
			ResultSet rs_query_metrics= prep_sql.executeQuery();
			
			while(rs_query_metrics.next()){
				
				Metrics metrics = new Metrics();
				
				metrics.setCentralNodeID(rs_query_metrics.getInt("CentralNodeID"));
				metrics.setUserID(rs_query_metrics.getInt("UserID"));
				metrics.setEnclosureNodeID(rs_query_metrics.getInt("EnclosureNodeID"));
				metrics.setTemperature(rs_query_metrics.getInt("Temperature"));
				metrics.setHumidity(rs_query_metrics.getInt("Humidity"));
				metrics.setLoad_IR(rs_query_metrics.getInt("Load_IR"));
				metrics.setLoad_IC(rs_query_metrics.getInt("Load_IC"));
				metrics.setState_UV(rs_query_metrics.getInt("State_UV"));
				metrics.setState_HUM(rs_query_metrics.getInt("State_HUM"));
					
				Timestamp myTimestamp = rs_query_metrics.getTimestamp("DateTime");
				String S = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(myTimestamp);			
				metrics.setDateTime(S);
				
				list.add(metrics);

			}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading metrics").build();
				
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