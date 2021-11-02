package server;

import main.Data;
import main.FileHandler;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class ServerR {
    private static ServerR instance;
    private ServerR() {
        startServer();
    }
    public static ServerR getInstance() throws IOException {
        if (instance == null) {
            instance = new ServerR();
        }
        return instance;
    }






    private void deleteFileFromMap(){

    }

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
                    startProcess(request);
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


    private void startProcess(String response) {
        String httpMethod = response.split(" ")[0];

        try (FileHandler fileHandler = new FileHandler())
        {
            switch (httpMethod) {
                case "PUT" -> addAFileToServer(response,fileHandler);
                case "DELETE" -> deleteFile(response);
                case "GET" -> getAFileFromTheServer(response,fileHandler);
            }
        }
         catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*Считывает файл с сервера и посывает его в папку клиента*/

    private void getAFileFromTheServer(String response, FileHandler fileHandler) {
        String fileName = response.split(" ")[1];
        String newFileName = response.split(" ")[2];

        fileHandler.setInputPath(Data.SERVER_DATA_PATH + fileName);
        fileHandler.setOutputPath(Data.CLIENT_DATA_PATH + newFileName);

        if (fileName.matches("\\w+.txt"))
            fileHandler.textFileHandling();
        else
            fileHandler.binaryFileHandling();

    }
//считывает файл с папки клиента и добавляет его на сервер
    private void addAFileToServer(String response, FileHandler fileHandler) {
        String fileName = response.split(" ")[1];
        String newFileName = response.split(" ")[2];
        fileHandler.setInputPath(Data.CLIENT_DATA_PATH + fileName);
        fileHandler.setOutputPath(Data.SERVER_DATA_PATH + fileName);

        if (fileName.matches("\\w+.txt"))
            fileHandler.textFileHandling();
        else
            fileHandler.binaryFileHandling();
        UUID uniqueId = UUID.randomUUID();

    }
    private void deleteFile(String response) {
        String fileName = response.split(" ")[1];
        File file = new File(Data.SERVER_DATA_PATH + fileName);
        if (file.exists())
            file.delete();


    }

}
