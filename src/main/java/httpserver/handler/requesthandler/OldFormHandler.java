package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;

public class OldFormHandler implements Handler {


    public Response handle(Request request) {
        if (request.getMethod().equals("GET")) {
            return handleGet(request);
        } else {
            return handlePost(request);
        }
    }

    private Response handleGet(Request request) {
        String HTMLBoilerPlate = "<!DOCTYPE html>\n<html>\n<header>\n</header>\n<body>\n";
        String openFormTag = "<form method=\"post\">";
        String firstField = "First Name:\n<input type=\"text\" name=\"firstname\">\n";
        String secondField = "Last Name:\n<input type=\"text\" name=\"lastname\">\n";
        String submit = "<input type=\"submit\" value=\"Submit\">\n</form>\n</body>\n</html>";
        String htmlContent = HTMLBoilerPlate + openFormTag + firstField + secondField + submit;
        byte[] data = htmlContent.getBytes();
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        return builder.body(data).reasonPhrase().version(request.getVersion()).build();
    }

    private Response handlePost(Request request) {
        String body = request.getBody();
        String[] splitBody = body.split("&");
        String values = "";
        for (String params: splitBody) {
            String[] keyValue = params.split("=");
            values += keyValue[1] + " ";
        }
        String htmlContent = "<!DOCTYPE html>\n<html>\n<header>\n</header>\n<body>\n"+ values + "\n</body>\n</html>";
        Response.ResponseBuilder builder = new Response.ResponseBuilder(200);
        return builder.body(htmlContent.getBytes()).reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        return path.equals("oldform");
    }

}
