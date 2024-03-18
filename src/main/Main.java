package main;

import org.eclipse.paho.client.mqttv3.MqttException;


import mqtt.MqttManager;

public class Main {

	public static void main (String[] args) {
		try {
			MqttManager mqtt = MqttManager.create("tcp://127.0.0.1:1883", "myjavaAPP", "test_sensor_data");
			mqtt.postOnQueue("test_sensor_data", "HELLO THERE");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
