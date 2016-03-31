package com.dynocloud.server.api;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.annotation.Priority;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;


@Logged
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

    	System.out.println("AuthenticationFilter");

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            validateToken(token);

        } catch (Exception e) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
        
    }

    private void validateToken(String token) throws Exception {

    	Database_connection link = new Database_connection();
      	 PreparedStatement prep_sql;

       	System.out.println("validateToken [" + token +"]");
     	  
     	  		link.Open_link();
     	    	  		
     	  	try{
     			String query_validateToken= "Select `UserID` from `Session` where `Token` = ?;";
     			prep_sql = link.linea.prepareStatement(query_validateToken);
     			prep_sql.setString(1, token);
     			
     			ResultSet rs_query_validateToken = prep_sql.executeQuery();

     			
     			if (!rs_query_validateToken.next() ) {
     				
     			    System.out.println("query_validateToken no data");
     			  throw new NotAuthorizedException("Invalid session token");
     			  
     			} else {
    			
         				String userid = rs_query_validateToken.getString("UserID");
         				System.out.println("rs_query_validateToken user idenfied: "+ userid);

     			}
     			
     	  }catch(Exception e){

   			System.out.println("Error at query_validateToken: " + e.getMessage());
   			
   			link.Close_link();
   			throw new NotAuthorizedException("Invalid session token");
   		}
   	 	
     	 link.Close_link();	
    	
    }
       
}