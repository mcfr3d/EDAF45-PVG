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
		boolean exit = false;
		do {
			int result = JOptionPane.showConfirmDialog(null, myPanel, "Konfigurera serverkoppling (Valfritt)",
					JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.OK_OPTION) {
				try {
					if (portField.getText().matches("\\d+")) {
						if (cc.tryToConnect(ipAddressField.getText(), Integer.parseInt(portField.getText()), 3000)) {
							JOptionPane.showMessageDialog(null, "Kopplad till servern :)");
						} else {
							throw new Exception("Connection Failed");
						}
						exit = true;
					} else {
						JOptionPane.showMessageDialog(null, "En port består endast av siffror", "Felmeddelande",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Kunde inte nå servern :(", e.getMessage(),
							JOptionPane.ERROR_MESSAGE);
					exit = true;
				}

			}
			else if(result == JOptionPane.OK_CANCEL_OPTION){
				exit=true;
			}
		} while (!exit);
		return cc;
	}
}
