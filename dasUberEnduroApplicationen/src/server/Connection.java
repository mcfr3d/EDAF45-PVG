package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import resultMerge.Database;

public class Connection extends Thread{
	private Database db;
	private Socket s;
    private DataInputStream in;
	
    public Connection(Socket s, Database db){
		this.s = s;
		this.db = db;
		try {
			in = new DataInputStream(s.getInputStream());	//Input buffer containing the data we read from the client
		} catch (IOException e) {
			System.out.println("could not create IO stream for this connection object");
		}
	}
	
	public void run(){
		while(true){
			try {
				while(in.available() == 0/**Buffern är tom*/){	//Bör vara while connected
					Thread.sleep(1000);	//Sov i en sekund innan man kollar
				}
				String input = in.readUTF();
				System.out.println("We got this message from the client: " + input);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
}
