package file_handler;

import java.io.File;
import java.io.IOException;

import java.util.concurrent.ConcurrentHashMap;

public class Main {
    int count = 0;
    ConcurrentHashMap<String, File> map = new ConcurrentHashMap<>();
    static String path = ("C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\file_handler\\data\\");


    public static void main(String[] args) throws IOException {
        String f = path + "m.txt";

        File file = new File(f);



    }


    public void delete(File file) {
        synchronized (file) {


        }


    }

    public void add(File file) {
        synchronized (file) {


        }
    }

    public void get(File file) {
        synchronized (file) {


        }
    }




}
