package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.File;

public class NotFoundHandler implements Handler {

    public Response handle(Request request) {
        ResponseBuilder builder = new ResponseBuilder(404);
        return builder.reasonPhrase().body("Sorry, we couldn't find what you were looking for".getBytes()).build();
    }

    public boolean willHandle(String method, String path) {
        if (!(new File(path).exists())) return true;
        return false;
    }
}
