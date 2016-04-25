package com.dynocloud.server.api;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
//import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Path("/login/central")
public class CentralNodeLogin {

	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	
	@POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticateUser(Credentials credentials) {

		System.out.println("[POST] /login/central");
		
        String username = credentials.getUsername();
        String password = credentials.getPassword();
        
        CentralNodeCredentials cnc = new CentralNodeCredentials();

  	  
  	  	link.Open_link();
        

        try {

            // Authenticate the user using the credentials provided
            int userID = authenticate(username, password);

            // Issue a token for the user
            String token = issueToken(username);
            
            
            cnc.setToken(token);
            cnc.setUserID(userID);

            //System.out.println("auth ok");
            
            try{
				String query_insertCentralNode = "INSERT INTO CentralNode (`UserID`, `Added`) VALUES (?, now())";
				
				prep_sql = link.linea.prepareStatement(query_insertCentralNode);
				
				prep_sql.setInt(1, userID);
																
				//int rs_query_insertCentralNodet= 
						prep_sql.executeUpdate();
				
//				if (!rs_query_insertCentralNodet.next() ) {
//					System.out.println("rs_query_insertCentralNodet no data");
//					link.Close_link();
//					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Module not found").build();	
//				} 
				
			}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
				link.Close_link();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();	
			}
            
            //System.out.println("add cn ok");
            Timestamp maxAdded=null;
            
            try{
				String query_getLatestAdded = "SELECT MAX(Added) AS MaxAdded FROM CentralNode where UserID=?;";
				prep_sql = link.linea.prepareStatement(query_getLatestAdded);
				
				prep_sql.setInt(1, userID);
																
				ResultSet rs_query_getLatestAdded = prep_sql.executeQuery();
				
				if (!rs_query_getLatestAdded.next() ) {
					System.out.println("rs_query_getLatestAdded no data");
					link.Close_link();
					return Response.status(Response.Status.FORBIDDEN).entity("Module not found").build();	
				} else {
					maxAdded = rs_query_getLatestAdded.getTimestamp("MaxAdded");
				}
	
			}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
				link.Close_link();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();	
			}
            
            //System.out.println("max added "+maxAdded);
            int centralNodeID=0;
            
            try{
				String query_getLatest = "SELECT * FROM CentralNode where UserID=? AND `Added`=?;";
				prep_sql = link.linea.prepareStatement(query_getLatest);
				
				prep_sql.setInt(1, userID);
				prep_sql.setTimestamp(2, maxAdded);
																
				ResultSet rs_query_getLatest= prep_sql.executeQuery();
				
				if (!rs_query_getLatest.next() ) {
					System.out.println("rs_query_getLatest no data");
					link.Close_link();
					return Response.status(Response.Status.FORBIDDEN).entity("Module not found").build();	
				} else {
					centralNodeID = rs_query_getLatest.getInt("CentralNodeID");
					cnc.setCentralNodeID(centralNodeID);
				}
	
			}catch(Exception e){
				System.out.println("Error: " + e.getMessage());
				link.Close_link();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();	
			}               

            //System.out.println("centralNodeID: " + centralNodeID);
            
            // Return the token on the response
            
            
            
        	ObjectMapper mapper = new ObjectMapper();
        	String jsonString = null;
        	
        	try {
        		jsonString = mapper.writeValueAsString(cnc);
        		
        	} catch (JsonProcessingException e) {
        		
        		System.out.println("Error mapping to json: " + e.getMessage());
        		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
        	}
            
            
            
            
            
            link.Close_link();
            return Response.ok(jsonString).build();

        } catch (Exception e) {
        	System.out.println("Error authenticating user");
            return Response.status(Response.Status.UNAUTHORIZED).build();
            
        }    
        
        
   
    }

    private int authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    	
    	
    	
//    	 Database_connection link = new Database_connection();
//       	 PreparedStatement prep_sql;

       	int userID = 0;
       	 
        	//System.out.println("authenticate [" + username + ", "+password+"]");
      	  
//      	  		link.Open_link();
      		

      	  		
      	  	try{
      			String query_authenticate= "Select `UserID` from `Users` where `UserName` = ? and `Password` = ?;";
      			prep_sql = link.linea.prepareStatement(query_authenticate);
      			prep_sql.setString(1, username);
      			prep_sql.setString(2, password);
      			
      			ResultSet rs_query_authenticate = prep_sql.executeQuery();

      			
      			if (!rs_query_authenticate.next() ) {
      			    System.out.println("rs_query_authenticate no data");
      			    throw new NotAuthorizedException("Invalid username or password");
      			} else {
          			//while(rs_query_authenticate.next()){
      					userID = rs_query_authenticate.getInt("UserID");
          				//System.out.println("rs_query_authenticate: " + userID);

      			}
      			

      			
      	  }catch(Exception e){

    			System.out.println("Error at query_authenticate: " + e.getMessage());
    			
    			//link.Close_link();
    			throw new NotAuthorizedException("Invalid username or password");
    			
    		}
    	
    	
    	
      	//link.Close_link();
      	
      	return userID;

    }

    public String issueToken(String username) throws SQLException {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
   
   	 //Database_connection link = new Database_connection();
   	 //PreparedStatement prep_sql;

    	//System.out.println("getUserID [" + username + "]");
  	  
  	  		//link.Open_link();
  		
  	  	String UserID=null;
  	  		
  	  	try{
  			String query_getUserID = "Select `UserID` from `Users` where `UserName` = ?;";
  			prep_sql = link.linea.prepareStatement(query_getUserID);
  			prep_sql.setString(1, username);
  			
  			ResultSet rs_query_getUserID = prep_sql.executeQuery();

  			while(rs_query_getUserID.next()){
  				UserID = rs_query_getUserID.getString("UserID");
  			}
  			
  	  }catch(Exception e){

			System.out.println("Error at query_getUserID: " + e.getMessage());
			
			//link.Close_link();
			throw new SQLException();
		}
			
			
	    	Random random = new SecureRandom();
	        String token = new BigInteger(130, random).toString(32);
	    	
	        
	    try{    
  			String query_setToken = "Insert into `Session` (`UserID`,`Token`) values (?,?);";
  			prep_sql = link.linea.prepareStatement(query_setToken);
  			prep_sql.setString(1, UserID);
  			prep_sql.setString(2, token);
  			prep_sql.executeUpdate();
  			//System.out.println("issueToken [Execute Insert]");
  			
	        
    }catch(Exception e){

		System.out.println("Error at query_setToken: " + e.getMessage());
		
		//link.Close_link();
		throw new SQLException();
	}  

  	//link.Close_link();
  	
  	
  	return token;
  	
    }
    
}