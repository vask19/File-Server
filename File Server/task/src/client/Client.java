package client;
import main.Data;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    Scanner sc = new Scanner(System.in);
    public void start(String answer, String fileName, String date) {
        try (Socket socket = new Socket(Data.SERVER_PATH, Data.SERVER_PORT);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()
                     ));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(
                             socket.getOutputStream()
                     )
             )
        ) {
            System.out.println("The request was sent.");
            switch (answer) {
                case "1" -> {
                    writer.write("GET " + fileName + "\n");
                    writer.flush();
                    String response = reader.readLine();
                    String responseCode = response.substring(0, 3);
                    System.out.println(responseCode.equals("200")
                            ? "The content of the file is:" + response.substring(3)
                            : "The response says that the file was not found!");
                }
                case "2" -> {
                    writer.write("PUT " + fileName + "\n");
                    writer.flush();
                    writer.write(date + "\n");
                    writer.flush();
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
                case "exit" -> {
                    writer.write("exit\n");
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}