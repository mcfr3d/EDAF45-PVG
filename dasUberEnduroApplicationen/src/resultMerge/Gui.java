package resultMerge;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Gui extends JFrame {

	final JFileChooser fc = new JFileChooser();
	
	private class NameFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Handle open button action.
						
			int returnVal = fc.showOpenDialog(Gui.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would open the file.
				//log.append("Opening: " + file.getName() + ".");
			} else {
				//log.append("Open command cancelled by user.");
			}
			
		}
	}

	private JPanel makeButtonPanel() {

		JPanel panel = new JPanel();

		GridLayout layout = new GridLayout(5, 1);

		layout.setHgap(8);
		layout.setVgap(8);

		panel.setLayout(layout);

		JButton buttons[] = new JButton[5];

		for (int i = 0; i < 5; ++i) {

			buttons[i] = new JButton("Button " + (i + 1));

			panel.add(buttons[i]);
		}

		buttons[0].setText("Läs namnfil");
		buttons[1].setText("Mass start");
		buttons[2].setText("Läs startfil");
		buttons[3].setText("Läs målgångsfil");
		buttons[4].setText("Exportera resultat");

		buttons[0].addActionListener(new NameFileButtonListener());
		
		panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(8.0f)));

		return panel;
	}

	public Gui() {

		super();
		this.setTitle("Sortering");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(makeButtonPanel());
		this.pack();
		this.setVisible(true);

	}

	public static void main(String[] args) {

		new Gui();
	}

}
