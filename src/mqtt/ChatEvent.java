package mqtt;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import mqtt.Publisher.Topic;

/**
 * Classe che estende la classe JFrame implementando la parte grafica della vista 
 * @author Daniele Ferrando
 * @version 1.0
 */
public class ChatEvent extends JFrame {
	
	public Button logoutButton;
	public Button button;
	public Button sendButton; /*Button: presenta un bottone*/
	public TextField textField; /*TextField: � un oggetto che consente la modifica di una singola riga di testo*/
	public Label username; /*Label: etichetta per posizionare il testo in un contenitore, visualizzando una singola riga di testo di sola lettura*/
	public Label usernameLabel; /*Label: etichetta per posizionare il testo in un contenitore, visualizzando una singola riga di testo di sola lettura*/
	public Label receivedLabel; /*Label: rappresenta l'etichetta*/
	public Label facolta;
	/*TextArea: � un'area multilinea in cui viene visualizzato il testo. 
	  Pu� essere impostato per consentire la modifica o per essere di sola lettura*/
	public TextArea messagesReceived;
	public TextArea textToSend;
	public Choice topics; /*Choice: presenta un menu a tendina*/
	public ArrayList<String> selectedTopics = new ArrayList<String>();
	/*MqttClient: contiene la parte logica del protocollo per connettersi e comunicare al broker mqtt 
	  cio� all'indirizzo 'tcp://127.0.0.1:1883'. 
	  127.0.0.1: e' l'indirizzo locale della macchina; mentre 1883 � la porta*/
	public MqttClient c;
	
	/**
	 * Costruttore che genera la parte logica del protocollo per connettersi e comunicare al broker mqtt
	 * @param cli
	 * @param user
	 */
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
		
		//inserisco username (l'ho inserito nella finestra del login)
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
		setSize(650,660);
		/*setLayout(null): il gestore del layout viene utilizzato per posizionare il componente 'Messaggi ricevuti' dell'interfaccia grafica 
		  sul contenitore. Se si chiamano setLayout(null) si possono posizionare i componenti in modo assoluto.*/
		setLayout(null);
		
		/*Viene creata l'etichetta 'Facolt�' specificando le posizioni e le coordinate e infine si passa la variabile aggiungendola che � stata 
		  creata come attributo di tipo oggetto Label*/
		facolta = new Label("Facolt�");
		facolta.setBounds(10,94,40,20); /*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		add(facolta);
		
		
		//ETICHETTA LABEL E TEXTAREA PER RICEVERE MESSAGGI INVIATI DALL'UTENTE
		receivedLabel = new Label("Messaggi ricevuti");
		receivedLabel.setBounds(300,50,150,20);
		add(receivedLabel);
		messagesReceived = new TextArea(null,10,1,1);
		messagesReceived.setBounds(300,70,300,500);
		//messagesReceived.setEnabled(true); //abilito per modificare i messaggi ricevuti in chat ('Messaggi ricevuti') su casella di testo.
		messagesReceived.setEnabled(false);//disabilito per non modificare i messaggi ricevuti in chat ('Messaggi ricevuti') su casella di testo.
		textToSend = new TextArea();
		textToSend.setBounds(10,150,220,200);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		textToSend.addKeyListener(s); /*viene eseguita una determinata azione per inviare i messaggi inviati dall'utente*/
		add(textToSend);
		//messagesReceived.addKeyListener(s);
		add(messagesReceived);
		
		//AGGIUNGERE questo se si vuole mandare messaggi solo una per TOPIC (servira' cambiare action subscriber)
		// topics = new Choice();
		// topics.setBounds(10,360,100,30);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		// add(topics);
		// for(Topic t : Topic.values()) {
		// 	//System.out.println(t);
		// 	topics.add(t.name());
		// }

		//PULSANTE INVIA
		sendButton = new Button("Invia");
		sendButton.setBounds(150,360,80,30);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		sendButton.setBackground(Color.YELLOW);
		sendButton.addActionListener(s);/*quando viene eseguita una determinata azione per inviare i messaggi tramite il pulsante 'INVIA'*/
		add(sendButton);	
		
		
		logoutButton = new Button("Logout");
		logoutButton.setBounds(280,580,80,30);/*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
		logoutButton.setBackground(Color.CYAN);
		logoutButton.addActionListener(s);/*quando viene eseguita una determinata azione per inviare i messaggi tramite il pulsante 'INVIA'*/
		add(logoutButton);
		
		
		/*configura il comportamento della finestra quando l'utente richiede di chiudere la finestra nel caso di utilizzo di JFrame.
		  EXIT_ON_CLOSE come parametro, indica l'applicazione da uscire quando l'utente chiude la finestra*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); //rende visibile il Frame grafico
		MultiSelectComboBox();
	}

	// Metodo per far comparire la select multipla (utilizza un Jpopup menu)
	public void MultiSelectComboBox() {
       
        ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
        JPopupMenu popup = new JPopupMenu();

        // Crea checkboxes dentro al popup (una per ogni TOPIC)
        for (Topic option : Topic.values()) {
            JCheckBox checkBox = new JCheckBox(option.name());
            checkBoxes.add(checkBox);
            popup.add(checkBox);
			// aggiungo azioni al click della singola checkbox
			checkBox.addActionListener(evt -> {
				// Se la seleziono mi connetto altrimenti mi disconnetto
				if (checkBox.isSelected()) {
					System.out.println(checkBox.getText() + " selezionato");
					try {
						c.subscribe(checkBox.getText());
						// aggiungo in una variabile di topic selezionati
						selectedTopics.add(checkBox.getText());
					} catch (MqttException e) {
						e.printStackTrace();
					}
				} else {
					try {
						c.unsubscribe(checkBox.getText());
						// rimuovo da variabile di topic selezionati la coda deselezionata
						selectedTopics.remove(checkBox.getText());
					} catch (MqttException e) {
						e.printStackTrace();
					}
				}
			});
        }
        // Fake combo box (just for display)
		JComboBox comboBox = new JComboBox<>(new String[]{"Seleziona"});
        
        // apro il popup quando click sulla combobox
        comboBox.addActionListener(e -> {
            popup.show(comboBox, 0, comboBox.getHeight());
        });

       
        add(comboBox);
        comboBox.setBounds(10,115,100,30); /*setBounds(x,y,width,height): specifica la posizione e le dimensioni di un componente GUI, coordinate x,y*/
    }
}
