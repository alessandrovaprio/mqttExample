package mqtt;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import login.LoginJFrame;
import mqtt.Publisher.Topic;

/*ACTIONLISTENER: interfaccia dell'ascoltatore per ricevere eventi d'azione.
Implementa l'interfaccia e l'oggetto creato
KEYLISTENER: e' l'interfaccia del listener della tastiera java che estende l'interfaccia EventListener e presenta le seguenti firme
dei metodi:
- @Override
  public void keyTyped(KeyEvent e) {}

- @Override
  public void keyPressed(KeyEvent e) {}

- @Override
  public void keyReleased(KeyEvent e) {}*/
/**
 * Classe che implementa l'interfaccia e gli oggetti creati ActionListener che è l'interfaccia dell'ascoltatore per ricevere eventi 
 * d'azione, e KeyListener che è l'interfaccia del listener della tastiera java che estende l'interfaccia EventListener
 * @author Daniele Ferrando
 * @version 1.0
 */
public class ActionSubscriber implements ActionListener, KeyListener {
	
	private final List<Subscriber> subscribers = new ArrayList<>();
	private LoginJFrame logoutButton;
	public ChatEvent obj;
	/*MqttConnectOptions: contiene il set di opzioni che controllano il modo in cui il client si connette a un server per evitare
	  che il file si spacchi*/
	public MqttConnectOptions opts = new MqttConnectOptions();
	
	/**
	 * Costruttore che genera un oggetto creato
	 * @param obj
	 */
	public ActionSubscriber(ChatEvent obj) {
		this.obj=obj;
	}

	/**
	 * Metodo invocato quando e' stata digitata una chiave
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		//metodo invocato quando e' stata digitata una chiave
	}

	/**
	 * Metodo invocato quando si attiva il bottone connect
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	/**
	 * Metodo invocato quando il bottone e' stato rilasciato
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		//metodo invocato quando il bottone e' stato rilasciato
	}

	/**
	 * Il metodo actionPerformed() viene richiamato automaticamente ogni volta che si digita il bottone sul componente registrato e
	 * viene invocato quando si verifica un'azione.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedTopic = obj.topics.getSelectedItem(); /*getSelectedItem(): restituisce l'elemento selezionato*/
		
		
		/*CONDIZIONE DUE: l'utente preme il bottone 'INVIA', quindi riguarda il 2° controllo dell'if() che permette di inviare e pubblicare 
  	  	  i messaggi nella textarea dei 'messaggi ricevuti'*/
  	    /*getSource(): restituisce l'oggetto su cui si e' verificato l'evento confrontandolo con il pulsante per inviare i messaggi
  	      aprendo la chat sui messaggi ricevuti e scrivendoli*/ 
		if(e.getSource() == obj.sendButton) {
			try {
				
				/*String m: viene popolato un messaggio m di tipo stringa. Per popolarlo concateno tra [] il nome che e' stato inserito 
  			  	  nella casella 'Username' in questa maniera viene tra [] username dell'utente che la inviato, concatenato viene scritto
  			      dopo il messaggio, cioe' l'utente che ha scritto dentro la casella di testo 'Username' che voleva inviare. Quando
  			      l'utente digita INVIO, l'evento relativo a quel bottone, finisce nella textarea dei messaggi ricevuti, compone una stringa
  			      con dentro il nome di chi l'ha messo tra []: il tasto del messaggio. Per mandarlo dentro la coda non lo si puo' mandare 
  			      direttamente ma bisogna formattarlo correttamente, quindi si istanzia una variabile 'msg' di tipo oggetto 
  			      MqttMessage, si prendono i byte della stringa, quando si e' creato/istanziato questo oggetto viene pubblicato,
  			      una volta pubblicato scattera' poi l'evento che legge dalla coda e infine va a popolare la textarea dei messaggi ricevuti*/
				String m = "[" +obj.username.getText()+ "]:" +obj.textToSend.getText();
				MqttMessage msg = new MqttMessage(m.getBytes(StandardCharsets.UTF_8));
				/*il metodo PUBLISH pubblica su una coda con i parametri publish(nome della mia coda, messaggio che voglio pubblicare),
  			      quindi in 'selectedTopic' ho la coda in cui voglio pubblicare, mentre in 'msg' il messaggio*/
				if(!obj.c.isConnected()) {
					obj.c.connect(opts);
				}
				obj.c.publish(selectedTopic, msg);
											
				
				obj.textToSend.setText(null);//pulisco dopo aver pubblicato sulla coda
			}
			catch(MqttException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == obj.logoutButton) {
            try {
                
                obj.setVisible(false); //chiudo il frame corrente
                for(Topic t : Topic.values()) {
					try {
						obj.c.unsubscribe(t.name());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
                //e' andato a buon fine e apriro' il form successivo
                new LoginJFrame(null);
            }
            /*catch(MqttException cEx) {
            	this.login.error.setText("Connessione fallita: " +cEx.getMessage());
                this.login.error.setVisible(true);
            }*/
            catch(Exception e1) { //secondo catch(): serve ad evitare di spaccare il file in caso di eccezioni diverse
            	System.out.println("Errore: " +e1.getMessage());
            	this.logoutButton.error.setText("Errore: " +e1.getMessage());
                this.logoutButton.error.setVisible(true);
                e1.printStackTrace();
            }
        }
	}
	
	public void subscribe(Subscriber subscriber) {
		subscribers.add(subscriber);
	}
}
