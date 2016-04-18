package com.dynocloud.node.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
//import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Date;


@Path("/publish")
public class TelemetryResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTelemetry() {
	  
	  System.out.println("[GET] /publish");
	      
	  link.Open_link();
		
	  ArrayList<Telemetry> list = new ArrayList<Telemetry>();
		
		try{
			String query_telemetry = "SELECT * FROM Telemetry;";
			prep_sql = link.linea.prepareStatement(query_telemetry);
			
			//prep_sql.setInt(1, userID);
			
			ResultSet rs_query_telemetry = prep_sql.executeQuery();
			//System.out.println("executeQuery");
			
				while(rs_query_telemetry.next()){
					
					Telemetry telemetry = new Telemetry();
					
					//`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUMI_STATUS`
					
					telemetry.setCLIENTID(rs_query_telemetry.getInt("EnclosureNodeID"));
					telemetry.setTEMP(rs_query_telemetry.getInt("TEMP"));
					telemetry.setRH(rs_query_telemetry.getInt("RH"));
					telemetry.setOPTIONAL_LOAD(rs_query_telemetry.getInt("OPTIONAL_LOAD"));
					telemetry.setHEAT_LOAD(rs_query_telemetry.getInt("HEAT_LOAD"));
					telemetry.setUV_STATUS(rs_query_telemetry.getInt("UV_STATUS"));
					telemetry.setHUMI_STATUS(rs_query_telemetry.getInt("HUMI_STATUS"));
					
					Timestamp myTimestamp = rs_query_telemetry.getTimestamp("DateTime");
					String S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myTimestamp);			
					telemetry.setDateTime(S);
					
//					private int CLIENTID;
//					private float TEMP;
//					private float RH;
//					private float IR_PW;
//					private float IC_PW;
//					private int UV_STATUS;
//					private int HUMI_STATUS;
					
					
					list.add(telemetry);

				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading telemetry").build();
			
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
 //*/ 
	  	@POST
	    @Consumes({MediaType.APPLICATION_JSON})
	    //@Produces({MediaType.APPLICATION_JSON})
	    public Response postTelemetry(Telemetry telemetry) throws Exception{
	  		
	  		
	  		
	        //String result=null;
	        System.out.println("[POST] /publish");
	        System.out.println(telemetry);
	          
	        link.Open_link();
			
			try{
				String query_telemetry = "INSERT INTO Telemetry (`EnclosureNodeID`,`TEMP`,`RH`,`OPTIONAL_LOAD`,`HEAT_LOAD`,`UV_STATUS`,`HUMI_STATUS`,`DateTime`) VALUES (?,?,?,?,?,?,?,?);";
				prep_sql = link.linea.prepareStatement(query_telemetry);
								
				prep_sql.setInt(1, telemetry.getCLIENTID());
				prep_sql.setFloat(2, telemetry.getTEMP());
				prep_sql.setFloat(3, telemetry.getRH());
				prep_sql.setFloat(4, telemetry.getOPTIONAL_LOAD());
				prep_sql.setFloat(5, telemetry.getHEAT_LOAD());
				prep_sql.setInt(6, telemetry.getUV_STATUS());
				prep_sql.setInt(7, telemetry.getHUMI_STATUS());
				
				prep_sql.setTimestamp(8, parseDate(telemetry.getDateTime()));
							
				prep_sql.executeUpdate();
				
			}catch(Exception e){

				System.out.println("Error: " + e.getMessage());
				
				link.Close_link();
				
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error saving telemetry").build();
								
			}

		link.Close_link();
		
		return Response.status(Response.Status.OK).build();
	    
	    }

	  	
	  	
	private static java.sql.Timestamp parseDate(String s) {
		
		DateFormat formatter = new SimpleDateFormat("MM-dd-yy HH:mm:ss");
		Date date = null;

		try {
	
			date = formatter.parse(s);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	return new java.sql.Timestamp(date.getTime());
	
	}
	
	
	  	
} 