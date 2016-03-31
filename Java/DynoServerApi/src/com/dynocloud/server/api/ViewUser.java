package com.dynocloud.server.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/viewUser")
public class ViewUser {

	@Logged
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User GetUser(@Context HttpHeaders headers) {
		
		Session session = new Session(headers);
        User currentUser = session.getUser();

	    return currentUser;
	}
} 