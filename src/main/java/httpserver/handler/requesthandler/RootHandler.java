package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

public class RootHandler implements Handler {

    public Response handle(Request request) {
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.reasonPhrase().version(request.getVersion()).body("Hello World".getBytes()).build();
    }

    public boolean willHandle(String method, String path) {
        if (method.equals("GET") && path.equals("/")) return true;
        return false;
    }
}
