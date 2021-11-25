package client.gui;

import client.gui.components.ServerTable;
import data.Data;
import data.Request;
import data.Response;
import files.FileHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Menu {
    private static Request request = new Request();
    private static Response response;
    private static JTextField fileNameOrIdTextField;
    private static InterfaceBuilder interfaceBuilder;
    private static volatile Map<Integer,String > map;
    private static JScrollPane table;

    public static void main(String[] args) {
        map = new HashMap<>();
        map = createFirstMap();


        table = new ServerTable().getTable(map);
        JRadioButton getButton = new JRadioButton(new RequestTypeChanging("GET"));
        getButton.setText("GET");
        JRadioButton putButton = new JRadioButton(new RequestTypeChanging("PUT"));
        putButton.setText("PUT");
        JRadioButton deleteButton = new JRadioButton(new RequestTypeChanging("DELETE"));
        deleteButton.setText("DELETE");
        JButton send  = new JButton(new RequestSender());
        send.setText("send");
        fileNameOrIdTextField = new JTextField();

        interfaceBuilder = new InterfaceBuilder(putButton, getButton, deleteButton,
                table,send,fileNameOrIdTextField);




    }

    private static Map<Integer, String> createFirstMap() {
        try {
            Socket socket = new  Socket(Data.SERVER_PATH, Data.SERVER_PORT);
            Request request = new Request();
            request.setRequestType("MAP");
            sendRequest(request,socket);
            Map<Integer,String> map = getServerResponse(socket);
            socket.close();

            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    private static Map<Integer,String > getMap(){
        try {
            Socket socket = new  Socket(Data.SERVER_PATH, Data.SERVER_PORT);
            Request request = new Request();
            request.setRequestType("MAP");
            sendRequest(request,socket);
            Map<Integer,String> map = getServerResponse(socket);
            socket.close();
            return map;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }


    private static <T> T getServerResponse(Socket socket) {
        T response = null;
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            response = (T) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }




    public static void sendRequest(Request request, Socket socket){
        try{ ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(
                                socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }










    private static class RequestTypeChanging extends AbstractAction {
        private final String requestType;

        public RequestTypeChanging(String requestType) {
            this.requestType = requestType;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            request.setRequestType(requestType);
            e.setSource(false);

        }
    }


    private static class RequestSender extends AbstractAction {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream objectOutputStream;
        private String fileName;
        private int fileId;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (fileNameOrIdTextField.getText().equals("")) {

            } else {
                request.setFileName(fileNameOrIdTextField.getText());
                try {
                    socket = new Socket(Data.SERVER_PATH, Data.SERVER_PORT);
                    System.out.println(2);
                    System.out.println(socket.isClosed());
                    switch (request.getRequestType()) {
                        // case "GET" -> get();
                        case "PUT" -> put();
                        case "DELETE" -> delete();
                    }
                    System.out.println(3);


                    Thread.sleep(200);
                    socket.close();



                    interfaceBuilder.revalidate(getMap());

                } catch (IOException | InterruptedException unknownHostException) {
                    unknownHostException.printStackTrace();
                }

            }


        }


        private void delete() {
            System.out.println(request.getFileName());

            request.setByName(true);
            sendRequest(request, socket);
            Response response = getServerResponse(socket);
            System.out.println(response);


        }


        private void put() {
            request.setByName(true);
            byte[] content = loadFile(request.getFileName());
            request.setFileContent(content);
            sendRequest(request, socket);
            Response response = getServerResponse(socket);


        }


        private void saveFile(String fileName, byte[] content) {
            File file = new File(Data.CLIENT_DATA_PATH + fileName);
            if (content.length > 0) {
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    outputStream.write(content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        private byte[] loadFile(String fileName) {
            File file = new File(fileName);
            byte[] content = null;
            if (file.length() > 0) {
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    content = inputStream.readAllBytes();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return content;
        }


    }}
