package main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MapHandler {
    private static volatile Map<String, UUID> filesNamesAndId;





    private void readMap(){
        filesNamesAndId = new HashMap<>();

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





}
