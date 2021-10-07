package server;
import main.Data;

import java.io.*;
import java.net.*;
public class Server {
    private Socket socket;
    private static Server instance;
    private DataInputStream input;
    private DataOutputStream output;

    private Server() {
        start();

    }




    public static Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();



        }
        return instance;
    }


    private static void start()  {

        try (ServerSocket serverSocket =
                     new ServerSocket(Data.SERVER_PORT, 50, InetAddress.getByName(Data.SERVER_PATH));) {
            while (true) {
                Session session = new Session(serverSocket.accept());
                session.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
class Session extends Thread {
    private final Socket socket;


    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    @Override
    public void run() {
        try (

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        ) {
            String msg = input.readUTF();
            outputStream.writeUTF("All files were sent!");

            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



