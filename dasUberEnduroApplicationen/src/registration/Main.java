package registration;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {

		final JFileChooser fc = new JFileChooser("dasUberEnduroApplicationen/mapp_med_shit");

		int userSelection = fc.showSaveDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {

			File fileToSave = fc.getSelectedFile();

			new Gui(fileToSave.getAbsolutePath());
			
		} else {
			
			JOptionPane.showMessageDialog(null, "Du måste välja en fil!", "Felmeddelande",
					JOptionPane.ERROR_MESSAGE);
		}

	}

}
