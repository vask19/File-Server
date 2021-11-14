package server;

import data.Data;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static MapHandler mapHandler;
    private static ServerSocket serverSocket;

    public static void exit() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void writeMapHandler() {
        mapHandler = new MapHandler();
    }



    public void startServer() {
        writeMapHandler();

        try { serverSocket =
                     new ServerSocket(Data.SERVER_PORT, 50, InetAddress.getByName(Data.SERVER_PATH));
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    Session session = new Session(socket, mapHandler);
                    new Thread(session).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}








