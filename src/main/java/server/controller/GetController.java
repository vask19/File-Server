package server.controller;
import data.Request;
import data.Response;
import files.FileHandler;
import server.Dispatcher;
import server.MapHandler;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GetController implements AutoCloseable{
    private MapHandler mapHandler;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private DataOutputStream dataOutputStream;
    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;


    public GetController(MapHandler mapHandler, Socket socket){
        this.mapHandler = mapHandler;
        this.socket = socket;
    }


    public Response get(Request request){
        return getFile(request);

    }
    /*отправляет файл с сервера клиенту*/

    public Response getFile(Request request) {
        File file = null;
        if (request.isById())
            file = mapHandler.getFile(request.getFileId());
        else if (request.isByName())
            file = mapHandler.getFile(request.getFileName());
        synchronized (file) {
            if (file.exists()){
                if (file.getName().matches("\\w+.txt")){
                    System.out.println(15);
                    getTextFile(file);

                }
                else {
                    System.out.println(17);
                    getBinaryFile(file);
                }
                return Dispatcher.createResponse(200);
            }
        }
        return Dispatcher.createResponse(404);
    }

    private void getBinaryFile(File file) {
        try {
            bufferedInputStream=
                    new BufferedInputStream(
                            new FileInputStream(file));
             bufferedOutputStream =
                    new BufferedOutputStream(
                            socket.getOutputStream());
            FileHandler.transferBinaryFile(bufferedOutputStream,bufferedInputStream);
            bufferedOutputStream.write(-1);
            bufferedOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getTextFile(File file) {
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


    @Override
    public void close() throws Exception {
        if (bufferedReader != null)
            bufferedReader.close();

        if (bufferedWriter != null)
            bufferedWriter.close();

        if (dataOutputStream != null)
            dataOutputStream.close();

       if (bufferedInputStream !=null)
            bufferedInputStream.close();

        if (bufferedOutputStream != null)
            bufferedOutputStream.close();
    }
}



