package com.dynocloud.api;



//import javax.inject.Inject;
//import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
//import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import java.security.Principal;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;


@Path("/viewUser")
public class ViewUser {
	
	//@Inject User theuser;
	
	@Logged

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public User GetUser(@Context SecurityContext securityContext) {
	  	    
		Principal principal = securityContext.getUserPrincipal();
	    String username = principal.getName();
		
        User currentUser = new User();
        currentUser.setUserID(username);
	        
	    System.out.println("viewUser");

		
  return currentUser;
  }
  

} 