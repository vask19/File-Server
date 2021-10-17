package server;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = null;

        try {
            server = Server.getInstance();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }}


