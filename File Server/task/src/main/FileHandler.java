package main;

import java.io.*;
import java.net.Socket;

public class FileHandler implements AutoCloseable{

    private BufferedWriter writer;
    private BufferedReader reader;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;
    private String outputPath;
    private String inputPath;


    public FileHandler(){

    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public void binaryFileHandling(){
        createBinaryStreams();
        try {
            int i;
            while ((i = inputStream.read()) != -1 ){
                outputStream.write(i);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void textFileHandling() {
        createTextStreams();
        try{
            String line;
            while ( (line = reader.readLine()) != null)
            {
                writer.write(line);
                writer.flush();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void createFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();

    }






    private void createTextStreams(){
        try{
            createFile(outputPath);
            if (writer == null)
                writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(outputPath)));
            if (reader == null)
                reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(inputPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createBinaryStreams(){
        try{
            createFile(outputPath);
            if (outputStream == null){
                outputStream = new BufferedOutputStream(
                        new FileOutputStream(outputPath));
            }
            if (inputStream == null){
                inputStream = new BufferedInputStream(
                        new FileInputStream(inputPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    @Override
    public void close() throws Exception {
        if (writer != null) writer.close();
        if (reader != null) reader.close();

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