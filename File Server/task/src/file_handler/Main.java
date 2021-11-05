package file_handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        ConcurrentHashMap<String ,String > map = new ConcurrentHashMap<>();

        String path = ("C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\file_handler\\data\\");
        Thread thread1 = new Thread(()->
        {
            map.put("1","m.txt");
            File file = new File(path + (map.get("1")));
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        Thread thread2 = new Thread( () ->
        {
            for (int i =0;i<15;i++){
                System.out.println(map);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        thread1.start();
        thread2.start();




    }
}
