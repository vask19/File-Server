package main;

import java.io.*;
import java.net.Socket;

public class FileHandler implements AutoCloseable{
    private Socket socket;
    private BufferedWriter socketWriter;
    private BufferedReader socketReader;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;
    private String outputPath;
    private String inputPath;


    public FileHandler(Socket socket){
        this.socket = socket;
        createSocketStreams();
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public void  writingFromAStreamToABinaryFile(){
        try {
            int i;
            while ((i = socketReader.read()) != -1 ){
                outputStream.write(i);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writingFromABinaryFileToAStream(){
        try {
            int i;
            while ((i = inputStream.read()) != -1){
                socketWriter.write(i);
                socketWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    private void createBinaryStreams(String outputFileName,String inputFileName){
        try{
            if (outputStream == null){
                outputStream = new BufferedOutputStream(
                        new FileOutputStream(outputFileName));
            }
            if (inputStream == null){
                inputStream = new BufferedInputStream(
                        new FileInputStream(inputFileName));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createSocketStreams() {
        try {
            if (socketReader == null){
                socketReader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
            }
            if (socketWriter == null){
                socketWriter = new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getFile() {
    }


    @Override
    public void close() throws Exception {
        if (socketWriter != null) socketWriter.close();
        if (socketReader != null) socketReader.close();
        if (socket != null) socket.close();
        if (outputStream != null) outputStream.close();
        if (inputStream != null) inputStream.close();


    }


}











/*(BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream("C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\client\\data\\" + fileName))
            )*/

/*(BufferedInputStream inputStream = new BufferedInputStream(
                    new FileInputStream("C:\\Users\\vask\\IdeaProjects\\File Server\\File Server\\task\\src\\server\\data\\123png.png"));

            )*/