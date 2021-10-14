package client;

import main.Data;

import java.io.*;

import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
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
            writer.write("PUT file6.txt\n");
            writer.flush();
            writer.write("124\n");



            writer.flush();

            System.out.println(reader.readLine());
            System.out.println("2");





        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
