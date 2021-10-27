package main;

import java.io.*;
import java.net.Socket;

public class FileHandler {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;

    private FileHandler(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader, BufferedOutputStream outputStream, BufferedInputStream inputStream) {
        this.socket = socket;
        this.bufferedWriter = bufferedWriter;
        this.bufferedReader = bufferedReader;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    static class Builder{
        private Socket socket;
        private BufferedWriter bufferedWriter;
        private BufferedReader bufferedReader;
        private BufferedOutputStream outputStream;
        private BufferedInputStream inputStream;

        Builder setSocket(Socket socket) {
            this.socket = socket;
            return this;
        }

        Builder setBufferedWriter(BufferedWriter bufferedWriter) {
            this.bufferedWriter = bufferedWriter;
            return this;
        }

        Builder setBufferedReader(BufferedReader bufferedReader) {
            this.bufferedReader = bufferedReader;
            return this;
        }

        Builder setOutputStream(BufferedOutputStream outputStream) {
            this.outputStream = outputStream;
            return this;
        }

        Builder setInputStream(BufferedInputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }
    }
    FileHandler build(){
        return new FileHandler(socket,bufferedWriter,bufferedReader,outputStream,inputStream);
    }





    public void writingFromAStreamToABinaryFile(){
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


    public void writingFromABinaryFileToAStream(){
        try(BufferedInputStream inputStream = new BufferedInputStream(
                new FileInputStream("C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data\\123png.png"));

        ) {
            int i;

            System.out.println("1");
            while ((i = inputStream.read()) != -1){
                writer.write(i);
                System.out.println("2");
                writer.flush();
                System.out.println("3");
            }
            System.out.println("4");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

















}
