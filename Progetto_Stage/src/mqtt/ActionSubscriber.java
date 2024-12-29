package mqtt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;



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
public class ActionSubscriber implements ActionListener, KeyListener {
	
	private ChatEvent obj;
	public MqttConnectOptions opts = new MqttConnectOptions();
	public ActionSubscriber(ChatEvent obj) {
		this.obj=obj;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//metodo invocato quando e' stata digitata una chiave
	}

	/*metodo invocato quando si attiva il bottone Connect scrivendo il nome utente all'interno del form 'Username' e infine viene prenuto.
	  Il pulsante INVIA si attiva solo quando l'utente scrive nella casella del messaggio.*/
	@Override
	public void keyPressed(KeyEvent e) {
		/*obj.button.setEnabled(!obj.textField.getText().isEmpty());
		obj.sendButton.setEnabled(!obj.textToSend.getText().isEmpty());*/
		//setEnabled(): � un metodo che attiva o disattiva il pulsante, cio� se si passa TRUE attiva; invece FALSE disattiva
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//metodo invocato quando il bottone e' stato rilasciato
	}

	/*il metodo actionPerformed() viene richiamato automaticamente ogni volta che si digita il bottone sul componente registrato.
	  Viene invocato quando si verifica un'azione*/
	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedTopic = obj.topics.getSelectedItem(); /*getSelectedItem(): restituisce l'elemento selezionato*/
		
		/*CONDIZIONE UNO: l'utente preme il bottone 'Connect' di cui si aprono le due textarea, quindi riguarda il 1� controllo dell'if()*/
    	//System.out.println(e.getSource());
		if(e.getSource() == obj.button) {
			try {
				
				/*subscribe(): metodo che si connette quando ci sono due code uguali e quindi i due client si comunicano entrambi tra loro*/
				obj.c.subscribe(selectedTopic); /*metodo legato al client mqtt (MqttClient) sottoscrivo al topic scelto*/			
				obj.username.setEnabled(false); //disabilito per non modificare piu' nome utente su casella di testo 'Username'.

				// se non e' vuoto allora metto un piccolo separatore (vuol dire che cambio facolta' e devo ricevere tutto)
				if( obj.messagesReceived != null && !obj.messagesReceived.toString().isEmpty()) { 
					obj.messagesReceived.append("--------------------------------------"); 
					obj.messagesReceived.append(System.lineSeparator());
				}
				obj.add(obj.textToSend);/*L'utente invia i messaggi di chat al destinatario*/
				obj.add(obj.messagesReceived);/*riceve messaggi inviati dall'utente sulla textarea dei messaggi ricevuti*/
				obj.add(obj.sendButton);/*L'utente invia i messaggi di chat al destinatario tramite il pulsante INVIA*/
			}
			catch(MqttException e1) {
				e1.printStackTrace();
			}
		}
		
		/*CONDIZIONE DUE: l'utente preme il bottone 'INVIA', quindi riguarda il 2� controllo dell'if() che permette di inviare e pubblicare 
  	  	  i messaggi nella textarea dei 'messaggi ricevuti'*/
  	    /*getSource(): restituisce l'oggetto su cui si � verificato l'evento confrontandolo con il pulsante per inviare i messaggi
  	      aprendo la chat sui messaggi ricevuti e scrivendoli*/
		if(e.getSource() == obj.sendButton) {
			try {
				
				/*String m: viene popolato un messaggio m di tipo stringa. Per popolarlo concateno tra [] il nome che � stato inserito 
  			  	  nella casella 'Username' in questa maniera viene tra [] username dell'utente che la inviato, concatenato viene scritto
  			      dopo il messaggio, cio� l'utente che ha scritto dentro la casella di testo 'Username' che voleva inviare. Quando
  			      l'utente digita INVIO, l'evento relativo a quel bottone, finisce nella textarea dei messaggi ricevuti, compone una stringa
  			      con dentro il nome di chi l'ha messo tra []: il tasto del messaggio. Per mandarlo dentro la coda non lo si pu� mandare 
  			      direttamente ma bisogna formattarlo correttamente, quindi si istanzia una variabile 'msg' di tipo oggetto 
  			      MqttMessage, si prendono i byte della stringa, quando si � creato/istanziato questo oggetto viene pubblicato,
  			      una volta pubblicato scatter� poi l'evento che legge dalla coda e infine va a popolare la textarea dei messaggi ricevuti*/
				String m = "[" +obj.username.getText()+ "]:" +obj.textToSend.getText();
				MqttMessage msg = new MqttMessage(m.getBytes(StandardCharsets.UTF_8));
				/*il metodo PUBLISH pubblica su una coda con i parametri publish(nome della mia coda, messaggio che voglio pubblicare),
  			      quindi in 'selectedTopic' ho la coda in cui voglio pubblicare, mentre in 'msg' il messaggio*/
				obj.c.publish(selectedTopic, msg);
				obj.textToSend.setText(null);//pulisco dopo aver pubblicato sulla coda
			}
			catch(MqttException e1) {
				e1.printStackTrace();
			}
		}
	}
}
