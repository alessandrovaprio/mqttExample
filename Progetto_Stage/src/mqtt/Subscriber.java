package mqtt;

import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriber implements MqttCallback {
	
	public enum Topic {INFORMATICA,CHIMICA,BIOLOGIA,MATEMATICA,FISICA,ECONOMIA,FILOSOFIA}; //tipo enumeratore
	
	/*MqttClient: contiene la parte logica del protocollo per connettersi e comunicare al broker mqtt 
	  cio� all'indirizzo 'tcp://127.0.0.1:1883'. 
	  127.0.0.1: e' l'indirizzo locale della macchina; mentre 1883 � la porta*/
	public MqttClient client;
	public ChatEvent visualize;
	
	public Subscriber(ChatEvent evt) {
		visualize = evt;
	}
	
	/*connectionLost(Throwable cause): quando il client ha perso la connessione con il mosquitto brokers
	  System.out.println("Il client ha perso la connessione: " +cause); 
	  (esempio: Il client ha perso la connessione: Connessione persa (32109))*/
	@Override
	public void connectionLost(Throwable arg0) {
		//questo metodo viene chiamato quando la connessione al server viene persa
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		//quando la pubblicazione in uscita e' completa (es: 'Consegna completata')
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		/*Metodo che stampa il messaggio ricevuto dal server (es: Messaggio ricevuto per il topic INFORMATICA: [Daniele]:Ciao come va?*/
		String strmsg = new String(message.getPayload(), StandardCharsets.UTF_8);
		System.out.println("Messaggio ricevuto per il topic " +topic+ ": " +strmsg);
		
		/*ActionEvent: evento che compie un'azione nel premere un pulsante da parte dell'utente*/
		new ActionEvent(visualize.button, ActionEvent.ACTION_PERFORMED, "");
		//visualize.messagesReceived.append(strmsg);
		/*append(): e' un metodo che aggiunge il valore stringa del suo argomento*/
		visualize.messagesReceived.append("Topic-"+topic+ System.lineSeparator());
		visualize.messagesReceived.append("  ->  "+strmsg+ System.lineSeparator());
	}
}
