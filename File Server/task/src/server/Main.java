package server;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ServerR server = null;

        try {
            server = ServerR.getInstance();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }}


