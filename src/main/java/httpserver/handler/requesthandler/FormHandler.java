package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;

public class FormHandler implements Handler {

    public Response handle(Request request) {
        String HTMLBoilerPlate = "<!DOCTYPE html>\n<html>\n<header>\n</header>\n<body>\n";
        String openFormTag = "<form method=\"post\">";
        String firstField = "First Name:\n<input type=\"text\" name=\"firstname\">\n";
        String secondField = "Last Name:\n<input type=\"text\" name=\"lastname\">\n";
        String submit = "<input type=\"submit\" value=\"Submit\">\n</form>\n</body>\n</html>";
        String htmlContent = HTMLBoilerPlate + openFormTag + firstField + secondField + submit;
        byte[] data = htmlContent.getBytes();
        ResponseBuilder builder = new ResponseBuilder(200);
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        if (method.equals("GET") && path.equals("form")) return true;
        return false;
    }
}
