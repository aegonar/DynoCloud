package com.dynocloud.node.api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/overview")
public class OverviewResource {
	
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOverview() {
      
      System.out.println("[GET] /overview");
	  
	  link.Open_link();
		
	  ArrayList<Overview> OverviewList = new ArrayList<Overview>();
		
		try{
			String query_getEnclosures = "SELECT * FROM EnclosureNode";
			prep_sql = link.linea.prepareStatement(query_getEnclosures);
			
			
			ResultSet rs_query_getEnclosures= prep_sql.executeQuery();
			
				while(rs_query_getEnclosures.next()){
	
					//System.out.println("EnclosureNodeIDs: " + rs_query_getEnclosures.getInt("EnclosureNodeID"));
						
					Overview overview = new Overview();
					
					try{
						String query_getOverview= "SELECT Telemetry.EnclosureNodeID, max(DateTime) as DateTime, Temperature, Humidity, "
								+ "Load_IR, Load_IC, State_UV, State_HUM, EnclosureNode.Name as EnclosureName, EnclosureNode.PetProfileID, "
								+ "PetProfiles.Name as ProfileName FROM Telemetry, EnclosureNode, PetProfiles "
								+ "WHERE EnclosureNode.EnclosureNodeID=Telemetry.EnclosureNodeID "
								+ "AND EnclosureNode.PetProfileID=PetProfiles.PetProfileID AND Telemetry.EnclosureNodeID=?;";
						
						prep_sql = link.linea.prepareStatement(query_getOverview);
						
						prep_sql.setInt(1, rs_query_getEnclosures.getInt("EnclosureNodeID"));
																		
						ResultSet rs_query_getOverview= prep_sql.executeQuery();
						
						if (!rs_query_getOverview.next() ) {
							System.out.println("rs_query_getOverview no data");
							link.Close_link();
							return Response.status(Response.Status.FORBIDDEN).entity("Module not found").build();	
						} else {
							
							
							overview.setTemperature(rs_query_getOverview.getFloat("Temperature"));
							overview.setHumidity(rs_query_getOverview.getFloat("Humidity"));
							overview.setLoad_IR(rs_query_getOverview.getFloat("Load_IR"));
							overview.setLoad_IC(rs_query_getOverview.getFloat("Load_IC"));
							
							overview.setEnclosureNodeID(rs_query_getOverview.getInt("EnclosureNodeID"));
							overview.setState_UV(rs_query_getOverview.getInt("State_UV"));
							overview.setState_HUM(rs_query_getOverview.getInt("State_HUM"));
							overview.setPetProfileID(rs_query_getOverview.getInt("PetProfileID"));

							overview.setEnclosureName(rs_query_getOverview.getString("EnclosureName"));
							overview.setProfileName(rs_query_getOverview.getString("ProfileName"));
							
							Timestamp myTimestamp = rs_query_getOverview.getTimestamp("DateTime");
							String S = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(myTimestamp);			
							overview.setDateTime(S);
							
							
							OverviewList.add(overview);
						}
						
					}catch(Exception e){

						System.out.println("Error: " + e.getMessage());
						
						link.Close_link();
						
						return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();
						
					}

				}
		}catch(Exception e){

			System.out.println("Error: " + e.getMessage());
			
			link.Close_link();
			
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error loading enclosures").build();
			
		}

	link.Close_link();
	
	ObjectMapper mapper = new ObjectMapper();
	String jsonString = null;
	
	try {
		jsonString = mapper.writeValueAsString(OverviewList);
		
	} catch (JsonProcessingException e) {
		
		System.out.println("Error mapping to json: " + e.getMessage());
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("JSON mapping error").build();
	}

  return Response.ok(jsonString, MediaType.APPLICATION_JSON).build();
  
  }

} 