package server;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Core core = Core.getInstance();
        System.out.println("+8");

        Server server = Server.getInstance();
        System.out.println(server.getSocket());
        System.out.println("+9");


        do {
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

        }while (true);














    }


}