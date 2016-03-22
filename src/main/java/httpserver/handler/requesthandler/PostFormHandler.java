package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.File;

public class PostFormHandler implements Handler {

    public Response handle(Request request) {
        String val1 = request.getBodyVal("firstname");
        String val2 = request.getBodyVal("lastname");
        String htmlContent = "<!DOCTYPE html>\n<html>\n<header>\n</header>\n<body>\n"+ val1 + " " + val2 + "\n</body>\n</html>";
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(htmlContent.getBytes()).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        if (method.equals("POST") && path.equals("form")) return true;
        return false;
    }
}
