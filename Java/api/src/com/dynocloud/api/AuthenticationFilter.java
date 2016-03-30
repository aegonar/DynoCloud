package com.dynocloud.api;

import java.io.IOException;
//import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Priority;
//import javax.enterprise.context.RequestScoped;
//import javax.enterprise.event.Event;
//import javax.enterprise.event.Observes;
//import javax.enterprise.inject.Produces;
//import javax.enterprise.inject.*;
//import javax.inject.Inject;
//import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;




@Logged
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

//	
//	@Inject
//	@AuthenticatedUser
//	Event<String> userAuthenticatedEvent;
	
//    @Produces
//    private User authenticatedUser;
	
	//private String UserID=null;
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

    	
    	
    	System.out.println("AuthenticationFilter");
    	
        // Get the HTTP Authorization header from the request
        String authorizationHeader = 
            requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        try {

            // Validate the token
            validateToken(token);

        } catch (Exception e) {
            requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED).build());
        }
        
        
        
//        requestContext.setSecurityContext(new SecurityContext() {
//
//            @Override
//            public Principal getUserPrincipal() {
//
//                return new Principal() {
//
//                    @Override
//                    public String getName() {
//                    	 //System.out.println("getName: "+token);
////                    	 String s=null;
////                        try {
////							s= ""+validateToken(token);
////						} catch (Exception e) {
////							// TODO Auto-generated catch block
////							e.printStackTrace();
////						}
//                        return UserID;
//                       
//                    }
//                };
//            }
//
//            @Override
//            public boolean isUserInRole(String role) {
//                return true;
//            }
//
//            @Override
//            public boolean isSecure() {
//                return false;
//            }
//
//            @Override
//            public String getAuthenticationScheme() {
//                return null;
//            }
//        });
        
    }


    
    private void validateToken(String token) throws Exception {
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    	
    	
    	
    	
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
         				//UserID= userid;
         				
         				
         				///////////
//         		        User currentUser = new User();
//         		        currentUser.setUserID("001");
//         		        
//         		        System.out.println("findUserID");
//         		        
//
//         		    	System.out.println("handleAuthenticationEvent");
//         		        this.authenticatedUser = currentUser;

         				////////////
         				
         				
         				
         				//userAuthenticatedEvent.fire(UserID);

     			}
     			

     			
     	  }catch(Exception e){

   			System.out.println("Error at query_validateToken: " + e.getMessage());
   			
   			link.Close_link();
   			throw new NotAuthorizedException("Invalid session token");
   		}
   	
   	
   	
     	 link.Close_link();
    	
    	
    	
    	
    	
    }
    



    
    
}
