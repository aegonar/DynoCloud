package com.dynocloud.telemetry;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Daemon {
	
	public static void main (String[] args){
		
		String cmd = "mosquitto_sub -t localhost -t local";
        ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            String s;
            BufferedReader stdout = new BufferedReader (
                new InputStreamReader(p.getInputStream()));
            while ((s = stdout.readLine()) != null) {
                //System.out.println(s);
                
                
                
                
                
                 //String cmd2 = "mosquitto_pub -h localhost -t local2 -m \'Server: "+s+"\'";
                 
                 String cmd2 = "curl -s -H \"Content-Type: application/json\" -d \'" + s + "\' -X POST \"http://localhost/api/telemetry\"";
                
                 System.out.println(cmd2);
                 ProcessBuilder pb2 = new ProcessBuilder("/bin/bash", "-c", cmd2);
                
               // String cmd2 = "mosquitto_pub -h localhost -t local2 -m \'Server: "+s+"\'";
                
                //System.out.println(cmd2);
//               ProcessBuilder pb2 = new ProcessBuilder("mosquitto_pub", "-h", "localhost", "-t", "local2", "-m", "\'Server: "+s+"\'");
                
                pb2.redirectErrorStream(true);
                try {
                    Process p2 = pb2.start();
                    String s2;
                    BufferedReader stdout2 = new BufferedReader (
                        new InputStreamReader(p2.getInputStream()));
                    while ((s2 = stdout2.readLine()) != null) {
                        System.out.println(s2);
                    }
                    p2.getInputStream().close();
                    p2.getOutputStream().close();
                    p2.getErrorStream().close();
                 } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                
                
            }
            p.getInputStream().close();
            p.getOutputStream().close();
            p.getErrorStream().close();
         } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		
		
	}

}