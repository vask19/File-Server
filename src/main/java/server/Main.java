package server;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            Server server = new Server();
            new Thread(()->{
                String answer = new Scanner(System.in).nextLine();
                if (answer.equals("exit")) {
                    try {
                        server.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            server.startServer();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
