package client;

import main.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public void start(){
        try(Socket socket = new Socket(Data.SERVER_PATH,Data.SERVER_PORT);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());)
        {
            System.out.println("Client started");
            dataOutputStream.writeUTF("Give me everything you have!");
            String msg = dataInputStream.readUTF();
            System.out.println(msg);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
