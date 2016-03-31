package com.dynocloud.server.api;

//import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//@WebService 
@Path("/")
public class Hello {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String hello() {
	
		return "Welcome to DynoCloud Server API\n";
  }
  
} 