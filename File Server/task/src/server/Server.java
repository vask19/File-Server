package server;
import main.Data;

import java.io.*;
import java.net.*;
import java.util.Map;

public class Server {
    private Core core;
    private Socket socket;
    private static Server instance;
    private DataInputStream input;
    private DataOutputStream output;

    private Server() {
        core = new Core();
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
                try (
                        Socket socket = serverSocket.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                ) {


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<String ,String > getFile(String fileName){
        return core.getFile(fileName);

    }
    public String addFile(String fileName){
        return core.addFile(fileName);
    }
    public String deleteFile(String fileName){
        return core.deleteFile(fileName);
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


            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



