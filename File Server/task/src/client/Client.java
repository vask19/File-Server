package client;
import main.Data;
import java.io.*;
import java.net.Socket;
public class Client {

    private BufferedReader reader;
    private BufferedWriter writer;


    public void start(String answer, String fileName, String date) {
        try (Socket socket = new Socket(Data.SERVER_PATH, Data.SERVER_PORT))
         {
            System.out.println("The request was sent.");
            switch (answer) {
                case "1" -> sendGetRequest(fileName);
                case "2" -> sendPutRequest(fileName,date);
                case "3" -> sendDeleteRequest(fileName);
                case "exit" -> closeServer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void sendGetRequest(String fileName) throws IOException {
        writer.write("GET " + fileName + "\n");
        writer.flush();
        String response = reader.readLine();
        String responseCode = response.substring(0, 3);
        System.out.println(responseCode.equals("200")
                ? "The content of the file is:" + response.substring(3)
                : "The response says that the file was not found!");

    }
    private void sendPutRequest(String fileName,String date) throws IOException {
        writer.write("PUT " + fileName + "\n");
        writer.flush();
        writer.write(date + "\n");
        writer.flush();
        String response = reader.readLine();
        System.out.println(response.equals("200") ? "The response says that the file was created!"
                : "The response says that creating the file was forbidden!");

    }
    private void sendDeleteRequest(String fileName) throws IOException {
        writer.write("DELETE " + fileName + "\n");
        writer.flush();

        String response = reader.readLine();
        System.out.println(response.equals("200") ? "The response says that the file was successfully deleted!"
                : "The response says that the file was not found!");

    }
    private void closeServer() throws IOException {
        writer.write("exit\n");
        writer.flush();
    }



    private void createTextStreams(Socket socket){
        try {

       reader = new BufferedReader(
                new InputStreamReader(
                       socket.getInputStream()));

        writer = new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}