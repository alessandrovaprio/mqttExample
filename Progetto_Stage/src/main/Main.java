package main;

import java.util.ArrayList;
import java.util.List;

import login.Login;
import login.LoginJFrame;
import login.LoginListener;
import mqtt.Publisher;

public class Main {
	
	
	public static void main(String[] args) {
		Publisher p = new Publisher();
		// p.start();
		// new Login(p);
		new LoginJFrame(p);
		// p.start();
		
		// p.visualize = new ChatEvent(p.client);
	}
	
}
