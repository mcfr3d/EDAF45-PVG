package registration;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;

import util.RegistrationWriter;

public class Main {
	public static void main(String [] args){
		/*Race _80sh = new Race();
		JFrame Schaufenster = new JFrame();
			JButton knopf = new JButton("das registrern knopf");
				knopf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent denKnopfEvent) {
						_80sh.addierenRennfahrer();
						knopf.setText("ziffer af renners: " + _80sh.betrag());
					}
				});
			JButton visenKnopfen = new JButton("registreren zi rennen");
				visenKnopfen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent denKnopfEvent) {
						JOptionPane.showMessageDialog(null,  _80sh.toString());
					}
				});
			
		Schaufenster.add(knopf, BorderLayout.WEST);
		Schaufenster.add(visenKnopfen, BorderLayout.EAST);
		Schaufenster.pack();
		Schaufenster.setSize(500, 500);
		Schaufenster.setVisible(true);*/
		System.out.println("Ange registreringsfil");
		Scanner sc = new Scanner(System.in);
		String path = sc.next();
		RegistrationWriter.write(path, "reg");
		new Gui(path);
		
	}

}
