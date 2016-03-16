package httpserver.mocks;

import httpserver.handler.responsehandler.ResponseHandler;
import httpserver.response.Response;
import httpserver.server.ClientSocketInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockResponseHandler implements ResponseHandler {

    public String outputString;
    private int callsToHandle;

    public int getCallsToHandle() {
        return callsToHandle;
    }

    public InputStream handle(Response response) {
        callsToHandle ++;
        InputStream in = response.getRawRequest();
        return in;

    }

    public void sendToClient(InputStream in, OutputStream out, byte[] buf) throws IOException {
    }
}

