package com.dynocloud.node.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/module")
public class ModuleResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModules() {
      
      System.out.println("[GET] /module");
	  
	  link.Open_link();
		
	  ArrayList<Module> list = new ArrayList<Module>();
		
		try{
			String query_getModules = "SELECT * FROM EnclosureNode;";
			prep_sql = link.linea.prepareStatement(query_getModules);
			
			ResultSet rs_query_getModules= prep_sql.executeQuery();
			
				while(rs_query_getModules.next()){
					
					Module module = new Module();
							
					module.setEnclosureNodeID(rs_query_getModules.getInt("EnclosureNodeID"));
					module.setName(rs_query_getModules.getString("Name"));
					module.setDev_IR(rs_query_getModules.getInt("DEV_IR"));
					module.setPetProfileID(rs_query_getModules.getInt("PetProfileID"));

					list.add(module);

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
  

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postModule(Module module) {
        	  
      System.out.println("POST] /module");
      
	  link.Open_link();
			
		try{
			String query_postModule = "INSERT INTO EnclosureNode (`Name`,`DEV_IR`,`PetProfileID`) VALUES (?,?,?);";
			prep_sql = link.linea.prepareStatement(query_postModule);


			prep_sql.setString(1, module.getName());
			prep_sql.setInt(2, module.getDev_IR());
			prep_sql.setInt(3, module.getPetProfileID());
			
			prep_sql.executeUpdate();

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating module").build();
			
		}

	link.Close_link();
	
	return Response.status(Response.Status.OK).build();
  
  }
	

	@GET
	@Path("{EnclosureNodeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getModule(@PathParam("EnclosureNodeID") int EnclosureNodeID) {
	  	
      System.out.println("[GET] /module/"+EnclosureNodeID);
      
	  link.Open_link();
			  
		Module module = new Module();
		
		try{
			String query_getModules = "SELECT * FROM EnclosureNode where`EnclosureNodeID` = ?;";
			prep_sql = link.linea.prepareStatement(query_getModules);
			
			prep_sql.setInt(1, EnclosureNodeID);
			
			ResultSet rs_query_getModules = prep_sql.executeQuery();
			
			if (!rs_query_getModules.next() ) {
				System.out.println("rs_query_getModules no data");
				link.Close_link();
				return Response.status(Response.Status.FORBIDDEN).entity("Module not found").build();	
			} else {
				module.setEnclosureNodeID(rs_query_getModules.getInt("EnclosureNodeID"));
				module.setName(rs_query_getModules.getString("Name"));
				module.setDev_IR(rs_query_getModules.getInt("DEV_IR"));
				module.setPetProfileID(rs_query_getModules.getInt("PetProfileID"));
			}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading module").build();
				
		}

	link.Close_link();
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonString = null;
	
	try {
		jsonString = mapper.writeValueAsString(module);
		
	} catch (JsonProcessingException e) {
		
		System.out.println("Error mapping to json: " + e.getMessage());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
	}

  return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
  
  }
	
	@DELETE
	@Path("{EnclosureNodeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteModule( @PathParam("EnclosureNodeID") int EnclosureNodeID) {
	  
      System.out.println("[DELETE] /module/"+EnclosureNodeID);
      
	  link.Open_link();
		
		try{
			String query_deleteModule = "DELETE FROM EnclosureNode where `EnclosureNodeID` = ?;";
			prep_sql = link.linea.prepareStatement(query_deleteModule);
			
			prep_sql.setInt(1, EnclosureNodeID);
			
			int rs_query_deleteModule=prep_sql.executeUpdate();

			if (rs_query_deleteModule == 0){
				System.out.println("rs_query_deleteModule no data");
				link.Close_link();
				return Response.status(Response.Status.FORBIDDEN).entity("Cannot delete module").build();
			}	

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error deleting module").build();
				
		}

	link.Close_link();

	return Response.status(Response.Status.OK).build();
  
  }
	
	@PUT
	@Path("{EnclosureNodeID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putModule( @PathParam("EnclosureNodeID") int EnclosureNodeID, Module module) {
		
      System.out.println("[PUT] /module/"+EnclosureNodeID);
     	  
	  link.Open_link();
			
		try{
			String query_putModule = "UPDATE EnclosureNode SET `Name`=?,`DEV_IR`=?,`PetProfileID`=? WHERE `EnclosureNodeID`=?;";
			prep_sql = link.linea.prepareStatement(query_putModule);
			
			prep_sql.setString(1, module.getName());
			prep_sql.setInt(2, module.getDev_IR());
			prep_sql.setInt(3, module.getPetProfileID());
			prep_sql.setInt(4, EnclosureNodeID);
					
			int rs_query_putModule=prep_sql.executeUpdate();

			if (rs_query_putModule == 0){
				System.out.println("rs_query_putModule no data");
				link.Close_link();
				return Response.status(Response.Status.FORBIDDEN).entity("Module not found").build();
			}

		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error updating module").build();
			
		}

	  
	  PetProfile profile = new PetProfile();
		
		try{
			String query_getProfiles = "SELECT * FROM PetProfiles where `PetProfileID` = ?";
			prep_sql = link.linea.prepareStatement(query_getProfiles);
			
			prep_sql.setInt(1, module.getPetProfileID());
			
			ResultSet rs_query_getProfiles= prep_sql.executeQuery();
			
			if (!rs_query_getProfiles.next() ) {
				System.out.println("rs_query_getProfiles no data");
				link.Close_link();
				return Response.status(Response.Status.FORBIDDEN).entity("Profile not found").build();
				
			} else {
					profile.setPetProfileID(rs_query_getProfiles.getInt("PetProfileID"));
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