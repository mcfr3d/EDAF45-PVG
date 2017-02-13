package server;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//import resultMerge.Database;

/*
 * TODO: Hitta någon nice bufferklass eller sätt att läsa in datan frpn klienten
 * */

public class Server extends Thread {
	private int port;
	private String ip;
	private ServerSocket ss;
	// private Database db;
	private DataInputStream in;

	public static void main(String[] args) throws IOException {

		Server s = new Server("127.0.0.1", 42069/* , new Database() */); // TODO:
																			// En
		System.out.println("Starting Server thread...");
		s.start(); // server
		System.out.println("Server thread started..."); // ska ta en
		// redan
		// skapad
		// Database
		// som
		// parameter

	}

	public Server(String ip, int port/* , Database db */) throws IOException {
		this.port = port;
		ss = new ServerSocket(port);
		ss.setSoTimeout(10000);
		// this.db = db;
		this.ip = ip;
	}

	public void run() {
		int i = 0;
		Socket socket = null;
		try {
			socket = ss.accept();
		} catch (IOException e1) {
			System.out.println("Could not connect");
		}
		while (socket.isConnected() && !socket.isClosed()) {
			try {
				while (in.readUTF() == null) { /** Buffern är tom */
					System.out.println("Buffern är tom...");
					Thread.sleep(1000); // Sov i en sekund innan man kollar
				}

				// String input = in.readUTF();
				// System.out.println("We got this message from the client: " +
				// input);
			} catch (Exception e) {
				if (i == 0) {
					System.out.println("WOOOW!!!!");
					i++;
				}
			}
		}
	}
}
