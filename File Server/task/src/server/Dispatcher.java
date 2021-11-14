package server;
import data.Request;
import data.Response;
import server.controller.DeleteController;
import server.controller.GetController;
import server.controller.PutController;
import java.io.*;
import java.net.Socket;

public class Dispatcher {
    private Socket socket;
    private  MapHandler mapHandler;


    public Dispatcher(Socket socket, MapHandler mapHandler) {
        this.socket = socket;
        this.mapHandler = mapHandler;
    }



    private boolean findFile(Request request) {
        File file = null;
        if (request.isByName()){
            file = mapHandler.getFile(request.getFileName());
        }
        else {
            file = mapHandler.getFile(request.getFileId());

        }

        return file != null;
    }

    public void get(Request request){
        try (GetController controller = new GetController(mapHandler,socket)) {
            if (findFile(request)){
                Response response = Dispatcher.createResponse(200);
                if (request.isById())
                    response.setFileName(mapHandler.getFile(request.getFileId()).getName());
                Thread.sleep(500);
                sendResponse(response);
                Thread.sleep(500);
                Response newResponse = controller.get(request);
                Thread.sleep(500);
                sendResponse(newResponse);
            }
            else sendResponse(Dispatcher.createResponse(404));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Request request){
        DeleteController controller = new DeleteController(mapHandler);
        sendResponse(controller.delete(request));

    }
    public void put(String  fileName){
        try(PutController controller = new PutController(mapHandler,socket)){
            sendResponse(controller.put(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*отправляет клиенту ответ сервера*/
    public void sendResponse(Response response) {
        try {

            ObjectOutputStream outputStream =
                    new ObjectOutputStream(
                            socket.getOutputStream());

            outputStream.writeObject(response);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //считывает запрос со стрима -> создает его -> возвращает
    public Request getClientRequest(){
        Request request = new Request();
        try {ObjectInputStream objectInputStream =
                     new ObjectInputStream(
                             socket.getInputStream());{
            request = (Request) objectInputStream.readObject();
          }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;

    }

    /*создает ответ для PUT*/
    public static Response createResponse(int statusCode, int fileId){
        Response response = new  Response();
        response.setStatusCode(statusCode);
        response.setFileId(fileId);
        return response;
    }
    /*создает ответ для GET*/
    private static Response createResponse(int statusCode,String fileContent){
        Response response = new Response();
        response.setFileContent(fileContent);
        response.setStatusCode(statusCode);
        return response;
    }

    /*создает ответ для статус кода*/
    public static Response createResponse(int statusCode){
        Response response = new Response();
        response.setStatusCode(statusCode);
        return response;

    }


}



























/*    public static void  test(String fileName){
        new Thread(() -> {
            File file = mapHandler.getFile(fileName);

            synchronized (file){
                System.out.println("true get");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }*/