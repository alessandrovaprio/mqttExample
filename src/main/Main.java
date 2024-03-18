package main;

import org.eclipse.paho.client.mqttv3.MqttException;


import mqtt.MqttManager;

public class Main {

	public static void main (String[] args) {
		try {
			// mi collego in locale alla porta 1883 chiamandomi myjavaApp e collegandomi alla coda test_sensor_data
			MqttManager mqtt = MqttManager.create("tcp://127.0.0.1:1883", "myjavaAPP", "test_sensor_data");
			// pubblico il messaggio "HELLO THERE" dentro alla coda test_sensor_data
			mqtt.postOnQueue("test_sensor_data", "HELLO THERE");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
