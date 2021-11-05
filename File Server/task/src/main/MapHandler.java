package main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MapHandler implements AutoCloseable{
    private static volatile Map<String, String > filesIdAndNames;
    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;




    public static void main(String[] args) {
        filesIdAndNames = new HashMap<>();
        filesIdAndNames.put("1","2");
        writeToFileWithDataFiles();
        System.out.println(readFileWithDataFiles());












    }

    private static void writeToFileWithDataFiles(){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(Data.FILE_WITH_ID_AND_NAMES)
        )){
            outputStream.writeObject(filesIdAndNames);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private static Map<String,String> readFileWithDataFiles(){

        HashMap map = new HashMap<>();
        try(ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(Data.FILE_WITH_ID_AND_NAMES))) {
            map  = (HashMap) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }
    private  String createUniqueNameToFile(String ext){
        Random random = new Random();
        File dir = new File(Data.SERVER_DATA_PATH);
        String fileName = String.format("%s%s",System.currentTimeMillis(),random.nextInt(100000)+ext);
        File file = new File(dir, fileName);
        while (file.exists()){
            fileName = String.format("%s%s",System.currentTimeMillis(),random.nextInt(100000)+ext);
            file = new File(dir, fileName);
        }
        return fileName;
    }

    private UUID createUniqueIdToFIle(){
         return UUID.randomUUID();
    }








    @Override
    public void close() throws Exception {
        writeToFileWithDataFiles();
    }
}
