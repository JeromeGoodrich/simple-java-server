package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

import java.util.List;

public class Router implements Handler {

    private final List<Handler> handlers;

    public Router(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public Response handle(Request request) {
        Response response = null;
        for (Handler handler : handlers) {
            if (handler.willHandle(request.getMethod(), request.getPath())) {
                response = handler.handle(request);
                break;
            }
        }
        return response;
    }

    public boolean willHandle(String method, String path) {
        return false;
    }
}
