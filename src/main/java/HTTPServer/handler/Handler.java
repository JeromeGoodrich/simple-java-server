package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

public interface Handler {
    Response handle(Request request);
}
