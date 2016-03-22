package httpserver.handler.requesthandler;

import httpserver.request.Request;
import httpserver.response.Response;

public interface Handler {
    Response handle(Request request);
    boolean willHandle(String method, String path);

}
