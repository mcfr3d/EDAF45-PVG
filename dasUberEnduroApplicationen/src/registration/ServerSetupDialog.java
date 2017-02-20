package registration;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerSetupDialog {
	
	public static ClientConnection initConnect() {
		ClientConnection cc = new ClientConnection();
		
		JTextField ipAddressField = new JTextField(15);
		JTextField portField = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("IP-adress:"));
		myPanel.add(ipAddressField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		myPanel.add(new JLabel("Port:"));
		myPanel.add(portField);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Konfigurera serverkoppling (Valfritt)",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.out.println("IP: " + ipAddressField.getText());
			System.out.println("Port: " + portField.getText());
			if(cc.tryToConnect(ipAddressField.getText(), Integer.parseInt(portField.getText()), 3000)) {
				JOptionPane.showMessageDialog(null,
					    "Successfully Connected to server :)");
			} else {
				JOptionPane.showMessageDialog(null,
					    "Failed to connect to server :(",
					    "Connection Failed",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
		return cc;
	}
}
