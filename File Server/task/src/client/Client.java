package client;
import data.Request;
import data.Response;
import files.FileHandler;
import main.Data;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements AutoCloseable{
    private Socket socket;

    private DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;
    private ObjectInputStream in;
    private BufferedReader bufferedReader;
    private static Scanner scanner = new Scanner(System.in);


    Client(Socket socket) {
        this.socket = socket;
    }








    public void start() throws Exception {

        System.out.println("Enter action (1 - get a file, 2 - save a file, 3 - delete a file):");
        String answer = scanner.nextLine();
        switch (answer) {
            case "1" -> get();
            case "2" -> put();
            case "3" -> delete();
            case "exit" ->exit();
        }
        close();


    }

    private void exit() {
        Request request = new Request();
        request.setRequestType("EXIT");
        sendRequest(request);
    }


    private void put() {
        Response response = null;
        System.out.println("Enter name of the file:");
        String fileName = scanner.nextLine();
        System.out.println("Enter name of the file to be saved on server:");
        String newFileName = scanner.nextLine();
        Request request = new Request();
        request.setFileName(fileName);
        request.setRequestType("PUT");
        newFileName = newFileName.equals("") ? null : newFileName;
        request.setNewFileName(newFileName);


        sendRequest(request);
        File file = new File(Data.CLIENT_DATA_PATH + fileName);
        if (file.exists()) {
            if (file.getName().matches("\\w+.txt")) {
                putTextFile(file);
            } else {
                putBinaryFile(file);
            }


        }
        response = getServerResponse();
        if (response.getStatusCode() == 200){
            System.out.println("Response says that file is saved! ID = "+response.getFileId());
        }
        else System.out.println("The response says that this file is not found!");


    }

    /*получает ответ сервера*/
    private Response getServerResponse() {
        Response response = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            response = (Response) in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void putBinaryFile(File file) {
        try {
            bos = new BufferedOutputStream(
                    socket.getOutputStream()
            );
            bis = new BufferedInputStream(
                    new FileInputStream(file)
            );
            FileHandler.transferBinaryFile(bos, bis);
            bos.write(-1);
            bos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putTextFile(File file) {
        StringBuffer messageBuffer = new StringBuffer();
        String line;
        try {
            bufferedReader = new BufferedReader(
                    new FileReader(file)
            );
            while ((line = bufferedReader.readLine()) != null)
                messageBuffer.append(line);
            String message = String.valueOf(messageBuffer);
            dataOutputStream =
                    new DataOutputStream(socket.getOutputStream());
            byte[] messageInBytes = message.getBytes(StandardCharsets.UTF_8);
            int lengthMessage = messageInBytes.length;
            dataOutputStream.writeInt(lengthMessage);
            dataOutputStream.write(messageInBytes);
            dataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*отпределяет критерий поиска файла на сервере для методов GET & DELETE*/
    private void selectSearchCriteria(Request request, String answer) {
        switch (answer) {
            case "1" -> {
                request.setByName(true);
                System.out.println("Enter name:");
                String name = scanner.nextLine();
                request.setFileName(name);

            }
            case "2" -> {
                request.setById(true);
                System.out.println("Enter id:");

                long id = scanner.nextLong();

                if (id > Integer.MAX_VALUE)
                    System.out.println("The response says that this file is not found!");
                else request.setFileId((int) id);





            }
        }
    }


    private  void get() {
        Request request = new Request();
        Response response = null;

        request.setRequestType("GET");
        System.out.println("Do you want to get the file by name or by id (1 - name, 2 - id):");

        String answer = scanner.nextLine();
        selectSearchCriteria(request,answer);
        sendRequest(request);
        response = getServerResponse();

        if (response.getStatusCode() == 200) {

            System.out.println("Specify a name for it:");

            String fileName;
            fileName = scanner.next();
            if (fileName.equals(""))
                fileName = response.getFileName();


            File file = new File(Data.CLIENT_DATA_PATH + fileName);
            if (file.getName().matches("\\w+.txt")) {
                getTextFile(file);
            } else getBinaryFile(file);
            response = getServerResponse();
        }
        else System.out.println("The response says that this file is not found!");
















        /*
Do you want to get the file by name or by id (1 - name, 2 - id): > 2
Enter id: > 23
The request was sent.
The file was downloaded! Specify a name for it: > cat.jpg
File saved on the hard drive!*/

    }

    private void getBinaryFile(File file) {
        try {
            bos = new BufferedOutputStream(
                    new FileOutputStream(file)
            );
            bis = new BufferedInputStream(
                    socket.getInputStream()
            );
            FileHandler.transferBinaryFile(bos,bis);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTextFile(File file) {
        try {
            dataInputStream =
                    new DataInputStream(socket.getInputStream());
            dataOutputStream =
                    new DataOutputStream(
                            new FileOutputStream(file)
                    );
            int length = dataInputStream.readInt();
            byte[] fileCapacity = new byte[length];
            dataInputStream.readFully(fileCapacity, 0, fileCapacity.length);
            for (byte b :fileCapacity){
                dataOutputStream.write(b);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private  void delete(){
        System.out.println("Do you want to delete the file by name or by id (1 - name, 2 - id):");
        String  answer = scanner.nextLine();
        Request request = new Request();
        request.setRequestType("DELETE");

        selectSearchCriteria(request, answer);

        sendRequest(request);
        Response response = getServerResponse();
        System.out.println(response);

    }


    /*отправляет запрос серверу*/
    private void sendRequest(Request request){
        try {
            objectOutputStream =
                    new ObjectOutputStream(
                            socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void close() throws Exception {
        if (dataOutputStream != null)
            dataOutputStream.close();
        if (objectOutputStream != null)
            objectOutputStream.close();
        if ( bos!= null)
            bos.close();
        if ( bis!= null)
            bis.close();
        if ( in!= null)
            in.close();
        if ( bufferedReader!= null)
            bufferedReader.close();




    }


}