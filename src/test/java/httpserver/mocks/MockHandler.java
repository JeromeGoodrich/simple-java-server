package httpserver.mocks;

import httpserver.handler.requesthandler.Handler;
import httpserver.request.Request;
import httpserver.response.Response;

public class MockHandler implements Handler {

    private int callsToHandle = 0;

    public int getCallsToHandle(){
        return callsToHandle;
    }

    public Response handle(Request request) {
        callsToHandle ++;
        return new MockResponse(request.getRawRequest());
    }

    public boolean willHandle(String method, String path) {
        return false;
    }
}
