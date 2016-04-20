package com.dynocloud.node.queue;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

public class Daemon {
	
	public static void main (String[] args) {
		
		String hostMQTT=null;
		
		try {
			hostMQTT = args[0];
		System.out.println("MQTT host: " + hostMQTT);
		} catch (Exception e) {
			System.out.println("MQTT Broker host not set as arguments");
			System.exit(1);
		}
		
		String serverMQTT=null;
		
		try {
			serverMQTT = args[1];
		System.out.println("Server MQTT host: " + serverMQTT);
		} catch (Exception e) {
			System.out.println("Server MQTT host not set as arguments");
			System.exit(1);
		}
		
		MQTT node = new MQTT();
		
		try {
			
			node.setHost(hostMQTT, 1883);		
		} catch (URISyntaxException e) {
			System.out.println("Error finding Broker");
			main(args);
		}

		node.setKeepAlive((short) 5);
		node.setWillTopic("will");
		node.setWillMessage("Node disconnected");
		
		
		BlockingConnection connection = node.blockingConnection();
		
		try {
			connection.connect();	
		} catch (Exception e) {
			System.out.println("Error connecting to Broker");
			main(args);
		}
		
		Topic[] topics = {new Topic("/DynoCloud/Queue", QoS.AT_LEAST_ONCE)};
		
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

//-------------------------------------------------------------------	
			
			System.out.println("Relaying message to server");
			
			MQTT server = new MQTT();
			
			try {
				
				server.setHost(serverMQTT, 1883);
				
				BlockingConnection server_connection = server.blockingConnection();
				
				try {
					
					server_connection.connect();
					
					server_connection.publish("/DynoCloud", payloadString.getBytes(), QoS.AT_LEAST_ONCE, false);
					System.out.println("Message relayed to server");
					
				} catch (Exception e) {
					System.out.println("Error relaying message");
				}
							
			} catch (URISyntaxException e) {
				System.out.println("Error connecting to Server");
			}

			System.out.println("---------------------------------------");				
		}
		
	}
	
}
