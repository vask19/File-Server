package server.controller;
import data.Response;

import files.FileHandler;
import server.Dispatcher;
import server.MapHandler;
import java.io.*;
import java.net.Socket;



public class PutController implements AutoCloseable{
    private MapHandler mapHandler;
    private Socket socket;
    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    public PutController(MapHandler mapHandler,Socket socket){
        this.mapHandler = mapHandler;
        this.socket = socket;
    }

    public Response put(String fileName){
        return putFile(fileName);
    }

    /*создает файл/ создает стримы/ занимает монитор файла / добавляет файл в мапу
     * / вызывает метод записи файла*/
    public Response putFile(String serverFile){
        File file = new File(serverFile);
        if (!file.exists()){
            synchronized (file){
                Integer fileId = createFileId(serverFile);
                mapHandler.putFile(serverFile,fileId);
                if (file.getName().matches("\\w+.txt")){
                    putTxtFile(file);

                }
                else  putBinaryFile(file);
                return Dispatcher.createResponse(200,fileId);

            }
        }
        return Dispatcher.createResponse(403);
    }
    /*принимает с сокета байты и записывает их на сервере*/
    private void putTxtFile(File file) {
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
    /*принимает с сокета байты и записывает их на сервере*/
    private void putBinaryFile(File file) {
        try {
            bufferedInputStream = new BufferedInputStream(
                    socket.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(file)
            );
                  FileHandler.transferBinaryFile(bufferedOutputStream,bufferedInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        /*создает уникальный ид файла*/
    private Integer createFileId(String fileName) {
        File file = new File(fileName);
        return file.hashCode();
    }
    @Override
    public void close() throws Exception {
        if (bufferedOutputStream != null)
            bufferedOutputStream.close();
        if (bufferedInputStream != null)
            bufferedInputStream.close();
        if (dataOutputStream != null)
            dataOutputStream.close();
        if (dataInputStream != null)
            dataInputStream.close();

    }
}
