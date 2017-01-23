package registration;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui extends JFrame {

	private JTextArea textOutput;
	private JTextField textEntry;
	private final String path;

	public Gui(String path) {
		super();
		this.setTitle("dasUberEnduroApplicationen");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.path = path;
		this.add(makeMainPanel());
		this.pack();
		this.setVisible(true);

	}

	private JPanel makeMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		textOutput = new JTextArea(10, 30);
		textOutput.setFont(new Font("Helvetica", Font.PLAIN, 34));

		panel.add(makeEntryPanel());
		panel.add(textOutput);
		return panel;
	}

	private JPanel makeEntryPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		ActionListener listener = new RegistrationListener();
		// Components
		textEntry = new JTextField(10);
		textEntry.addActionListener(listener);
		textEntry.setFont(new Font("Helvetica", Font.PLAIN, 34));
		textEntry.setMaximumSize(new Dimension(600,50));
		JButton button = new JButton("Registrera");
		button.addActionListener(listener);
		button.setFont(new Font("Helvetica", Font.PLAIN, 34));

		panel.add(textEntry);
		panel.add(button);

		return panel;
	}

	private class RegistrationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { //TODO: write to file
			String time = getCurrentTime();
			String startNumber = textEntry.getText().trim();
			if (correctInput(startNumber)) {
				String outputText = startNumber + "; " + time + "\n" + textOutput.getText();
				textOutput.setText(outputText);
				
			} else {
				JOptionPane.showMessageDialog(null,
					    "Felaktig input. Använd endast siffror.",
					    "Felmeddelande",
					    JOptionPane.ERROR_MESSAGE);
			}
			textEntry.setText("");
		}

		private boolean correctInput(String input) {
			for (char c : input.toCharArray()) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
			return input.length() != 0;
		}

		private String getCurrentTime() {
			LocalTime currentTime = LocalTime.now();
			String hour = String.format("%02d", currentTime.getHour());
			String minute = String.format("%02d", currentTime.getMinute());
			String second = String.format("%02d", currentTime.getSecond());
			return hour + "." + minute + "." + second;
		}
	}
}
