package mqtt;

import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {
	
	public enum Topic {INFORMATICA,CHIMICA,BIOLOGIA,MATEMATICA,FISICA,ECONOMIA,FILOSOFIA}; //tipo enumeratore
	
	/*MqttClient: contiene la parte logica del protocollo per connettersi e comunicare al broker mqtt 
	  cio� all'indirizzo 'tcp://127.0.0.1:1883'. 
	  127.0.0.1: e' l'indirizzo locale della macchina; mentre 1883 � la porta*/
	public MqttClient client;
	// public ChatEvent visualize;
	
	public Publisher() {
		/*Indirizzo del broker MQTT; 127.0.0.1: e' l'indirizzo locale della macchina; mentre 1883 � la porta*/
		//String brokerURL = "tcp://127.0.0.1:1883";
		
		/*ritorna e genera un identificatore client in modo casuale in base al login dell'utente corrente*/ 
		//String clientId = MqttClient.generateClientId(); 
				
		try {
			client = new MqttClient("tcp://127.0.0.1:1883", MqttClient.generateClientId());
		}
		catch(MqttException e) {
			e.printStackTrace();
		}
		// visualize = new ChatEvent(client);
	}
	
	
	/*quando chiamiamo il metodo start() con l'oggetto thread, significa che il thread inizier� la sua esecuzione.
	  Il metodo start() chiama internamente il metodo run() dell'interfaccia Runnable e esegue il codice specificato 
	  nel metodo run() sovrascrivibile nel nostro thread*/
	public void start(MqttConnectOptions opts, Subscriber sub) {
		try {
			/*MqttConnectOptions: contiene il set di opzioni che controllano il modo in cui il client si connette a un server*/
			if(opts == null) {

				opts = new MqttConnectOptions();
			}
			
			/*setCallback(): la callback viene attivata quando ascolta messages write, cio� l'utente invia i messaggi che vengono
			  ricevuti nella textarea dei messaggi ricevuti. La setCallback() gestisce i tre metodi Override avendo dichiarato come 
			  classe implements MqttCallback, perch� la callback � quella che ascolta la messages write e infine riceve i messaggi*/
			client.connect(opts); //si connette a un server MQTT utilizzando le opzioni specificate 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
