package com.dynocloud.node.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/login")
public class DynoCloudLogin {

	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	@POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticateUser(Credentials credentials) {

		System.out.println("[POST] /login");
		
		String url = "http://dynocare.xyz/api/login/central";
		
		System.out.println("Path: " + url);
		
		URL obj = null;
		HttpURLConnection con = null;
			
			try {
				obj = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to DynoCloud").build();
			}
			
			try {
				con = (HttpURLConnection) obj.openConnection();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to DynoCloud").build();
			}
			
			try {
				con.setRequestMethod("POST");
			} catch (ProtocolException e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to DynoCloud").build();
			}
			//System.out.println("Method: " + mFromJSON.getMethod());
			
			//for( Header header : mFromJSON.getHeaderList()){
				
				con.setRequestProperty("Content-Type", "application/json");
				//System.out.println("Header: " + header.getKey() + ": " + header.getValue());
				
		    	ObjectMapper mapper = new ObjectMapper();
	        	String jsonString = null;
	        	
	        	try {
	        		jsonString = mapper.writeValueAsString(credentials);
	        		
	        	} catch (JsonProcessingException e) {
	        		
	        		System.out.println("Error mapping to json: " + e.getMessage());
	        		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
	        	}
			

			String urlParameters = jsonString;
				
			//String urlParameters = mFromJSON.getPayload();
			
			con.setDoOutput(true);
			DataOutputStream wr;
			try {
				wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to DynoCloud").build();
				//null payload

			}
				

			int responseCode = 0;
			try {
				responseCode = con.getResponseCode();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to DynoCloud").build();
			}

			//System.out.println("Response Code : " + responseCode);
			if(responseCode == 403){
				return Response.status(Response.Status.FORBIDDEN).entity("Incorrect DynoCloud credentials").build();
			} else if(responseCode != 200){
				return Response.status(Response.Status.FORBIDDEN).entity("DynoCloud error").build();
			}

			BufferedReader in;
			StringBuffer response = null;
			
			try {
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error connecting to DynoCloud").build();
			}

			
			//System.out.println("CN resp: "+response.toString());
			
			//-------------------------------------------------------------------					
			//ObjectMapper mapper = new ObjectMapper();
			CentralNodeCredentials centralNodeCredentials = null;
				
			try {
				centralNodeCredentials = mapper.readValue(response.toString(), CentralNodeCredentials.class);
			} catch (Exception e1) {
				System.out.println("Error mapping to json: " + e1.getMessage());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
			}	
			
			//System.out.println(centralNodeCredentials);
			//------------------------------------------------------------------- 	
			link.Open_link();

			try{
				String query_postProfile = "UPDATE Config set `UserID`=?,`Token`=?,`CentralNodeID`=?,`DynoCloud`=? where `DynoCloud`=0;";
				
				prep_sql = link.linea.prepareStatement(query_postProfile);
				
				prep_sql.setInt(1, centralNodeCredentials.getUserID());
				prep_sql.setString(2, centralNodeCredentials.getToken());
				prep_sql.setInt(3, centralNodeCredentials.getCentralNodeID());
				prep_sql.setBoolean(4, true);
				
				prep_sql.executeUpdate();

			}catch(Exception e){

				System.out.println("Error: " + e.getMessage());
				
				link.Close_link();
				
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating config").build();
				
			}
			
		link.Close_link();	
			
        return Response.status(Response.Status.OK).build();  
   
    }
    
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLogin() {
      
      System.out.println("[GET] /login");
		
		 link.Open_link();
			
		 boolean DynoCloud = false;
			
			try{
				String query_getOnline = "SELECT `DynoCloud` FROM Config;";
				prep_sql = link.linea.prepareStatement(query_getOnline);

				ResultSet rs_query_getOnline = prep_sql.executeQuery();
				
					while(rs_query_getOnline.next()){									
						DynoCloud = rs_query_getOnline.getBoolean("DynoCloud");
					}
					
			}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
				link.Close_link();
			}

		link.Close_link();
		
		//-----------------------------------------
		String jsonString=null;
		
		if(DynoCloud)
		jsonString = "{\"online\":"+1+"}";
		else
		jsonString = "{\"online\":"+0+"}";

		return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
  
  }
	
}