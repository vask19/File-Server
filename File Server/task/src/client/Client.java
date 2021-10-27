package client;
import main.Data;
import streams.Streams;

import java.io.*;
import java.net.Socket;
public class Client {

    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private String clientDataPath = "C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\client\\data";


    public void start(String answer, String fileName, String date) {
        try (Socket socket = new Socket(Data.SERVER_PATH, Data.SERVER_PORT))
         {
             createSocketsStreams(socket);
            System.out.println("The request was sent.");
            switch (answer) {
                case "1" -> test(fileName);//sendGetRequest(fileName);
                case "2" -> sendPutRequest(fileName,date);
                case "3" -> sendDeleteRequest(fileName);
                case "exit" -> closeServer();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void test(String fileName) throws IOException {
        socketWriter.write("4 5 6" + "\n");
        socketWriter.flush();
        try(BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream("C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\client\\data\\" + fileName))
        ) {
            int i;
            System.out.println("14");
            while ((i = socketReader.read()) != -1 ){

                outputStream.write(i);
                System.out.println("14.5");
                outputStream.flush();
                System.out.println("15");

            }
            System.out.println("16");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void sendGetRequest(String fileName) throws IOException {
        if (fileName.matches("\\w+.txt")){
            socketWriter.write("GET " + fileName + "\n");
            socketWriter.flush();
            String response = socketReader.readLine();
            String responseCode = response.substring(0, 3);
            System.out.println(responseCode.equals("200")
                    ? "The content of the file is:" + response.substring(3)
                    : "The response says that the file was not found!");

        }




    }
    private void saveTxtFile(String fileName){
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName)
        )){
            String line;
            while ((line = socketReader.readLine()) != null){
                writer.write(line + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
   }

   private void saveBinaryFile(String fileName){
        try(BufferedOutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(fileName))
        ) {
            int i;
            while ((i = socketReader.read()) != -1 ){
                outputStream.write(i);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

   }









    private void sendPutRequest(String fileName,String date) throws IOException {
        socketWriter.write("PUT " + fileName + "\n");
        socketWriter.flush();
        socketWriter.write(date + "\n");
        socketWriter.flush();
        String response = socketReader.readLine();
        System.out.println(response.equals("200") ? "The response says that the file was created!"
                : "The response says that creating the file was forbidden!");

    }
    private void sendDeleteRequest(String fileName) throws IOException {
        socketWriter.write("DELETE " + fileName + "\n");
        socketWriter.flush();

        String response = socketReader.readLine();
        System.out.println(response.equals("200") ? "The response says that the file was successfully deleted!"
                : "The response says that the file was not found!");

    }
    private void closeServer() throws IOException {
        socketWriter.write("exit\n");
        socketWriter.flush();
    }



    private void createSocketsStreams(Socket socket){
        try {

       socketReader = new BufferedReader(
                new InputStreamReader(
                       socket.getInputStream()));

        socketWriter = new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}