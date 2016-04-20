package com.dynocloud.node.telemetry;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

public class Daemon {
	
	public static void main (String[] args) {
		
		String host=null;
		
		try {
			host = args[0];
		System.out.println("MQTT host: " + host);
		} catch (Exception e) {
			System.out.println("MQTT Broker host not set as arguments");
			System.exit(1);
		}
		
		MQTT mqtt = new MQTT();
		
		try {
			
			mqtt.setHost(host, 1883);
					
		} catch (URISyntaxException e) {
			System.out.println("Error finding Broker");
			main(args);
		}

		mqtt.setKeepAlive((short) 5);
		mqtt.setWillTopic("will");
		mqtt.setWillMessage("Node disconnected");
		
		
		BlockingConnection connection = mqtt.blockingConnection();
		
		try {
			connection.connect();	
		} catch (Exception e) {
			System.out.println("Error connecting to Broker");
			main(args);
		}
		
		Topic[] topics = {new Topic("/DynoCloud/VariableSend", QoS.AT_LEAST_ONCE)};
		
		try {
			
			@SuppressWarnings("unused")
			byte[] qoses = connection.subscribe(topics);
			
		} catch (Exception e) {
			System.out.println("Error subscribing to topic");
			main(args);
		}
		
		while(true){
			
			Message message=null;
			
			try {
				message = connection.receive();
			} catch (Exception e) {
				System.out.println("Error receiving message");
				main(args);
			}
			
			String topic = message.getTopic();		
			byte[] payload = message.getPayload();			
			String payloadString = new String(payload, StandardCharsets.UTF_8);
			
			System.out.println(topic + " " + payloadString);			
			
			String url = "http://localhost/api/publish";
			URL obj;
			HttpURLConnection con = null;
			
			try {
				
				obj = new URL(url);
				
			try {
				
				con = (HttpURLConnection) obj.openConnection();
				
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/json");
				//con.setRequestProperty("Authorization", "Bearer 3p35vittr361q4socmtqhmeos6");

				String urlParameters = payloadString;
				
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
	
				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + urlParameters);
				System.out.println("Response Code : " + responseCode);
	
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				System.out.println(response.toString());
						
			} catch (MalformedURLException e) {
				System.out.println("Error connecting to Server");
			}
			} catch (IOException e) {
				System.out.println("Server Response: Malformed Message");
			}
			
			message.ack();
//-------------------------------------------------------------------			
			
//			System.out.println("Relaying message to server");
//			
//			MQTT server = new MQTT();
//			
//			try {
//				
//				server.setHost("localhost", 1883);
//				
//				BlockingConnection server_connection = server.blockingConnection();
//				
//				try {
//					
//					server_connection.connect();
//					
//					server_connection.publish("server", payloadString.getBytes(), QoS.AT_LEAST_ONCE, false);
//					System.out.println("Message relayed to server");
//					
//				} catch (Exception e) {
//					System.out.println("Error relaying message");
//					//TODO update local DB
//				}
//							
//			} catch (URISyntaxException e) {
//				System.out.println("Error connecting to Server");
//			}
							
		}
		
	}

}
