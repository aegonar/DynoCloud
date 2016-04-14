import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {

		
	private static Database_connection link = new Database_connection();
	private static PreparedStatement prep_sql;
	
	public static void main (String a[]){
			
		link.Open_link();
					
			try{				
				String query = "INSERT INTO OverrideHistory (`UserID`,`CentralNodeID`,`EnclosureNodeID`,`DateTime`) VALUES ('2', '1', '2', ?);";
				
				
				prep_sql = link.linea.prepareStatement(query);
				
				prep_sql.setTimestamp(1, parseDate("04/13/16 22:11:26"));
				
				prep_sql.executeUpdate();


			}catch(Exception e){

				System.out.println("Error: " + e.getMessage());
				
				link.Close_link();

			}

		link.Close_link();
		
	}
	
//	private static java.sql.Timestamp getCurrentTimeStamp() {
//
//		java.util.Date today = new java.util.Date();
//		return new java.sql.Timestamp(today.getTime());
//
//	}
	
	private static java.sql.Timestamp parseDate(String s) {
		
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		Date date = null;

		try {
	
			date = formatter.parse(s);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	return new java.sql.Timestamp(date.getTime());
	
	}

}
