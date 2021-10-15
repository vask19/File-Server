package client;

import main.Data;

import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.time.format.SignStyle;
import java.util.Scanner;

public class Client {
    Scanner sc = new Scanner(System.in);

    public void start(){
        System.out.println("Client Started");
        try(Socket socket = new Socket(Data.SERVER_PATH,Data.SERVER_PORT);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    ));
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    )
            )
        )
        {
            System.out.println("Enter action (1 - get a file, 2 - create a file, 3 - delete a file):");
            String answer = sc.nextLine();
            System.out.println("Enter filename:");
            String fileName = sc.nextLine();
            switch (answer){
                case "1" -> {
                    writer.write( "GET " +  fileName + "\n");
                    writer.flush();

                    String response = reader.readLine();
                    String responseCode = response.substring(0,3);
                    System.out.println(responseCode.equals("200")
                            ? "The content of the file is:" + response.substring(3)
                            : "The response says that the file was not found!");

                }
                case "2" -> {
                    writer.write("PUT " + fileName + "\n");
                    writer.flush();
                    System.out.println("Enter file content:");
                    String date = sc.nextLine();
                    writer.write(date + "\n");
                    writer.flush();
                    System.out.println("1");

                    String response = reader.readLine();
                    System.out.println(response.equals("200") ? "The response says that the file was created!"
                            : "The response says that creating the file was forbidden!");
                }
                case "3" -> {
                    writer.write("DELETE " + fileName + "\n");
                    writer.flush();

                    String response = reader.readLine();
                    System.out.println(response.equals("200") ? "The response says that the file was successfully deleted!"
                            : "The response says that the file was not found!");

                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
