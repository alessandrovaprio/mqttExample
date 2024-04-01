package awt;

import org.eclipse.paho.client.mqttv3.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.charset.*;
import java.util.Scanner;
/**
 *PannelloCtrl: implementa un pannello di controllo che in base alle richieste provenienti dall'utente
 *pubblica su determinati topic i messaggi appropriati per attivare /disattivare un attuatore.
 */
public class PannelloCtrl implements MqttCallback {

	public enum Topic {
		  SPORT,
		  CULTURA,
		  MOTORI,
		  TECNOLOGIA,
		  AMORE
		}
    // the MQTT topics of this example
//    private static final String TOPIC_SWITCH = "home/switch";
//    private static final String TOPIC_SW_STATE = "home/sw-state";
//    private static final String TOPIC_LWT = "home/LWT";
    // init the client
    private MqttClient client;
    private AEvent2 visualize;
    /**
     * Constructor. It generates a client id and instantiate the MQTT client.
     */
    public PannelloCtrl() {
        // the broker URL
        String brokerURL = "tcp://127.0.0.1:1883";
//        String brokerURL = "tcp://localhost:1883";
        //String brokerURL = "tcp://193.206.52.98:1883";



        // A randomly generated client identifier
        String clientId = MqttClient.generateClientId();
        // crea  il client MQTT
        try {
             client = new MqttClient(brokerURL, clientId);

        } catch (MqttException e) {
            e.printStackTrace();
        }
        visualize = new AEvent2(client);
    }
    /** Implementation of MQTTCallBack Interface methods
     *
     */
    @Override
    public void connectionLost(Throwable cause) {
        // what happens when the connection is lost. We could reconnect here, for example.
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        // what happens when a new message arrive: in this case, we print it out.
        String strmsg = new String(message.getPayload(), StandardCharsets.UTF_8);
        System.out.println("Message arrived for the topic '" + topic + "': " + strmsg);
        
        // additional action for the Last Will and Testament message
//        if ("home/LWT".equals(topic)) {
//            System.err.println(strmsg);
//        }
//        else // changed state topic arrived - make the button change color
//        {
        	ActionEvent ae = new ActionEvent(visualize.b, ActionEvent.ACTION_PERFORMED, "");
            visualize.messagesReceived.append(strmsg);
            visualize.messagesReceived.append(System.lineSeparator());
//            if ("ON".equals(strmsg)) {visualize.b.setBackground(Color.green);}
//            else  {visualize.b.setBackground(Color.red);}
//            System.out.println("received message "+strmsg+" on topic "+topic);
//        }
        
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // called when delivery for a message has been completed, and all acknowledgments have been received
        // no-op, here
    }

    /**
     * The method to start the publisher. Currently, it sets a Last Will and Testament
     * message, open a non persistent connection, and publish a temperature value
     */
    public void start() {
        Scanner sc = new Scanner(System.in);

        try { char pwd[] = {'p','i','s','s','i','r','2','0','2', '4'};

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("pissir");
            options.setPassword(pwd);
            // persistent, durable connection
            options.setCleanSession(false);
//            options.setWill(client.getTopic("home/LWT"), "PANNELLO: I'm gone. Bye.".getBytes(), 0, false);

            // connect the publisher to the broker
            client.setCallback(this);
            client.connect(options);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}