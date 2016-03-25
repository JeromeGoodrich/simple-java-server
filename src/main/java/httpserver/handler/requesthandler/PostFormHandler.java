package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

import java.io.File;

public class PostFormHandler implements Handler {

    public Response handle(Request request) {
        String body = request.getBody();
        String[] splitBody = body.split("&");
        String values = "";
        for (String params: splitBody) {
            String[] keyValue = params.split("=");
            values += keyValue[1] + " ";
        }
        String htmlContent = "<!DOCTYPE html>\n<html>\n<header>\n</header>\n<body>\n"+ values + "\n</body>\n</html>";
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(htmlContent.getBytes()).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        if (method.equals("POST") && path.equals("form")) return true;
        return false;
    }
}
