package httpserver.handler.requesthandler;


import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;
import java.util.ArrayList;
import java.util.List;

public class MethodNotAllowedHandler implements Handler {

    private final List<String> allowedMethods = new ArrayList<String>() {
        {
            add("GET");
            add("POST");
            add("PUT");
            add("OPTIONS");
            add("HEAD");
            add("PATCH");
            add("DELETE");
            add("TRACE");
            add("CONNECT");
        }
    };


    public Response handle(Request request) {
        ResponseBuilder builder = new ResponseBuilder(405);
        return builder.version(request.getVersion()).reasonPhrase().body("Method Not Allowed".getBytes()).build();
    }

    public boolean willHandle(String method, String path) {
        return true;
    }
}
