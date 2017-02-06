package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import resultMerge.Database;
import resultMerge.Racer;

public class RegistrationIO {

	
	public static void rewrite(String path, String data) {		
		
		try{
			PrintWriter writer = new PrintWriter(path, "UTF-8");
			writer.println(data);		
			writer.close();
		} 
		catch(IOException e) {
			System.out.println("File does not exist");		
		}
	}
	
	public static String read(String path) {
		StringBuilder sb = new StringBuilder();
		try {
			Scanner sc = new Scanner(new File(path));
			while(sc.hasNext()) {
				sb.append(sc.nextLine()).append('\n');
			}
			
		}catch(IOException e) {
			System.out.println("File does not exist, creating");
		}
		return sb.toString();
	}
	
}
