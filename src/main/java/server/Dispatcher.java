package server;
import data.Data;
import data.Request;
import data.Response;
import files.FileHandler;
import server.controller.DeleteController;
import server.controller.GetController;
import server.controller.PutController;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Socket socket;
    private MapHandler mapHandler;


    public Dispatcher(Socket socket, MapHandler mapHandler) {
        this.socket = socket;
        this.mapHandler = mapHandler;
    }


    private boolean findFile(Request request) {
        File file = null;
        if (request.isByName()) {
            file = mapHandler.getFile(request.getFileName());
        } else file = mapHandler.getFile(request.getFileId());

        return file != null;
    }

    public void get(Request request) {
        try (GetController controller = new GetController(mapHandler, socket)) {
            if (findFile(request)) {
                sendResponse(Dispatcher.createResponse(200));
                Response response = controller.get(request);
                Thread.sleep(500);
                sendResponse(response);
            } else sendResponse(Dispatcher.createResponse(404));


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


        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

    //считывает запрос со стрима -> создает его -> возвращает
    public Request getClientRequest() {
        Request request = new Request();
        try {
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(
                            socket.getInputStream());
            {
                request = (Request) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;

    }



    public void delete(Request request){
        File file = null;
        request.setFileName(Data.SERVER_DATA_PATH + request.getFileName());
        if (request.isById())
            file = mapHandler.getFile(request.getFileId());
        else if (request.isByName()){
            file = mapHandler.getFile(request.getFileName());

        }
        synchronized (file){
            mapHandler.deleteFile(file);

            if( file.delete()){
                sendResponse( createResponse(200));
            }
        }
        sendResponse(createResponse(404));
        mapHandler.serializationMap();
    }



    public void put(Request request) {
        String fileName = createFileName(request);
        File file = new File(fileName);
        if (!file.exists()) {
            synchronized (file) {
                Integer fileId = createFileId(fileName);
                mapHandler.putFile(fileName, fileId);
                if (request.getFileContent().length > 0) {
                    try (FileOutputStream outputStream = new FileOutputStream(file)) {
                        outputStream.write(request.getFileContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sendResponse(createResponse(200,fileId));
            }

        }
        createResponse(403);
        mapHandler.serializationMap();
    }


    private String createFileName(Request request){
        String filePath = request.getFileName();
        String[] comps = filePath.split("\\\\");
        return Data.SERVER_DATA_PATH + comps[comps.length-1];
    }


    private Integer createFileId(String fileName) {
        File file = new File(fileName);
        return file.hashCode();
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

    public void getMap() {
        try {

            HashMap<Integer,File> map = mapHandler.getMap();
            HashMap<Integer,String> newMap = new HashMap<>();
            for (Map.Entry<Integer,File> entry : map.entrySet()){
                newMap.put(entry.getKey(),entry.getValue().getName());

            }
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(
                            socket.getOutputStream());

            outputStream.writeObject(newMap);
            outputStream.flush();
            Thread.sleep(1);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
