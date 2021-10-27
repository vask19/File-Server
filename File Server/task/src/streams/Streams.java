package streams;

import java.io.*;
import java.util.Scanner;

public class Streams {


    public static String readTxtFile(File file) {
        StringBuilder fileContents = new StringBuilder();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine())
                fileContents.append(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
        return fileContents.toString();
    }

    public static void writeInTxtFile(File file,String date){
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file)
        )){
            writer.write(date + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException();
        }

    }
}
