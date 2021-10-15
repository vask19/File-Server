package server;
import main.Data;

import java.io.*;
import java.net.*;
public class Server {
    private Core core;
    private static Server instance;
    private BufferedWriter writer;
    private BufferedReader reader;

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

    private void run(Socket socket) throws IOException {
        createStreams(socket);
        String response = reader.readLine();
        String httpMethod = response.split(" ")[0];
        String fileName = response.split(" ")[1];

         switch (httpMethod){
            case "PUT" -> addFile(fileName);
            case "DELETE" -> deleteFile(fileName);
            case "GET" -> getFile(fileName);
        }

    }


    private void accept(ServerSocket serverSocket){
        while (true) {
            try (Socket socket = serverSocket.accept();)
            {
                run(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    private void createStreams(Socket socket){
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    )
            );
            reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );

        } catch (IOException e) {
            throw new RuntimeException();
        }

    }

    private void start()  {
        try (ServerSocket serverSocket =
                     new ServerSocket(Data.SERVER_PORT, 50, InetAddress.getByName(Data.SERVER_PATH));)
        {
            accept(serverSocket);
        } catch (IOException e) {
           throw new RuntimeException();
        }
    }


    public void getFile(String fileName){
        String answer = (core.getFile(fileName));
        try {
            writer.write(answer + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }


    }
    public void addFile(String fileName){
        try {
            String date = reader.readLine();
            writer.write(core.addFile(fileName,date) +"\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    public void deleteFile(String fileName){
        try {
            writer.write(core.deleteFile(fileName) + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
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


            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}



