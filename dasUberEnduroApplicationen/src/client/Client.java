package client;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws InterruptedException {

		Socket socket = null;
		String serverHostname = new String("127.0.0.1");
		int port = 42069;
		OutputStream outPutStream = null;
		DataOutputStream out = null;
		System.out.println("Attemping to connect to host " + serverHostname + " on port " + port + ".");
		try {
			System.out.println("Trying to connect to server...");
			socket = new Socket(serverHostname, port);	//Här görs själva uppkopplingmot servern
			System.out.println("Managed to connect! Yay!");
			outPutStream = socket.getOutputStream();
			out = new DataOutputStream(outPutStream);
			InputStream inFromServer = socket.getInputStream();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + serverHostname);
			System.exit(1);
		} catch (IOException er) {
			System.err.println("Couldn't get I/O for " + "the connection to: " + serverHostname);
			System.exit(1);
		}

		while (socket.isConnected() && !socket.isClosed()) {
			Thread.sleep(5000); // Sover i 5 sekunder innan vi skickar nästa
								// meddelande
			try {
				System.out.println("Send data to server...");
				out.writeUTF("Wazzup bro?");

			} catch (IOException e) {
				System.out.println("Could not send message to server");
				e.printStackTrace();
			}
		}
	}
}