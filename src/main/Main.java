package main;

import java.util.ArrayList;
import java.util.List;

import login.Login;
import login.LoginJFrame;
import mqtt.Publisher;

public class Main {
	
	
	public static void main(String[] args) {
		Publisher p = new Publisher();
		new LoginJFrame(p);
	}
	
}
