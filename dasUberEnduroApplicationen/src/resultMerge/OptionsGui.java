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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import util.IOReader;
import util.ResultWriter;


public class OptionsGui extends JFrame{

	private JPanel modeSelectionPanel;

		
	private class RaceListener implements ActionListener {
		
		private int type;
		
		RaceListener(int i) {
			
			type = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
							
			remove(modeSelectionPanel);
			
			switch(type) {
			case 0: add(new NormalRaceGui(OptionsGui.this)); break;
			case 1: add(new MaratonRaceGui(OptionsGui.this)); break;
			case 2: add(new MassStartRaceGui(OptionsGui.this)); break;
			}
			
			// Ful lösning eftesom att repaint inte funkar
			OptionsGui.this.setSize(300,300);
			OptionsGui.this.setSize(400,300);
			//OptionsGui.this.repaint();
		}
	}
	
	
	private JPanel makeModeSelectionPanel() {

		JPanel panel = new JPanel();

		GridLayout layout = new GridLayout(4, 1);

		
		layout.setHgap(8);
		layout.setVgap(8);

		panel.setLayout(layout);

		panel.add(new JLabel("Välj typ av race"));
		
		JButton buttons[] = new JButton[3];

		for (int i = 0; i < 3; ++i) {

			buttons[i] = new JButton("Button " + (i + 1));

			panel.add(buttons[i]);
		}

		buttons[0].setText("Normalt race");
		buttons[1].setText("Maraton race");
		buttons[2].setText("Mass start");

		buttons[0].addActionListener(new RaceListener(0));
		buttons[1].addActionListener(new RaceListener(1));
		buttons[2].addActionListener(new RaceListener(2));

		panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(8.0f)));

		return panel;
	}


	public OptionsGui() {

		super();
		this.setTitle("Sortering");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		modeSelectionPanel = makeModeSelectionPanel();
		
		this.add(modeSelectionPanel);
		this.setSize(400,300);
		//this.pack();
		this.setVisible(true);

	}

	public static void main(String[] args) {

		new OptionsGui();
	}
	
}
