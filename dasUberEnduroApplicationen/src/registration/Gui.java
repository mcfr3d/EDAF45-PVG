package registration;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import util.RegistrationWriter;

public class Gui extends JFrame implements Subscriber{
	private Random r = new Random();
	
	
	private JTextArea textOutput;
	private JTextField textEntry;
	private final String path;
	private final String font = "Arial";
	private JPanel faultyRegistrationPanel;
	private HashMap<ListItem, String> map = new HashMap<>();
	
	

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
		panel.add(makeEntryPanel());
		JScrollPane scroller = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
				   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroller.setViewportView(makeOutputPanel());
		panel.add(scroller);
		return panel;
	}
	
	private JPanel makeOutputPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		String s = RegistrationWriter.read(path);
		textOutput = new JTextArea(10, 10);
		textOutput.setFont(new Font(font, Font.PLAIN, 34));
		textOutput.setEditable(false);
		textOutput.setText(s);
		panel.add(textOutput);
		faultyRegistrationPanel = new JPanel();
		faultyRegistrationPanel.setLayout(new BoxLayout(faultyRegistrationPanel, BoxLayout.Y_AXIS));
		faultyRegistrationPanel.setBackground(Color.BLUE);
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
		textEntry.setFont(new Font(font, Font.PLAIN, 34));
		textEntry.setMaximumSize(new Dimension(600, 50));
		textEntry.setBackground(Color.GREEN);
		JButton button = new JButton("Registrera");
		button.addActionListener(listener);
		button.setFont(new Font(font, Font.PLAIN, 34));
		button.setBackground(new Color(153, 102, 204));
		panel.add(textEntry);
		panel.add(button);

		return panel;
	}

	
	
	
	private class RegistrationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { // TODO: write to file
			String time = getCurrentTime();
			String startNumber = textEntry.getText().trim();
			if (correctInput(startNumber)) {
				writeToFile(time, startNumber);

			} else if (startNumber.length() == 0) { 
				ListItem li = new ListItem(time, map, Gui.this);
				map.put(li, time);
				faultyRegistrationPanel.add(li);
				faultyRegistrationPanel.revalidate();
			} else {
				JOptionPane.showMessageDialog(null, "Felaktig input. Använd endast siffror.", "Felmeddelande",
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




	@Override
	public void update() {
		if(!checkIfRemoved()) {
			checkEdit();
		}
		faultyRegistrationPanel.repaint();
		faultyRegistrationPanel.revalidate();
	}

	private void checkEdit() {
		for(ListItem item: map.keySet()){
			if(map.get(item).contains(";")){
				String[] temp = map.get(item).split(";");
				writeToFile(temp[1].trim(),temp[0].trim());
				faultyRegistrationPanel.remove(item);
				map.remove(item);
				break;
			}
		}
		
	}

	private boolean checkIfRemoved() {
		for(Component c : faultyRegistrationPanel.getComponents()) {
			if(!map.containsKey(c)) {
				faultyRegistrationPanel.remove(c);
				return true;
			}
		}
		return false;
	}
	

	private void writeToFile(String time, String startNumber) {
		System.out.println("startnumber is ok will update file");
		String outputText = startNumber + "; " + time + "\n" + textOutput.getText();
		textOutput.setText(outputText);
		textOutput.setCaretPosition(0);
		try {

			System.out.println("Writing to file now");

			Files.write(Paths.get(path), textOutput.getText().getBytes());

			System.out.println("Finished writing to file now");

		} catch (FileNotFoundException e1) {
			System.out.println("Could not find file");
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}