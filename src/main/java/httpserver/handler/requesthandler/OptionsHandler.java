package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;

public class OptionsHandler implements Handler {

    public Response handle(Request request) {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        builder.addHeader("Allow","GET,HEAD,POST,OPTIONS,PUT");
        return builder.reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        return (method.equals("OPTIONS"));
    }
}
