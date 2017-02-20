package server_client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class ConnectionFactory extends Thread {

    private Database db;
    private boolean running = true;

    public ConnectionFactory(Database db) {
        this.db = db;
        start();
    }

    public void stopWaiting() {
        running = false;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10008);
            try {
                while (running) {
                    serverSocket.setSoTimeout(100);
                    try {
                        new Connection(serverSocket.accept(), db);
                    } catch (SocketTimeoutException ste) {
                    }
                }
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not close port: 10008.");
                System.exit(1);
            }
        }
    }
}