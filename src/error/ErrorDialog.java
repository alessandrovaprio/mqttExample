package error;

import java.awt.Button;
import java.awt.Font;
import java.awt.Label;
import javax.swing.JFrame;


public class ErrorDialog extends JFrame {
	
    public Button okButton;
	
	// HashMap<String,String> logininfo = new HashMap<String,String>();
	
	public ErrorDialog(String title) {
        JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(420,420);
        Label messageLabel = new Label(title);
		messageLabel.setBounds(50,100,400,200);
        messageLabel.setFont(new Font(null,Font.ITALIC,13));
		add(messageLabel);
		setLayout(null);
		setVisible(true); //rende visibile il Frame grafico
	}
}