package login;

import java.awt.event.*;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import mqtt.ChatEvent;

//classe usata per gestire gli eventi con il pattern observer/notify
/**
 * Classe che implementa l'interfaccia e gli oggetti creati ActionListener che è l'interfaccia dell'ascoltatore per ricevere eventi 
 * d'azione, e KeyListener che è l'interfaccia del listener della tastiera java che estende l'interfaccia EventListener
 * @author Daniele Ferrando
 * @version 1.0
 */
public class Login implements ActionListener, KeyListener {
	
	private LoginJFrame login;
	
	/**
	 * Costruttore che genera un oggetto creato
	 * @param login
	 */
	public Login(LoginJFrame login) {
		this.login=login;
	}
	
	/**
	 * Metodo invocato quando e' stata digitata una chiave
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		//metodo invocato quando e' stata digitata una chiave
	}

	/*metodo invocato quando si attiva il bottone Login scrivendo il nome utente all'interno del form 'Username', la Password e infine viene 
	  prenuto.*/
	/**
	 * Metodo invocato quando si attiva il bottone Login scrivendo il nome utente all'interno del form username, 
	 * la Password e infine viene prenuto
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

	/*il metodo actionPerformed() viene richiamato automaticamente ogni volta che si digita il bottone sul componente registrato.
	  Viene invocato quando si verifica un'azione*/
	/**
	 * Il metodo actionPerformed() viene richiamato automaticamente ogni volta che si digita il bottone sul componente registrato.
	   Viene invocato quando si verifica un'azione.
	 */
	public void actionPerformed(ActionEvent e) {
        /*CONDIZIONE UNO: l'utente digita il bottone 'Login' dopo aver inserito i campi 'Username' e 'Password' 
          di cui a seguire si apre il frame della chat messaggistica*/
        //System.out.println(e.getSource());
        if(e.getSource() == login.loginButton) {
            try {
                //controllo se i campi sono stati compilati, se non lo sono faccio uscire un form con errore.
                if(login.usernameField==null || login.usernameField.getText().isEmpty() || login.passwordField==null || login.passwordField.getPassword().length==0) {
                	/*this.errorArea.setText("Nessun nome utente o password forniti");
                    this.errorArea.setVisible(true);*/
                	this.login.error.setText("Nessun nome utente o password forniti");
                    this.login.error.setVisible(true);
                    return;
                }
                //imposto utente e password per connettersi alla coda
                MqttConnectOptions opt = new MqttConnectOptions();
                opt.setUserName(login.usernameField.getText());
                opt.setPassword(login.passwordField.getPassword());

                login.publisher.client.connect(opt);
                //publisher.client.connect(opt);

                login.setVisible(false); //chiudo il frame corrente 
                
                //e' andato a buon fine e apriro' il form successivo
                new ChatEvent(login.publisher.client, login.usernameField.getText());
                //new ChatEvent(publisher.client, usernameField.getText());
            }
            catch(MqttException cEx) {
            	/*this.errorArea.setText("Connessione fallita: " + cEx.getMessage());
                this.errorArea.setVisible(true);*/
            	this.login.error.setText("Connessione fallita: " + cEx.getMessage());
                this.login.error.setVisible(true);
            }
            catch(Exception e1) {
            	System.out.println("Errore: " +e1.getMessage());
                /*this.errorArea.setText("Errore: " +e1.getMessage());
                this.errorArea.setVisible(true);*/
            	this.login.error.setText("Errore: " +e1.getMessage());
                this.login.error.setVisible(true);
                e1.printStackTrace();
            }
        }
        /*CONDIZIONE DUE: l'utente preme il bottone 'Reset' per reimpostare nuovamente 'Username' e 'Password'*/
        if(e.getSource() == login.resetButton) {
            try {
                login.passwordField.setText(null);
                login.usernameField.setText(null);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}