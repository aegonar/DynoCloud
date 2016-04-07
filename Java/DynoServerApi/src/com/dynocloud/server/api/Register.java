package com.dynocloud.server.api;

import java.sql.PreparedStatement;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class Register {

	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/json")
	public Response register(User user) {
		
        System.out.println("register [POST]");
        System.out.println(user);
        
        Login log = new Login();

        link.Open_link();
        
        String token = null;
		
		try{
			String query_register = "INSERT INTO Users (`UserName`,`Password`,`Name`,`LastName`,`Email`,`Phone`) VALUES (?,?,?,?,?,?);";
			prep_sql = link.linea.prepareStatement(query_register);
							
			prep_sql.setString(1, user.getUserName());
			prep_sql.setString(2, user.getPassword());
			prep_sql.setString(3, user.getName());
			prep_sql.setString(4, user.getLastName());
			prep_sql.setString(5, user.getEmail());
			prep_sql.setString(6, user.getPhone());
	
			int  rs_query_register = prep_sql.executeUpdate();
			
			if (rs_query_register == 0){
				throw new NotAuthorizedException("Username or password error");
			}
						
	        token = log.issueToken(user.getUserName());

	        System.out.println("register [Execute Insert]");	               
	        
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.CONFLICT).entity("Username or password error").build();
				
		}

	link.Close_link();

	return Response.ok(token).build();
	}

} 