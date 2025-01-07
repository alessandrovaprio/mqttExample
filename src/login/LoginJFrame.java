package login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.MqttException;
import mqtt.Publisher;

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
//classe usata per generare la pagina di login, implementa metodi per ascoltare gli eventi su azioni e tasti
public class LoginJFrame extends JFrame {
	
	Frame frame = new Frame();
	public Button loginButton;
	public Button resetButton;
	public TextField usernameField;
	public JPasswordField passwordField;
	public Label usernameLabel;
	public Label passwordLabel;
	public Label messageLabel;
    //private Login login;
    //public TextArea errorArea = new TextArea();
    
	
	public Label error;
    
    public Publisher publisher;
    
    
    public LoginJFrame(Publisher pub) {
        //this.login = new Login(pub);
    	Login l = new Login(this);
    	this.publisher = new Publisher();
        usernameLabel = new Label("Username");
		usernameLabel.setBounds(50,100,75,25);
		add(usernameLabel);
		
		passwordLabel = new Label("Password");
        passwordLabel.setBounds(50,150,75,25);
        add(passwordLabel);
        
        messageLabel = new Label();
        messageLabel.setBounds(125,250,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,25));
        
        usernameField = new TextField();
        usernameField.setBounds(125,100,200,25);
        usernameField.setText(null); /*setText(null): serve per settare le proprietà text a null, cioè inzialmente è vuota*/
        add(usernameField);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(125,150,200,25);
        passwordField.setText(null); /*setText(null): serve per settare le proprietà text a null, cioè inzialmente è vuota*/
        add(passwordField);
        
        loginButton = new Button("Login");
        loginButton.setBounds(125,200,100,25);
        //loginButton.setFocusable(false);
        loginButton.setBackground(Color.ORANGE);
        loginButton.addActionListener(l);
        add(loginButton);
        
        resetButton = new Button("Reset");
        resetButton.setBounds(225,200,100,25);
        //resetButton.setFocusable(false);
        resetButton.setBackground(Color.CYAN);
        resetButton.addActionListener(l);
        add(resetButton);
        
        //configuro error Area
        /*errorArea.setText(null);
        errorArea.setBounds(10,250,350,100);
        errorArea.setFont(new Font(null,Font.ITALIC,15));
        errorArea.setForeground(Color.RED);        
        errorArea.setVisible(false); //non la rendo visibile finche' non ho errori da far vedere
        add(errorArea);*/
        
        
        error = new Label();
        error.setBounds(25,250,350,100);
        error.setFont(new Font(null,Font.ITALIC,12));
        error.setForeground(Color.RED); 
        add(error);
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420,420);
        setLayout(null);
        setVisible(true); //rende visibile il Frame grafico
    }

	/*/**
	 * Metodo invocato quando e' stata digitata una chiave
	 */
	/*@Override
	public void keyTyped(KeyEvent e) {
		//metodo invocato quando e' stata digitata una chiave
	}

	/*metodo invocato quando si attiva il bottone Login scrivendo il nome utente all'interno del form 'Username', la Password e infine viene 
	  prenuto.*/
	/*/**
	 * Metodo invocato quando si attiva il bottone Login scrivendo il nome utente all'interno del form username, 
	 * la Password e infine viene prenuto
	 */
	/*@Override
	public void keyPressed(KeyEvent e) {
		
	}

	///**
	 * Metodo invocato quando il bottone e' stato rilasciato
	 */
	/*@Override
	public void keyReleased(KeyEvent e) {
		//metodo invocato quando il bottone e' stato rilasciato
	}

	/*il metodo actionPerformed() viene richiamato automaticamente ogni volta che si digita il bottone sul componente registrato.
	  Viene invocato quando si verifica un'azione*/
	/*/**
	 * Il metodo actionPerformed() viene richiamato automaticamente ogni volta che si digita il bottone sul componente registrato.
	   Viene invocato quando si verifica un'azione.
	 */
	/*public void actionPerformed(ActionEvent e) {
        /*CONDIZIONE UNO: l'utente digita il bottone 'Login' dopo aver inserito i campi 'Username' e 'Password' 
          di cui a seguire si apre il frame della chat messaggistica*/
        //System.out.println(e.getSource());
        /*if(e.getSource() == loginButton) {
            try {
                // controllo se i campi sono stati compilati, se non lo sono faccio uscire un form con errore.
                if(usernameField==null || usernameField.getText().isEmpty() || passwordField==null || passwordField.getPassword().length==0) {
                	/*this.errorArea.setText("Nessun nome utente o password forniti");
                    this.errorArea.setVisible(true);*/
                	/*this.error.setText("Nessun nome utente o password forniti");
                    this.error.setVisible(true);
                    return;
                }
                //imposto utente e password per connettersi alla coda
                MqttConnectOptions opt = new MqttConnectOptions();
                opt.setUserName(usernameField.getText());
                opt.setPassword(passwordField.getPassword());

                //login.publisher.client.connect(opt);
                publisher.client.connect(opt);

                setVisible(false); //chiudo il frame corrente 
                
                //e' andato a buon fine e apriro' il form successivo
                //new ChatEvent(login.publisher.client, usernameField.getText());
                new ChatEvent(publisher.client, usernameField.getText());
            }
            catch(MqttException cEx) {
            	/*this.errorArea.setText("Connessione fallita: " + cEx.getMessage());
                this.errorArea.setVisible(true);*/
            	/*this.error.setText("Connessione fallita: " + cEx.getMessage());
                this.error.setVisible(true);
            }
            catch(Exception e1) {
            	System.out.println("Errore: " +e1.getMessage());
                /*this.errorArea.setText("Errore: " +e1.getMessage());
                this.errorArea.setVisible(true);*/
            	/*this.error.setText("Errore: " +e1.getMessage());
                this.error.setVisible(true);
                e1.printStackTrace();
            }
        }
        /*CONDIZIONE DUE: l'utente preme il bottone 'Reset' per reimpostare nuovamente 'Username' e 'Password'*/
        /*if(e.getSource() == resetButton) {
            try {
                passwordField.setText(null);
                usernameField.setText(null);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        }
    }*/
}