package server;

import client.Client;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Server server = null;

        try {
            server = Server.getInstance();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Core core = Core.getInstance();




        /*do {
            String answer = scanner.nextLine();
            if (answer.equals("exit"))
                return;
            String fileName = answer.split(" ")[1];
            answer = answer.split(" ")[0];
            switch (answer){
                case "add":
                    if (core.addFile(fileName))
                        System.out.printf("The file %s added successfully\n",fileName);
                    else System.out.printf("Cannot add the file %s\n",fileName);
                    break;
                case "get":
                    if (core.getFile(fileName))
                        System.out.printf("The file %s was sent\n",fileName);
                    else System.out.printf("The file %s not found\n",fileName);
                    break;
                case "delete":
                    if (core.deleteFile(fileName))
                        System.out.printf("The file %s was deleted\n",fileName);
                    else System.out.printf("The file %s not found\n",fileName);

                    break;
            }

        }while (true);*/














    }


}