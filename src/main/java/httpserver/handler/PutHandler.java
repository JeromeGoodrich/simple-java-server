package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

public class PutHandler implements Handler {

    public Response handle(Request request) {
        return new Response.ResponseBuilder(200).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        return (method.equals("PUT"));
    }
}
