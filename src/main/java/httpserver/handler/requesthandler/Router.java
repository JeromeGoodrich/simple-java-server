package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.response.ResponseBuilder;
import org.json.simple.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Router implements Handler {

    private final List<Handler> handlers = new ArrayList<Handler>() {
        {
            add(new RootHandler());
            add(new DirHandler());
            add(new FileHandler());
            add(new FormHandler());
            add(new PostFormHandler());
            add(new NotFoundHandler());
        }
    };

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
