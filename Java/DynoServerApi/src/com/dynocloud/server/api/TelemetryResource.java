package com.dynocloud.server.api;

import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.ArrayList;
import java.util.Date;


@Path("/publish")
public class TelemetryResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
//	@GET
//  	@Produces(MediaType.APPLICATION_JSON)
//	public String GetTelemetry() throws Exception{
//		
//		System.out.println("Telemetry [GET]");
//		
//		String result="Telemetry";
//		
//		return result;
//	}
/*	
@Logged
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<Telemetry> GetTelemetry(@Context HttpHeaders headers) {
	  
	  System.out.println("Telemetry [GET]");
	  
		Session session = new Session(headers);
        User currentUser = session.getUser();
        
        int userID=currentUser.getUserID();
	  
	  link.Open_link();
		
	  ArrayList<Telemetry> list = new ArrayList<Telemetry>();
		
		try{
			String query_telemetry = "SELECT * FROM Telemetry where `UserID` = ?";
			prep_sql = link.linea.prepareStatement(query_telemetry);
			
			prep_sql.setInt(1, userID);
			
			ResultSet rs_query_telemetry = prep_sql.executeQuery();
			System.out.println("executeQuery");
			
				while(rs_query_telemetry.next()){
					
					Telemetry telemetry = new Telemetry();
					
					//`EnclosureNodeID`,`Temperature`,`Humidity`,`Load_IR`,`Load_IC`,`State_UV`,`State_HUM`
					
					telemetry.setCLIENTID(rs_query_telemetry.getInt("EnclosureNodeID"));
					telemetry.setTEMP(rs_query_telemetry.getInt("Temperature"));
					telemetry.setRH(rs_query_telemetry.getInt("Humidity"));
					telemetry.setIR_PW(rs_query_telemetry.getInt("Load_IR"));
					telemetry.setIC_PW(rs_query_telemetry.getInt("Load_IC"));
					telemetry.setUV_STATUS(rs_query_telemetry.getInt("State_UV"));
					telemetry.setHUMI_STATUS(rs_query_telemetry.getInt("State_HUM"));
					
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
			
		}

	link.Close_link();
	    
  return list;
  }
 */ 	@Logged
	  	@POST
	    @Consumes({MediaType.APPLICATION_JSON})
	    //@Produces({MediaType.APPLICATION_JSON})
	    public Response postTelemetry(Telemetry telemetry, @Context HttpHeaders headers) throws Exception{
	  		
	  		
		Session session = new Session(headers);
        User currentUser = session.getUser();
        
        int userID=currentUser.getUserID();
	        //String result=null;
        
        System.out.println("["+currentUser.getUserName()+"] [POST] /publish");
    	
	        //System.out.println("Telemetry [POST]");
	        //System.out.println(telemetry);
	          
	        link.Open_link();
			
			try{
				String query_telemetry = "INSERT INTO Telemetry (`EnclosureNodeID`,`Temperature`,`Humidity`,`Load_IR`,`Load_IC`,`State_UV`,`State_HUM`,`DateTime`,`CentralNodeID`,`UserID`) VALUES (?,?,?,?,?,?,?,?,?,?);";
				prep_sql = link.linea.prepareStatement(query_telemetry);
								
				prep_sql.setInt(1, telemetry.getCLIENTID());
				prep_sql.setFloat(2, telemetry.getTEMP());
				prep_sql.setFloat(3, telemetry.getRH());
				prep_sql.setFloat(4, telemetry.getIR_PW());
				prep_sql.setFloat(5, telemetry.getIC_PW());
				prep_sql.setInt(6, telemetry.getUV_STATUS());
				prep_sql.setInt(7, telemetry.getHUMI_STATUS());
				
				prep_sql.setTimestamp(8, parseDate(telemetry.getDateTime()));
				
				prep_sql.setInt(9, telemetry.getCentralNodeID());
				prep_sql.setInt(10, userID);
							
				prep_sql.executeUpdate();
				
			}catch(Exception e){

				System.out.println("Error: " + e.getMessage());
				
				link.Close_link();
				
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error publishing telemetry").build();
								
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