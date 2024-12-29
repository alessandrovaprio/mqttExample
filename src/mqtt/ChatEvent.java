package mqtt;

import java.awt.*;
import javax.swing.JFrame;
import org.eclipse.paho.client.mqttv3.MqttClient;
import mqtt.Publisher.Topic;

public class ChatEvent extends JFrame {
	
	public Button button;
	public Button sendButton; /*Button: presenta un bottone*/
	public Label username; /*TextField: � un oggetto che consente la modifica di una singola riga di testo*/
	public Label usernameLabel; /*Label: etichetta per posizionare il testo in un contenitore, visualizzando una singola riga di testo di sola lettura*/
	public Label receivedLabel; /*Label: rappresenta l'etichetta*/
	public Label facolta;
	/*TextArea: � un'area multilinea in cui viene visualizzato il testo. 
	  Pu� essere impostato per consentire la modifica o per essere di sola lettura*/
	public TextArea messagesReceived;
	public TextArea textToSend;
	public Choice topics; /*Choice: presenta un menu a tendina*/
	/*MqttClient: contiene la parte logica del protocollo per connettersi e comunicare al broker mqtt 
	  cio� all'indirizzo 'tcp://127.0.0.1:1883'. 
	  127.0.0.1: e' l'indirizzo locale della macchina; mentre 1883 � la porta*/
	public MqttClient c;
	

	public ChatEvent(MqttClient cli, String user) {
		c=cli;
		ActionSubscriber s = new ActionSubscriber(this);
		c.setCallback(new Subscriber(this));
		usernameLabel = new Label("Username");
		usernameLabel.setBounds(10,50,170,20);
		
		add(usernameLabel);
		username = new Label();
		username.setBounds(10,70,170,20);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		username.setText(null);/*setText(null): serve per settare le propriet� text a null, cio� inzialmente � vuota*/
		
		// inserisco username (l'ho inserito nella finestra del login)
		username.setText(user);
		username.setFont(new Font(null,Font.ITALIC,20));
		
		/*addKeyListener(s): attiva un listener sul tasto della tastiera. 
		 Ascolta gli eventi quando l'utente digita il tasto gestendo i 3 metodi: 
		 -public void keyPressed(KeyEvent e)
		 -public void keyReleased(KeyEvent e)
		 -public void actionPerformed(ActionEvent e)*/
		username.addKeyListener(s);
		add(username);
		/*setSize(WIDTH, HEIGHT): imposta la dimensione della larghezza e dell'altezza del Frame*/
		setSize(650,650);
		/*setLayout(null): il gestore del layout viene utilizzato per posizionare il componente 'Messaggi ricevuti' dell'interfaccia grafica 
		  sul contenitore. Se si chiamano setLayout(null) si possono posizionare i componenti in modo assoluto.*/
		setLayout(null);
		
		/*Viene creata l'etichetta 'Facolt�' specificando le posizioni e le coordinate e infine si passa la variabile aggiungendola che � stata 
		  creata come attributo di tipo oggetto Label*/
		facolta = new Label("Facoltà");
		facolta.setBounds(10,94,40,20); /*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		add(facolta);
		
		/*Viene creato il menu a tendina Choice specificando le posizioni e le coordinate e infine si passa la variabile aggiungendola che � stata 
		  creata come attributo di tipo oggetto Choice*/
		topics = new Choice();
		topics.setBounds(10,115,100,30);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		add(topics);
		for(Topic t : Topic.values()) {
			//System.out.println(t);
			topics.add(t.name());
		}
		
		//PULSANTE CONNECT
		button = new Button("Connect");
		button.setBounds(150,115,80,30);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		button.setBackground(Color.GREEN);
		button.addActionListener(s);/*quando viene eseguita una determinata azione per attivare/disattivare il pulsante 'Connect'*/
		add(button);
		
		//ETICHETTA LABEL E TEXTAREA PER RICEVERE MESSAGGI INVIATI DALL'UTENTE
		receivedLabel = new Label("Messaggi ricevuti");
		receivedLabel.setBounds(300,50,150,20);
		add(receivedLabel);
		messagesReceived = new TextArea(null,10,1,1);
		messagesReceived.setBounds(300,70,300,500);
		//messagesReceived.setEnabled(true); //abilito per modificare i messaggi ricevuti in chat ('Messaggi ricevuti') su casella di testo.
		messagesReceived.setEnabled(false);//disabilito per non modificare i messaggi ricevuti in chat ('Messaggi ricevuti') su casella di testo.
		textToSend = new TextArea();
		textToSend.setBounds(10,150,200,200);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		textToSend.addKeyListener(s); /*viene eseguita una determinata azione per ricevere i messaggi inviati dall'utente*/
		
		//PULSANTE INVIA
		sendButton = new Button("Invia");
		sendButton.setBounds(80,360,80,30);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		sendButton.setBackground(Color.YELLOW);
		sendButton.addActionListener(s);/*quando viene eseguita una determinata azione per inviare i messaggi tramite il pulsante 'INVIA'*/
		
		/*configura il comportamento della finestra quando l'utente richiede di chiudere la finestra nel caso di utilizzo di JFrame.
		  EXIT_ON_CLOSE come parametro, indica l'applicazione da uscire quando l'utente chiude la finestra*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); //rende visibile il Frame grafico
	}
}
