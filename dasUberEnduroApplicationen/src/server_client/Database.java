package server_client;

import java.util.LinkedList;
import java.util.List;

public class Database {

    private List<String> totalData = new LinkedList<>();

    public void addData(String data) {

        synchronized (this) {

            totalData.add(data);
            System.out.println("DB: " + data);
        }
    }
}