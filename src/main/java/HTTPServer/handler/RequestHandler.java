package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.Response;

public interface RequestHandler {

    Response handle(Request request);
}
