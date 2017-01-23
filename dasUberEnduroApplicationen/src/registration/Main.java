package registration;

import java.util.Scanner;

public class Main {
	public static void main(String [] args){
		
		System.out.println("Ange registreringsfil");		
		Scanner sc = new Scanner(System.in);
		String path = sc.next();
		sc.close();
		new Gui(path);
		
	}

}
