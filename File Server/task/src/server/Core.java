package server;

import java.io.*;
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

    public String  addFile(String fileName,String date){
        File file = new File(dataPath + fileName);
        try {
            if (!file.exists() & file.createNewFile()){
                writeInFile(file,date);
                return "200";
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }
        return "403";
    }



    public  String getFile(String fileName){
        File file = new File(dataPath + fileName);
        if (! file.exists())
            return "404";
        else return "200 " + readFile(file);
    }


    private void writeInFile(File file,String date){
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file)
        )){
            writer.write(date);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }

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
