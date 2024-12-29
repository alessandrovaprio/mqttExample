package login;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPasswordField;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
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
// classe usata per generare la pagina di login, implementa metodi per ascoltare gli eventi su azioni e tasti
public class LoginJFrame extends JFrame implements ActionListener, KeyListener {
    
    
    Frame frame = new Frame();
	public Button loginButton;
	public Button resetButton;
	public TextField usernameField;
	public JPasswordField passwordField;
	public Label usernameLabel;
	public Label passwordLabel;
	public Label messageLabel;
    
    private Login login;
    
    public LoginJFrame(Publisher pub) {
        this.login= new Login(pub);
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
		usernameField.setText(null);
		add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(125,150,200,25);
		passwordField.setText(null);
		add(passwordField);
		
		loginButton = new Button("Login");
		loginButton.setBounds(125,200,100,25);
		//loginButton.setFocusable(false);
		loginButton.setBackground(Color.ORANGE);
		loginButton.addActionListener(this);
		add(loginButton);
		
		resetButton = new Button("Reset");
		resetButton.setBounds(225,200,100,25);
		//resetButton.setFocusable(false);
		resetButton.setBackground(Color.CYAN);
		resetButton.addActionListener(this);
		add(resetButton);
		
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420,420);
		setLayout(null);
		setVisible(true); //rende visibile il Frame grafico

        addLoginListener((isSuccess, username) -> {
            if (isSuccess) {
                System.out.println("Welcome, " + username + "! Login was successful.");
                // notifica alla classe login che il login e' andato bene
                login.notifyLoginSuccess(username);
            } else {
                System.out.println("Login failed for username: " + username);
                // notifica alla classe login che il login e' andato male
                login.notifyLoginError(username);
            }
            return isSuccess;
        });
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
        /*CONDIZIONE UNO: l'utente preme il bottone 'Connect' di cui si aprono le due textarea, quindi riguarda il 1� controllo dell'if()*/
        //System.out.println(e.getSource());
        if(e.getSource() == loginButton) {
            try {
                // controllo se i campi sono stati compilati, se non lo sono faccio uscire un form con errore.
                if( usernameField == null || usernameField.getText().isEmpty()
                    || passwordField == null || passwordField.toString().isEmpty()) {
                    login.notifyLoginError("NO user/psw provided");
                    return;
                } 
                // imposto utente e password per connettersi alla coda
                MqttConnectOptions opt = new MqttConnectOptions();
                opt.setUserName(usernameField.getText());
                opt.setPassword(passwordField.getPassword());

                login.publisher.client.connect(opt);

                // chiudo il frame corrente 
                setVisible(false);
                // notifico che e' andato a buon fine e apriro' il form successivo
                login.notifyLoginSuccess(usernameField.getText());
            }
            catch(MqttException cEx) {
                // new ErrorDialog("Connection Failed: " + cEx.getMessage());
                login.notifyLoginError("Connection Failed: " + cEx.getMessage());
            }
            catch(Exception e1) {
                System.out.println("Error: " + e1.getMessage());
                
                e1.printStackTrace();
            }
        }
        if(e.getSource() == resetButton) {
            try {
                passwordField.setText(null);
                usernameField.setText(null);
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void addLoginListener(LoginListener listener) {
        login.addLoginListener(listener);
    }    
}
