package server;

import data.Data;
import data.Request;


import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

public class Session implements Runnable{
    private  Socket socket;
    private MapHandler mapHandler;

    public Session(Socket socket,MapHandler mapHandler)  {

        this.socket = socket;
        this.mapHandler = mapHandler;





    }
    @Override
    public void run() {
        System.out.println(1);
        Dispatcher dispatcher =  new Dispatcher(this.socket,this.mapHandler);
            Request request = dispatcher.getClientRequest();
            switch (request.getRequestType()){
                case "DELETE" -> dispatcher.delete(request);
                case "PUT" -> dispatcher.put(request);
                case "GET" -> getFile(request,dispatcher);
                case "MAP" ->dispatcher.getMap();

            }

    }


    /*отправляет файл с сервера на клиент*/
    private void getFile(Request request, Dispatcher dispatcher) {
        dispatcher.get(request);
    }






}
