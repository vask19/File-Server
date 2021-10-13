package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Core {
    private final String dataPath = "C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data\\";
    private String separator = File.separator;
    private List<String > files;
    private final Pattern pattern;
    private static Core instance;

    public static void main(String[] args) {
        System.out.println(new Core().addFile("file1.txt"));
        System.out.println(new Core().addFile("file1.txt"));
        Map<String,String>m = new Core().getFile("file1.txt") ;
        System.out.println(m);
        System.out.println(new Core().deleteFile("file1.txt"));
    }
    public Core(){
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

    public String  addFile(String fileName){
        File file = new File(dataPath + fileName);
        try {
            if (!file.exists() & file.createNewFile())
                return "200";
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return "403";
    }



    public  Map<String,String > getFile(String fileName){
        File file = new File(dataPath + fileName);
        if (! file.exists())
            return Map.of("404","not found");
        else return Map.of("200",readFile(file));
    }

    private String readFile(File file) {
        StringBuilder fileContents = new StringBuilder();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine())
                fileContents.append(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        return fileContents.toString();
    }

    public String  deleteFile(String fileName){
        File file = new File(dataPath + fileName);
        if (file.exists() & file.delete())
            return "200";
        return "404";
    }



}
