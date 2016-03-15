package httpserver.mocks;

import httpserver.handler.requesthandler.RequestHandler;
import httpserver.request.Request;
import httpserver.response.Response;

public class MockRequestHandler implements RequestHandler {

    private int callsToHandle = 0;

    public int getCallsToHandle(){
        return callsToHandle;
    }

    public Response handle(Request request) {
        callsToHandle ++;
        return new MockResponse(request.getRawRequest());
    }
}
