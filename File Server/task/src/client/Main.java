package client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Client client = new Client();
        String answer;
        String date = " ";
        String fileName =" ";
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter action (1 - get a file, 2 - create a file, 3 - delete a file):");
        answer = sc.nextLine();
        if (!answer.equals("exit")){
            System.out.println("Enter filename:");
            fileName = sc.nextLine();


            if (answer.equals("2")) {
                System.out.println("Enter file content:");
                date = sc.nextLine();
            }

        }
        client.start(answer,fileName,date);



    }
}
