package awt;
import org.eclipse.paho.client.mqttv3.*;

import awt.PannelloCtrl.Topic;

import java.awt.*;
import java.awt.event.*;
import java.nio.charset.StandardCharsets;

public class AEvent2 extends Frame {
    int state = 0;
    Button b;
    Label username;
    TextField tf;
    Label receivedLabel;
    TextArea messagesReceived;
    TextArea textToSend;
    Choice topics;
    Button sendBtn;
    
    // setting the bounds of choice menu   

    MqttClient c;

    AEvent2(MqttClient cli) {
//create components
        c = cli;
        Outer o = new Outer(this);
        username = new Label("Username");
        username.setBounds(10, 50, 170, 20);
        add(username);
        tf = new TextField();
        tf.setBounds(10, 70, 170, 20);
        tf.setText(null);
        tf.addKeyListener(o);
       
//register listener
        
//add components and set size, layout and visibility
        
        add(tf);
        setSize(600, 600);
        setLayout(null);
        
        topics = new Choice();
        // setting the bounds of choice menu
        topics.setBounds(10, 100, 80, 30); 
        add(topics);
        for(Topic t : Topic.values()) {
    		System.out.println(t);
   	        // adding items to the choice menu  
    		topics.add(t.name()); 
    	}
        
        b = new Button("Connect");
        b.setEnabled(false);
        b.setBounds(150, 100, 80, 30);
        b.setBackground(Color.GREEN);
        
        b.addActionListener(o);//passing outer class instance
        add(b);
        //textarea
        receivedLabel = new Label("Messaggi ricevuti");
        receivedLabel.setBounds(300, 50, 150, 20);
        add(receivedLabel);
        messagesReceived = new TextArea (null, 10, 1, 1);
        messagesReceived.setBounds(300, 70, 300, 500); 
        messagesReceived.setEnabled(false); // set not editable
        
        
        textToSend = new TextArea ();
        textToSend.setBounds(10, 150, 200, 200);
        textToSend.addKeyListener(o);
        sendBtn = new Button("INVIA");
        sendBtn.setBounds(80, 360, 80, 30);
        sendBtn.setBackground(Color.BLUE);
        sendBtn.addActionListener(o);//passing outer class instance
        
        
        setVisible(true);
        
    }

}

    class Outer implements ActionListener, KeyListener{
//        private static final String TOPIC_SWITCH = "home/switch";
        AEvent2 obj;
        Outer(AEvent2 obj){
            this.obj=obj;
        }
        @Override
        public void keyTyped(KeyEvent e) {
        	
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
        	// TODO Auto-generated method stub
        	obj.b.setEnabled(!obj.tf.getText().isEmpty());
        	obj.sendBtn.setEnabled(!obj.textToSend.getText().isEmpty());
        	
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
        	// TODO Auto-generated method stub
        	
        }
        public void actionPerformed(ActionEvent e){
        	String selectedTopic = obj.topics.getSelectedItem();
        	
        	System.out.println(e.getSource());
        	if (e.getSource() == obj.b) {
        		try {
	    		    for(Topic t : Topic.values()) {
	    		    	obj.c.unsubscribe(t.name());
	    		    }

        			obj.c.subscribe(selectedTopic); //sottoscrivo al topic scelto
        			
        			obj.tf.setEnabled(false); //sisabilito per non cambiare piu' nome.
        			obj.tf.setBackground(Color.LIGHT_GRAY); //rendo il colore grigio per fare capire che e' disabilitato
        			obj.messagesReceived.setText(null); //pulisco i messaggi ricevuti
        			obj.messagesReceived.setBackground(Color.LIGHT_GRAY);
        			
        			obj.add(obj.receivedLabel);
        			obj.add(obj.messagesReceived);
        			obj.add(obj.textToSend);
        			obj.add(obj.sendBtn);
        		} catch (MqttException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
        		
        	}
        	
        	if (e.getSource() == obj.sendBtn) {
        		try {
        			String m = "[" + obj.tf.getText()+ "]" +obj.textToSend.getText();
        			MqttMessage msg = new MqttMessage(m.getBytes(StandardCharsets.UTF_8));
					obj.c.publish(selectedTopic, msg);
					obj.textToSend.setText(null); //pulisco dopo aver pubblicato sulla coda
				} catch (MqttException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}


        }
    }