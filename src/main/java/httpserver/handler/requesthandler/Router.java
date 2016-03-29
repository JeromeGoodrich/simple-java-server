package httpserver.handler.requesthandler;

import httpserver.RequestLogger;
import httpserver.request.Request;
import httpserver.response.Response;

import java.util.ArrayList;
import java.util.List;

public class Router implements Handler {

    private final List<Handler> handlers;

    public Router(final String rootDir) {
        this.handlers = new ArrayList<Handler>() {
            {
                add(new BasicAuthHandler());
                add(new DirHandler(rootDir));
                add(new PatchHandler(rootDir));
                add(new FileHandler(rootDir));
                add(new OldFormHandler());
                add(new FormDataHandler());
                add(new PutHandler());
                add(new OptionsHandler());
                add(new RedirectHandler());
                add(new ParamsHandler());
                add(new NotFoundHandler());
            }
        };
    }

    public Response handle(Request request) {
        Response response = null;
        RequestLogger logger = new RequestLogger();
        logger.log(request);
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
