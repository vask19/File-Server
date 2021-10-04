package server;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Core {
    private List<String > files;
    private final Pattern pattern;
    private static Core instance;
    private Core(){
        files = new ArrayList<>();
        pattern = Pattern.compile("file([0-9]|10)");
    };
    public static Core getInstance(){
        if (instance == null){
            instance = new Core();
        }
        return instance;
    }

    private boolean chekFileName(String fileName){
        return pattern.matcher(fileName).matches();
    }

    public boolean addFile(String fileName){
        if (chekFileName(fileName)){
            if (!files.contains(fileName)){
                files.add(fileName);
               return true;
            }
        }
        return false;
    }

    public boolean getFile(String fileName){
        if (chekFileName(fileName)){
            return files.contains(fileName);
        }
        return false;
    }
    public boolean deleteFile(String fileName){
        if (chekFileName(fileName)){
            return files.remove(fileName);
        }
        return false;
    }



}
