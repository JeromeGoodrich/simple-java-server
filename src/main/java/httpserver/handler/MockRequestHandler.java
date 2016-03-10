package httpserver.handler;

import httpserver.request.Request;
import httpserver.response.MockResponse;
import httpserver.response.Response;

public class MockRequestHandler implements RequestHandler {

    public Response handle(Request request) {
        return new MockResponse(request.getRawRequest());
    }
}
