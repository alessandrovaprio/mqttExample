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

	private List<LoginListener> listeners = new ArrayList<>();
    // Method to add listeners
    public void addLoginListener(LoginListener listener) {
        listeners.add(listener);
    }

	public void notifyLoginSuccess(String username) {
        for (LoginListener listener : listeners) {
            System.out.println("OK");
        }
		new ChatEvent(publisher.client, username);
    }
	public void notifyLoginError(String message) {
        for (LoginListener listener : listeners) {
            System.out.println("NO");
        }
		new ErrorDialog(message);
    }

	
}