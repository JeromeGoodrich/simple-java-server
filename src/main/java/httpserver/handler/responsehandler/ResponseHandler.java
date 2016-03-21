package httpserver.handler.responsehandler;

import httpserver.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ResponseHandler {
    public InputStream handle(Response response) throws IOException;

    void sendToClient(InputStream in, OutputStream outputStream, byte[] bytes) throws IOException;
}
