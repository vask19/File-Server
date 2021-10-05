package server;
import main.Data;

import java.io.*;
import java.net.*;
public class Server {
    private Socket socket;
    private static Server instance;
    private DataInputStream input;
    private DataOutputStream output;
    private Server(){
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };



    public static Server getInstance(){
        if (instance == null)
            instance = new Server();
        return instance;
    }


    private void start() throws IOException {
        ServerSocket serverSocket =
                new ServerSocket(Data.SERVER_PORT,50,InetAddress.getByName(Data.SERVER_PATH));
        socket = serverSocket.accept();
        input = new DataInputStream(socket.getInputStream());
        output  = new DataOutputStream(socket.getOutputStream());


    }
    public String addStringToServer(String data) throws IOException {
        return input.readUTF();

    }


    public Socket getSocket() {
        return socket;
    }
}
