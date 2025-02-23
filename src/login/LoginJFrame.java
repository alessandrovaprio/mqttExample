package login;

import java.awt.*;
import javax.swing.*;
import mqtt.Publisher;
//import mqtt.Subscriber;


/**
 * Classe che estende la classe JFrame implementando la parte grafica della vista 
 * @author Daniele Ferrando
 * @version 1.0
 */
public class LoginJFrame extends JFrame {
	
	public Button loginButton;
	public Button resetButton;
	public TextField usernameField;
	public JPasswordField passwordField;
	public Label usernameLabel;
	public Label passwordLabel;
	public Label messageLabel;
	public Label error;
    public Publisher publisher;
    
    /**
     * Costruttore che genera la parte logica del protocollo per connettersi e comunicare al broker mqtt
     * @param pub
     */
    public LoginJFrame(Publisher pub) {
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
        usernameField.setText(null);
        add(usernameField);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(125,150,200,25);
        passwordField.setText(null); /*setText(null): serve per settare le proprietà text a null, cioè inzialmente è vuota*/
        add(passwordField);
        
        loginButton = new Button("Login");
        loginButton.setBounds(125,200,100,25);
        loginButton.setBackground(Color.ORANGE);
        loginButton.addActionListener(l);
        add(loginButton);
        
        resetButton = new Button("Reset");
        resetButton.setBounds(225,200,100,25);
        resetButton.setBackground(Color.CYAN);
        resetButton.addActionListener(l);
        add(resetButton);
        
        
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
}
