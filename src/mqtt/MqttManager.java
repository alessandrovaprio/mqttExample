package mqtt;

import java.nio.charset.StandardCharsets;
import org.eclipse.paho.client.mqttv3.*;

/**
 * Classe che gestisce la comunicazione Mqtt.
 */
public class MqttManager implements MqttCallback {
   
    private MqttClient client;
    private boolean isConnected = false;



    /**
     * Restituisce l'istanza di MqttManager creata in base all'URI, all'id del client e al listerner MQTT passati
     * come parametri
     *
     * @param uri indirizzo del server
     * @param clientId id del client
      @param queueName e' il nome della coda.
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
     * @param queueName e' il nome della coda.
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
     * Pubblico su una coda il messaggio che viene passato.
     * 
     *
     * @param queueName e' il nome della coda
     * @param message e' il messaggio
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
