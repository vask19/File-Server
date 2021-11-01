package server;

import main.Data;
import main.FileHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerR {
    private void startServer()  {
        try (ServerSocket serverSocket =
                     new ServerSocket(Data.SERVER_PORT, 50, InetAddress.getByName(Data.SERVER_PATH))){
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                String request = getRequest(socket);

                if (request.equals("exit")){
                    serverSocket.close();
                    return;
                }
                new Thread(() -> {
                    startProcess(socket,request);
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getRequest(Socket socket){
        String response = null;
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     socket.getInputStream())))
        {
            response = reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }


    private void startProcess(Socket socket,String response) {
        String httpMethod = response.split(" ")[0];
        String fileName = response.split(" ")[1];

        try (FileHandler fileHandler = new FileHandler(socket))
        {
            switch (httpMethod) {
                case "PUT" -> addFile(fileName,fileHandler);
                case "DELETE" -> deleteFile(fileName);
                case "GET" -> getFile(fileName,fileHandler);
            }
        }
         catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getFile(String fileName, FileHandler fileHandler) {
        fileHandler.setInputPath(Data.SERVER_DATA_PATH + fileName);
        fileHandler.getFile();
    }

    private void addFile(String fileName, FileHandler fileHandler) {

    }






    private void deleteFile(String fileName) {

    }








}
