package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

public class NotFoundHandler implements Handler {

    public Response handle(Request request) {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(404);
        return builder.version(request.getVersion()).body("Sorry, we couldn't find what you were looking for".getBytes()).build();
    }

    public boolean willHandle(String method, String path) {
        return true;
    }
}
