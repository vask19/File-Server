package server;

import data.Data;
import files.SerializationUtils;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapHandler implements AutoCloseable{
    private static volatile HashMap<Integer,File> fileAndFileId;

    public MapHandler() {
        fileAndFileId = new HashMap<>();
        String fileName = "C:\\File-Server-GUIv1\\src\\main\\java\\server\\resources\\meta.bin";
        File file = new File(fileName);
        if (file.exists()) {
            try {
                fileAndFileId = (HashMap<Integer, File>) SerializationUtils.deserialize(fileName);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            serializationMap();
        }
    }

    public HashMap<Integer,File> getMap(){
        return  fileAndFileId;
    }






    /*добавляет файл и ид файла в мапу*/
    public void putFile(String fileName,Integer fileId){
        fileAndFileId.put(fileId,new File(fileName));


    }


    /*создает уникальный ид файла*/
    private Integer createFileId(String fileName) {
        File file = new File(fileName);
        return file.hashCode();
    }






    /*ищет файл в мапе по имени*/
    public File getFile(String  fileName){
        Integer fileId = createFileId(fileName);
        return fileAndFileId.get(fileId);
    }
    /*исчет файл в мапе по ид*/
    public File getFile(Integer id){
        return fileAndFileId.get(id);
    }


    /*удаляет файл с мапы*/
    public void deleteFile(File file) {

        fileAndFileId.remove(createFileId(Data.SERVER_DATA_PATH + file.getName()));


    }

    public void serializationMap(){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream("C:\\File-Server-GUIv1\\src\\main\\java\\server\\resources\\meta.bin")
        )){
            outputStream.writeObject(fileAndFileId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void printMap(){
        System.out.println(fileAndFileId);
    }

    @Override
    public void close() throws Exception {



    }
}
