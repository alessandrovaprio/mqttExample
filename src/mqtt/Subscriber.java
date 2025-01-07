package mqtt;

import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import org.eclipse.paho.client.mqttv3.*;

/*ACTIONLISTENER: interfaccia dell'ascoltatore per ricevere eventi d'azione.
Classe Subscriber: Implementa l'interfaccia e l'oggetto creato
KEYLISTENER: e' l'interfaccia del listener della tastiera java che estende l'interfaccia EventListener e presenta le seguenti firme
dei metodi:
- @Override
  public void keyTyped(KeyEvent e) {}

- @Override
  public void keyPressed(KeyEvent e) {}

- @Override
  public void keyReleased(KeyEvent e) {}*/

/**
 * Classe che implementa l'interfaccia e l'oggetto creato del protocollo di messaggistica MqttCallback
 * @author Daniele Ferrando
 * @version 1.0
 */
public class Subscriber implements MqttCallback {
	
	public enum Topic {INFORMATICA,CHIMICA,BIOLOGIA,MATEMATICA,FISICA,ECONOMIA,FILOSOFIA}; //tipo enumeratore
	public ChatEvent visualize;
	public MqttClient client;
	
	/**
	 * Costruttore che genera un oggetto creato
	 * @param event
	 */
	public Subscriber(ChatEvent event) {
		this.visualize=event;
	}

	/*connectionLost(Throwable cause): quando il client ha perso la connessione con il mosquitto brokers
	  System.out.println("Il client ha perso la connessione: " +cause); 
	  (esempio: Il client ha perso la connessione: Connessione persa (32109))*/
	/**
	 * Metodo che viene chiamato quando il client ha perso la connessione al server con il mosquitto brokers 
	 */
	@Override
	public void connectionLost(Throwable arg0) {
		//questo metodo viene chiamato quando la connessione al server viene persa
	}

	/**
	 * Metodo quando la pubblicazione in uscita e' completa
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		//quando la pubblicazione in uscita e' completa (es: 'Consegna completata')
	}

	/**
	 * Metodo che stampa il messaggio ricevuto dal server
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		/*Metodo che stampa il messaggio ricevuto dal server (es: Messaggio ricevuto per il topic INFORMATICA: [Daniele]: Ciao come va?)*/
		String strmsg = new String(message.getPayload(), StandardCharsets.UTF_8);
		System.out.println("Messaggio ricevuto per il topic " +topic+ ": " +strmsg);
		
		/*ActionEvent: evento che compie un'azione nel premere un pulsante da parte dell'utente*/
		ActionEvent ae = new ActionEvent(visualize.button, ActionEvent.ACTION_PERFORMED, "");
		//visualize.messagesReceived.append(strmsg);
		/*append(): e' un metodo che aggiunge il valore stringa del suo argomento nella textArea dei Messaggi Ricevuti*/
		visualize.messagesReceived.append("Topic-"+topic+ System.lineSeparator()+ " -> " +strmsg+ System.lineSeparator());
	}
}