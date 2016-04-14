package com.dynocloud.node.api;

import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.net.URISyntaxException;
//import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/override")
public class ControlOverrideResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	//@Logged
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOverrides() {
      
      System.out.println("[GET] /override");
	  
	  link.Open_link();
		
	  ArrayList<ControlOverride> list = new ArrayList<ControlOverride>();
		
		try{
			String query_getOverrides = "SELECT * FROM OverrideHistory";
			prep_sql = link.linea.prepareStatement(query_getOverrides);
			
			
			ResultSet rs_query_getOverrides= prep_sql.executeQuery();
			
				while(rs_query_getOverrides.next()){
					
					ControlOverride controlOverride = new ControlOverride();
							
					controlOverride.setEnclosureNodeID(rs_query_getOverrides.getInt("EnclosureNodeID"));
					controlOverride.setIC_OW(rs_query_getOverrides.getInt("IC_OW"));
					controlOverride.setIR_OW(rs_query_getOverrides.getInt("IR_OW"));
					controlOverride.setUV_OW(rs_query_getOverrides.getInt("UV_OW"));
					controlOverride.setHUM_OW(rs_query_getOverrides.getInt("HUM_OW"));
					controlOverride.setIC(rs_query_getOverrides.getInt("IC"));
					controlOverride.setIR(rs_query_getOverrides.getInt("IR"));
					controlOverride.setUV(rs_query_getOverrides.getInt("UV"));
					controlOverride.setHUM(rs_query_getOverrides.getInt("HUM"));
					
					Timestamp myTimestamp = rs_query_getOverrides.getTimestamp("DateTime");
					String S = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(myTimestamp);			
					controlOverride.setDateTime(S);
					
					list.add(controlOverride);

				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading overrides").build();
			
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
  
	//@Logged
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postOverride(ControlOverride controlOverride) {
	  	  
      System.out.println("[POST] /override");
      
	  link.Open_link();
			
		try{
			String query_postOverride = "INSERT INTO OverrideHistory (`EnclosureNodeID`,`DateTime`,`IC_OW`,`IR_OW`,`UV_OW`,`HUM_OW`,`IC`,`IR`,`UV`,`HUM`) VALUES (?,now(),?,?,?,?,?,?,?,?);";
			prep_sql = link.linea.prepareStatement(query_postOverride);

			prep_sql.setInt(1, controlOverride.getEnclosureNodeID());
			prep_sql.setInt(2, controlOverride.getIC_OW());
			prep_sql.setInt(3, controlOverride.getIR_OW());
			prep_sql.setInt(4, controlOverride.getUV_OW());
			prep_sql.setInt(5, controlOverride.getHUM_OW());
			prep_sql.setInt(6, controlOverride.getIC());
			prep_sql.setInt(7, controlOverride.getIR());
			prep_sql.setInt(8, controlOverride.getUV());
			prep_sql.setInt(9, controlOverride.getHUM());
			
			prep_sql.executeUpdate();

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating override").build();
			
		}

	link.Close_link();
	
	
	
	
	
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonString = null;
	
	try {
		jsonString = mapper.writeValueAsString(controlOverride);
		
	} catch (JsonProcessingException e) {
		
		System.out.println("Error mapping to json: " + e.getMessage());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
	}


	

	System.out.println("Relaying message to node");
	
	MQTT server = new MQTT();
	
	try {
		
		server.setHost("localhost", 1883);
		
		BlockingConnection server_connection = server.blockingConnection();
		
		try {
			
			server_connection.connect();
			
			server_connection.publish("nodes", jsonString.getBytes(), QoS.AT_LEAST_ONCE, false);
			System.out.println("Message relayed to node");
			
			server_connection.disconnect();
			
		} catch (Exception e) {
			System.out.println("Error relaying message");
			//TODO update local DB
		}
					
	} catch (URISyntaxException e) {
		System.out.println("Error connecting to Server");
	}
	
	
	
	
	
	
	return Response.status(Response.Status.OK).build();
  
  }
} 