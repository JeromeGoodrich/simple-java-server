package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;

import java.util.ArrayList;
import java.util.List;

public class Router implements Handler {

    private final String rootDir;
    private final List<Handler> handlers;

    public Router(final String rootDir) {
        this.rootDir = rootDir;
        this.handlers = new ArrayList<Handler>() {
            {
                add(new DirHandler(rootDir));
                add(new FileHandler(rootDir));
                add(new FormHandler());
                add(new PostFormHandler());
                add(new ParamsHandler());
                add(new PatchHandler(rootDir));
                add(new NotFoundHandler(rootDir));
                add(new MethodNotAllowedHandler());
            }
        };
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
