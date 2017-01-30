package registration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Gui extends JFrame {
	private Random r = new Random();

	private JTextArea textOutput;
	private JTextField textEntry;
	private final String path;
	private final String font = "Arial";

	public Gui(String path) {
		super();
		this.setTitle("dasUberEnduroApplicationen");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.path = path;
		this.add(makeMainPanel());
		this.pack();
		this.setVisible(true);

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

				randomizeColor();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

				randomizeColor();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

				randomizeColor();
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

				randomizeColor();
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

				randomizeColor();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

				randomizeColor();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
				randomizeColor();
			}

		});
		
	}

	private JPanel makeMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		textOutput = new JTextArea(10, 30);
		textOutput.setFont(new Font(font, Font.PLAIN, 34));
		textOutput.setEditable(false);

		panel.add(makeEntryPanel());
		panel.add(textOutput);
		JScrollPane scroller = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setViewportView(textOutput);
		panel.add(scroller);
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

	private Color randomColor() {

		int a = r.nextInt(255);
		int b = r.nextInt(255);
		int c = r.nextInt(255);
		return new Color(a, b, c);
	}

	private Color negativeColor(Color c) {

		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
	}

	private void randomizeColor() {

		Color c = randomColor();

		textOutput.setBackground(c);
		textOutput.setForeground(negativeColor(c));
	}

	private class RegistrationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { // TODO: write to file
			String time = getCurrentTime();
			String startNumber = textEntry.getText().trim();
			if (correctInput(startNumber)) {
				System.out.println("startnumber is ok will update file");
				String outputText = startNumber + "; " + time + "\n" + textOutput.getText();
				textOutput.setText(outputText);
				textOutput.setCaretPosition(0);

				randomizeColor();

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
}
