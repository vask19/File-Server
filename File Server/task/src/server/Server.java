package server;
import main.Data;

import java.io.*;
import java.net.*;
public class Server {
    private final Core core;
    private static Server instance;
    private BufferedWriter writer;
    private BufferedReader reader;

    private Server() {
        core = new Core();
        startServer();
    }
    public static Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }
    private void startProcess(Socket socket,String response) throws IOException {
        String httpMethod = response.split(" ")[0];
        String fileName = response.split(" ")[1];
         switch (httpMethod){
            case "PUT" -> addFile(fileName);
            case "DELETE" -> deleteFile(fileName);
            case "GET" -> getFile(fileName);
        }
    }
    private void closeConnection() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
    private void startServer()  {
        try (ServerSocket serverSocket =
                    new ServerSocket(Data.SERVER_PORT, 50, InetAddress.getByName(Data.SERVER_PATH))){
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                createStreams(socket);
                String response = reader.readLine();
                if (response.equals("exit")){
                    closeConnection();
                    serverSocket.close();
                    return;
                }
                new Thread(() -> {
                    try {
                        startProcess(socket,response);
                        closeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
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