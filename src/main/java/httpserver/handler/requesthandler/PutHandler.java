package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

public class PutHandler implements Handler {

    public Response handle(Request request) {
        return new ResponseBuilder(200).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        return (method.equals("PUT"));
    }
}
