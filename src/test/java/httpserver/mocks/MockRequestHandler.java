package httpserver.mocks;

import httpserver.handler.requesthandler.RequestHandler;
import httpserver.request.Request;
import httpserver.response.Response;

public class MockRequestHandler implements RequestHandler {

    public Response handle(Request request) {
        return new MockResponse(request.getRawRequest());
    }
}
