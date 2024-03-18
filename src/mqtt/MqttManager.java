package mqtt;

import java.nio.charset.StandardCharsets;

//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonPrimitive;
import org.eclipse.paho.client.mqttv3.*;

/**
 * Classe che gestisce la comunicazione Mqtt.
 */
public class MqttManager implements MqttCallback {
   

//    private static final Gson gson = new Gson();
    private MqttClient client;
    private boolean isConnected = false;

//    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    /**
     * Restituisce l'istanza di MqttManager creata in base all'URI, all'id del client e al listerner MQTT passati
     * come parametri
     *
     * @param uri indirizzo del server
     * @param clientId id del client
     * @param listener listener di MQTT
     * @return
     * @throws MqttException se la creazione fallisce
     */
    public static MqttManager create(String uri, String clientId, String queueName) throws MqttException {
        MqttManager instance = new MqttManager();
//        instance.setListener(listener);
        instance.connect(uri, clientId, queueName);
        return instance;
    }

    /**
     * Configura la connessione al client MQTT.
     *
     * @param uri indirizzo del server.
     * @param clientId id del client.
     * @throws MqttException se la configurazione fallisce.
     */
    public void connect(String uri, String clientId, String queueName) throws MqttException {
        if (isConnected())
            throw new IllegalStateException("già connesso");

        client = new MqttClient(uri, clientId);
        MqttConnectOptions co = new MqttConnectOptions();
        co.setCleanSession(false);
        co.setAutomaticReconnect(true);
        client.setCallback(this);
        client.connect(co);
        client.subscribe(queueName);
//        client.subscribe("iotevents/movement/+", 2);

        isConnected = true;
    }

    /**
     * Restituisce true se è connesso, altrimenti false.
     *
     * @return true se è connesso, altrimenti false.
     */
    public boolean isConnected() {
        return isConnected;
    }

     

    /**
     * Comunica il nuovo stato di riepimento dell'aula al dispositivo IoT con id passato come parametro.
     * L'azione non viene effettuata immediatamente, ma viene messa in una coda di elaborazione.
     *
     * @param id id del dispositivo
     * @param occupancy nuovo stato di riempimento dell'aula
     */
    public void postOnQueue(String queueName, String message) {
    	try {
    		MqttMessage msg = new MqttMessage(message.getBytes(StandardCharsets.UTF_8));
    		client.publish(queueName, msg);
    	}
    	catch (MqttException ex) {
    		
    	}
        
    }

  

    @Override
    public void connectionLost(Throwable cause) {
        // todo: viene lanciato anche con la riconnessione automatica?
    }

    /**
     * Viene chiamato in modo asincrono quando arriva un messaggio dal server.
     * Se è un messaggio del tipo <code>iotbind</code>, richiama il metodo <code>bind</code>.
     * Se è un messaggio del tipo <code>iotevents</code>, richiama il metodo <code>movement</code>.
     * Altrimenti non fa nulla.
     *
     * @param topic nome del topic del messaggio
     * @param message il messaggio.
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        System.out.println("Ricevuto messaggio: " + new String(message.getPayload(), StandardCharsets.UTF_8)+". Da "+ topic);
        
    }

//    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {}
}
