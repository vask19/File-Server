package server;

import data.Data;
import data.Request;


import java.net.Socket;

public class Session implements Runnable,AutoCloseable{
    private  Socket socket;
    private MapHandler mapHandler;

    public Session(Socket socket,MapHandler mapHandler)  {

        this.socket = socket;
        this.mapHandler = mapHandler;





    }
    @Override
    public void run() {
        Dispatcher fileHandler =  new Dispatcher(this.socket,this.mapHandler);

            Request request = fileHandler.getClientRequest();
            String fileName = request.getNewFileName() == null ?
                    request.getFileName() : request.getNewFileName();
            request.setFileName(Data.SERVER_DATA_PATH + fileName);


            switch (request.getRequestType()){

                case "DELETE" -> deleteFile(request,fileHandler);
                case "PUT" -> putFile(request,fileHandler);
                case "GET" -> getFile(request,fileHandler);
                case "EXIT" -> Server.exit();

            }



    }




    /*отправляет файл с сервера на клиент*/
    private void getFile(Request request, Dispatcher dispatcher) {
        dispatcher.get(request);


    }
    /*добавляет файл на сервер и  отправляет ответ клиенту*/
    private void putFile(Request request, Dispatcher dispatcher)  {
       dispatcher.put(request.getFileName());

    }
    /*если клиент отправил запрос на удаление файла -> метод вызывает
    * удаление файла на обработчике файлов ->
    * -> вызывает метод отправки результата клиенту*/
    private void deleteFile(Request request, Dispatcher dispatcher){
        dispatcher.delete(request);


    }



    @Override
    public void close() throws Exception {

    }
}
