package registration;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import util.EvaluatedExpression;
import util.RegistrationExpression;
import util.RegistrationIO;
import util.TotalTimeCalculator;

public class Gui extends JFrame implements Subscriber {
	private JTextArea textOutput;
	private JTextField textEntry;
	private final String path;
	private final Font font = new Font("Arial", Font.PLAIN, 34);
	private JPanel faultyRegistrationPanel;
	private HashMap<ListItem, String> map = new HashMap<>();

	public Gui(String path) {
		super();
		this.setTitle("dasUberEnduroApplicationen");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.path = path;
		this.add(makeMainPanel());
		this.pack();
		this.setMinimumSize(new Dimension(900, 400));
		this.setVisible(true);

	}

	private JPanel makeMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(makeEntryPanel());
		JScrollPane scroller = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		scroller.setViewportView(makeOutputPanel());
		panel.add(scroller);
		return panel;
	}

	private JPanel makeOutputPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		String s = RegistrationIO.read(path);
		textOutput = new JTextArea(10, 10);
		textOutput.setFont(font);
		textOutput.setEditable(false);
		textEntry.setMaximumSize(new Dimension(8000, 50));
		textOutput.setText(s);
		panel.add(textOutput);
		faultyRegistrationPanel = new JPanel();
		faultyRegistrationPanel.setLayout(new BoxLayout(faultyRegistrationPanel, BoxLayout.Y_AXIS));
		panel.add(faultyRegistrationPanel);

		return panel;
	}

	private JPanel makeEntryPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		ActionListener listener = new RegistrationListener();
		// Components
		textEntry = new JTextField(10);
		textEntry.addActionListener(listener);
		textEntry.setFont(font);
		JButton button = new JButton("Registrera");
		button.addActionListener(listener);
		button.setFont(font);
		button.setBackground(new Color(83, 156, 52));
		panel.add(textEntry);
		panel.add(button);

		return panel;
	}

	private class RegistrationListener implements ActionListener {
		private String time;
		@Override
		public void actionPerformed(ActionEvent e) {
			time = TotalTimeCalculator.getCurrentTime();
			
			EvaluatedExpression evalTuple = RegistrationExpression.eval(textEntry.getText().trim());
			for(String correct : evalTuple.evaluatedNbrs) {
				String outputText = correct + "; " + time + "\n" + textOutput.getText();
				textOutput.setText(outputText);
				textOutput.setCaretPosition(0);		
				writeToFile();
			}
			for(String error : evalTuple.errorList) {
				addFaultyRegistration(time, error);
			}

			textEntry.setText("");
		}

		private void addFaultyRegistration(String time, String faultyStartNumber) {
			ListItem li = new ListItem(time, map, Gui.this, faultyStartNumber);
			map.put(li, time);
			faultyRegistrationPanel.add(li);
			repaintGui();		
		}
	}

	@Override
	public void update() {
		if (!checkIfRemoved()) {
			checkEdit();
		}
		repaint();
	}

	private void checkEdit() {
		for (ListItem item : map.keySet()) {
			if (map.get(item).contains(";")) {
				String[] temp = map.get(item).split(";");
				String outputText = temp[0].trim() + "; " + temp[1].trim() + "\n" + textOutput.getText();
				textOutput.setText(outputText);
				writeToFile();
				faultyRegistrationPanel.remove(item);
				map.remove(item);
				break;
			}
		}

	}

	private boolean checkIfRemoved() {
		for (Component c : faultyRegistrationPanel.getComponents()) {
			if (!map.containsKey(c)) {
				faultyRegistrationPanel.remove(c);
				return true;
			}
		}
		return false;
	}

	private void writeToFile() {
		try {
			RegistrationIO.rewrite(path, textOutput.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void repaintGui() {
		faultyRegistrationPanel.repaint();
		faultyRegistrationPanel.revalidate();
		// Imba code here
		textOutput.setText(textOutput.getText());
		textOutput.setCaretPosition(0);
	}
}