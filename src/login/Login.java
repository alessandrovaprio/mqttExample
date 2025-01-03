package login;

import java.util.ArrayList;
import java.util.List;
import error.ErrorDialog;
import mqtt.ChatEvent;
import mqtt.Publisher;

// classe usata per gestire gli eventi con il pattern observer/notify
public class Login {
	
	
	public Publisher publisher;
	public Login(Publisher publisher) {
		this.publisher= publisher;
	}

	
}