package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

public class NotFoundHandler implements Handler {

    public Response handle(Request request) {
        ResponseBuilder builder = new ResponseBuilder(404);
        return builder.version(request.getVersion()).body("Sorry, we couldn't find what you were looking for".getBytes()).build();
    }

    public boolean willHandle(String method, String path) {
        return true;
    }
}
