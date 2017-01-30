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

import util.IOReader;
import util.ResultWriter;

public class Gui extends JFrame {

	final JFileChooser fc = new JFileChooser("./mapp_med_shit");
	private Database db = new Database();
	
	private class NameFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent eee) {
						
			int returnVal = fc.showOpenDialog(Gui.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				File file = fc.getSelectedFile();
				
				try {
					IOReader.readNames(file.getPath(),db);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
	}
	private class StartFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent eee) {
						
			int returnVal = fc.showOpenDialog(Gui.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				File file = fc.getSelectedFile();
				
				try {
					IOReader.readStart(file.getPath(), db);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
	}
	private class FinishFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent eee) {
						
			int returnVal = fc.showOpenDialog(Gui.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				File file = fc.getSelectedFile();
				
				try {
					IOReader.readFinish(file.getPath(), db);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
		}
	}
	private class ExportButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent eee) {
					
			int userSelection = fc.showSaveDialog(Gui.this);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fc.getSelectedFile();
			    
			    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				ResultWriter.write(fileToSave.getPath(),db);
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
		
		buttons[2].addActionListener(new StartFileButtonListener());
		buttons[3].addActionListener(new FinishFileButtonListener());
		buttons[4].addActionListener(new ExportButtonListener());
		
		
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
