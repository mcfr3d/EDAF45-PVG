package resultMerge;

import java.awt.BasicStroke;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.IOReader;
import util.ResultWriter;

public class RaceGui extends JPanel{

	
	private class NameFileButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent eee) {
						
			int returnVal = fc.showOpenDialog(frame);

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
						
			int returnVal = fc.showOpenDialog(frame);
			
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
						
			int returnVal = fc.showOpenDialog(frame);
			
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
					
			int userSelection = fc.showSaveDialog(frame);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fc.getSelectedFile();
			    
			    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				ResultWriter.write(fileToSave.getPath(),db);
			}
						
			
		}
	}
	

	final JFileChooser fc = new JFileChooser("/h/d5/n/dat15jca/workspace/pvgvt17-team06-production/dasUberEnduroApplicationen/mapp_med_shit");
	protected Database db = new Database();
	private JFrame frame;
	
	protected void addNameFileButton() {
		
		JButton button = new JButton("Läs namnfil");
		

		button.addActionListener(new NameFileButtonListener());
		
		this.add(button);
	}	
	protected void addStartFileButton() {

		JButton button = new JButton("Läs startfil");

		button.addActionListener(new StartFileButtonListener());
		
		this.add(button);
	}
	protected void addFinishFileButton() {

		JButton button = new JButton("Läs målfil");

		button.addActionListener(new FinishFileButtonListener());
		
		this.add(button);
	}
	protected void addExportButton() {

		JButton button = new JButton("Exportera resultat");

		button.addActionListener(new ExportButtonListener());
		
		this.add(button);
	}
	
	RaceGui(JFrame frame,String type) {
						
		this.frame = frame;
		
		GridLayout layout = new GridLayout(5, 1);

		layout.setHgap(8);
		layout.setVgap(8);

		this.setLayout(layout);

		this.add(new JLabel(type));
		
	}
	
	
}
