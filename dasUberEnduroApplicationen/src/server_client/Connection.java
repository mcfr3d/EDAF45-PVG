package server_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection extends Thread {

    private Socket clientSocket;
    private Database db;

    public Connection(Socket clientSoc, Database db) {
        this.db = db;
        clientSocket = clientSoc;
        start();
    }

    public void handlePacket(String data) {
        db.addData(data);
    }

    @Override
    public void run() {
        String s = "Client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort();
        System.out.println(s + " connected");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                handlePacket(inputLine);
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
        System.out.println(s + " disconnected");
    }
}