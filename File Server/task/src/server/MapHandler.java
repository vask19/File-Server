package server;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapHandler {
    private HashMap<Integer,File> fileAndFileId;

    public MapHandler() {

        fileAndFileId = new HashMap<>();
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
    /*ищет файл в мапе по ид*/
    public File getFile(Integer id){
        return fileAndFileId.get(id);
    }









    /*удаляет файл с мапы*/
    public void deleteFile(File file) {
        fileAndFileId.remove(createFileId(file.getName()));


    }


    public void printMap(){
        System.out.println(fileAndFileId);
    }
}
