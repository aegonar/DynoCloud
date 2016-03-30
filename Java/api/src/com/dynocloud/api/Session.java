package com.dynocloud.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;

public class Session {
	
	private User user = new User();
	
	public User getUser(){
		return this.user;
	}
	
	public Session(HttpHeaders headers){
		
        String authorizationHeader = 
        		headers.getHeaderString(HttpHeaders.AUTHORIZATION);


        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();


	Database_connection link = new Database_connection();
 	 PreparedStatement prep_sql;

  	System.out.println("validateToken [" + token +"]");
	  
	  		link.Open_link();
		

	  		
	  	try{

			String query_session = "SELECT `Users`.`UserID`, `UserName`, `Name`, `LastName`, `Email`, `Phone` FROM `Users`, `Session` where `Token` = ? and `Users`.`UserID`=`Session`.`UserID`;";
			
			prep_sql = link.linea.prepareStatement(query_session);
			prep_sql.setString(1, token);
			
			ResultSet rs_query_session = prep_sql.executeQuery();

			
			if (!rs_query_session.next() ) {
				
			    System.out.println("query_validateToken no data");
			  throw new NotAuthorizedException("Invalid session token");
			  
			} else {
		
    				System.out.println("rs_query_validateToken user idenfied: "+ rs_query_session.getString("UserID"));
    				
    			      this.user.setUserID(rs_query_session.getString("UserID"));  
    			      this.user.setUserName(rs_query_session.getString("UserName"));
    			      this.user.setName(rs_query_session.getString("Name"));
    			      this.user.setLastName(rs_query_session.getString("LastName"));
    			      this.user.setEmail(rs_query_session.getString("Email"));
    			      this.user.setPhone(rs_query_session.getString("Phone"));
			}
			

			
	  }catch(Exception e){

			System.out.println("Error at query_validateToken: " + e.getMessage());
			
			link.Close_link();
			throw new NotAuthorizedException("Invalid session token");
		}
	
	
	
	 link.Close_link();
	 

}
	
}
