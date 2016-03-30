package com.dynocloud.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


@Path("/telemetry")
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
	
@Logged
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayList<Telemetry> GetTelemetry() {
	  
	  System.out.println("Telemetry [GET]");
	  
	  link.Open_link();
		
	  ArrayList<Telemetry> list = new ArrayList<Telemetry>();
		
		try{
			String query_telemetry = "SELECT * FROM Telemetry";
			prep_sql = link.linea.prepareStatement(query_telemetry);
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
  
	  	@POST
	    @Consumes({MediaType.APPLICATION_JSON})
	    @Produces({MediaType.APPLICATION_JSON})
	    public String PostTelemetry(Telemetry telemetry) throws Exception{
	  		
	  		
	  		
	        String result=null;
	        System.out.println("Telemetry [POST]");
	        System.out.println(telemetry);
	          
	        link.Open_link();
			
			try{
				String query_telemetry = "INSERT INTO Telemetry (`EnclosureNodeID`,`Temperature`,`Humidity`,`Load_IR`,`Load_IC`,`State_UV`,`State_HUM`) VALUES (?,?,?,?,?,?,?);";
				prep_sql = link.linea.prepareStatement(query_telemetry);
								
				prep_sql.setInt(1, telemetry.getCLIENTID());
				prep_sql.setFloat(2, telemetry.getTEMP());
				prep_sql.setFloat(3, telemetry.getRH());
				prep_sql.setFloat(4, telemetry.getIR_PW());
				prep_sql.setFloat(5, telemetry.getIC_PW());
				prep_sql.setInt(6, telemetry.getUV_STATUS());
				prep_sql.setInt(7, telemetry.getHUMI_STATUS());
				
				prep_sql.executeUpdate();
				
				System.out.println("Telemetry [Execute Insert]");
				
				result="ok";
				
			}catch(Exception e){

				System.out.println("Error: " + e.getMessage());
				
				link.Close_link();
				
				result="Error: " + e.getMessage();
					
				return result;
				
			}

		link.Close_link();
	        
	    return result;
	    
	    }

} 