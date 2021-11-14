package client;

import data.Data;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket(Data.SERVER_PATH, Data.SERVER_PORT);
            Client client = new Client(socket);
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
