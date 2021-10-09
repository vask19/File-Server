package client;

import main.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void start(){
        System.out.println("Client Started");
        try(Socket socket = new Socket(Data.SERVER_PATH,Data.SERVER_PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());)
        {

            dataOutputStream.writeUTF("Give me everything you have!");
            System.out.println("Sent: " + "Give me everything you have!");
            System.out.println("Received: " + dataInputStream.readUTF());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
