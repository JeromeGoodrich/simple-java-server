package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

public class RedirectHandler implements Handler {

    public Response handle(Request request) {
        Response.ResponseBuilder builder = new Response.ResponseBuilder(302);
        builder.addHeader("Location","http://localhost:5000/");
        return builder.reasonPhrase().version(request.getVersion()).build();
    }

    public boolean willHandle(String method, String path) {
        return (path.equals("redirect"));
    }
}
