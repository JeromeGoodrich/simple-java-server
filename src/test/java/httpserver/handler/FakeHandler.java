package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

public class FakeHandler implements Handler {

    private String fakePath;
    private int statusCode;

    public FakeHandler(String path, int statusCode) {
        this.fakePath = path;
        this.statusCode = statusCode;
    }

    @Override
    public Response handle(Request request) {
        return new Response.ResponseBuilder(statusCode).build();
    }

    @Override
    public boolean willHandle(String method, String path) {
        return (path.equals(fakePath));
    }
}
