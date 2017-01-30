package resultMerge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import util.ResultWriter;

public class MassStartRaceGui extends RaceGui {

	private class MassStartButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent eee) {

			String s = JOptionPane.showInputDialog(null, "Tiden för massstart", "Skriv in masstart tid",
					JOptionPane.QUESTION_MESSAGE);
			
			boolean b = db.setMassStart(s);
			
			if(!b) {
				
				JOptionPane.showMessageDialog(null, "Felaktig input.", "Felmeddelande",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	
	protected void addMassStartButton() {
		
		JButton button = new JButton("Masstart");
		
		button.addActionListener(new MassStartButtonListener());
		
		this.add(button);
	}	
	
	MassStartRaceGui(JFrame frame) {
		super(frame,"Mass start race");
				

		this.addNameFileButton();
		
		addMassStartButton();
		
		this.addFinishFileButton();
		this.addExportButton();
	}

}
