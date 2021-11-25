package server.controller;

import data.Request;
import data.Response;
import server.Dispatcher;
import server.MapHandler;

import java.io.File;


public class DeleteController {
    private MapHandler mapHandler;

    public DeleteController(MapHandler mapHandler){
        this.mapHandler = mapHandler;
    }

    public Response delete(Request request){
        return deleteFile(request);

    }
    private Response deleteFile(Request request) {
        File file = null;
        if (request.isById())
            file = mapHandler.getFile(request.getFileId());
        else if (request.isByName())
            file = mapHandler.getFile(request.getFileName());

        synchronized (file){
            if (file.delete()){
                mapHandler.deleteFile(file);
                return Dispatcher.createResponse(200);}
        }
        return Dispatcher.createResponse(404);
    }
}
