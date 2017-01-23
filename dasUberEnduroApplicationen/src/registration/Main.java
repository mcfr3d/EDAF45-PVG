package registration;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;

import util.RegistrationWriter;

public class Main {
	public static void main(String [] args){
		
		System.out.println("Ange registreringsfil");
		Scanner sc = new Scanner(System.in);
		String path = sc.next();
		new Gui(path);
		
	}

}
