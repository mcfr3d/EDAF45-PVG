package registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {
	
    private PrintWriter out;
    private Socket socket;
    private boolean connected;
	
    public ClientConnection() {
    	connected = false;
    }
	
	public boolean tryToConnect(String ipAddress, int port, int timeout) {
		//TODO use of timeout
		if(!connected) {
			System.out.println("Attemping to connect to host " + ipAddress + " on port " + port + ".");
	        try {
	            socket = new Socket(ipAddress, port);
	            out = new PrintWriter(socket.getOutputStream(), true);
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host: " + ipAddress);
	            return false;
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for " + "the connection to: " + ipAddress);
	            return false;
	        }
	        connected = true;
		}
		return true;		
	}

	public boolean disconnect() {
		if(connected) {
			try{
				out.close();
				socket.close();
				connected = false;
				return true;
			} catch(IOException e) {
				System.err.println("Failed to disconnect.");
				return false;
			}			
		}	
		return false;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
	public void sendData(String data) {
		out.println(data);
	}

}
