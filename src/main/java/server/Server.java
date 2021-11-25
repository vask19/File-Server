package server;

import data.Data;
import files.SerializationUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements AutoCloseable{
    private ServerSocket serverSocket;
    private MapHandler mapHandler;

    public void startServer() {

        try { serverSocket =
                     new ServerSocket(Data.SERVER_PORT, 50, InetAddress.getByName(Data.SERVER_PATH));
               mapHandler = new MapHandler();
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                try {
                    Session session = new Session(socket, mapHandler);
                    new Thread(session).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

            e.printStackTrace();


        }
    }

    @Override
    public void close() throws Exception {
        if (mapHandler != null)
            mapHandler.close();
        if (serverSocket != null)
            serverSocket.close();

    }
}











