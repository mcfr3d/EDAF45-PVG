package server_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Server {

    public static void main(String[] args) throws IOException {
        Database db = new Database();
        ConnectionFactory spawner = new ConnectionFactory(db);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while ((input = stdIn.readLine()) != null) {
            if(input.equals("exit")) {
                System.out.println("Closing server");
                spawner.stopWaiting();
                break;
            }
        }
    }
}