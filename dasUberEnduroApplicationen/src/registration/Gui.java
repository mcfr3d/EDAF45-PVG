package registration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui extends JFrame {

	private JTextArea textOutput;
	private JTextField textEntry;

	public Gui() {
		super();
		this.setTitle("dasUberEnduroApplicationen");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.add(makeMainPanel());
		this.pack();
		this.setVisible(true);

	}

	private JPanel makeMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		textOutput = new JTextArea(10, 30);

		panel.add(makeEntryPanel());
		panel.add(textOutput);
		return panel;
	}

	private JPanel makeEntryPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		// Components
		textEntry = new JTextField(10);

		JButton button = new JButton("Registrera");
		button.addActionListener(new RegistrationListener());

		panel.add(textEntry);
		panel.add(button);

		return panel;
	}

	private class RegistrationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO: Implement button function
			System.out.println("Button pressed !");
		}
	}
}
