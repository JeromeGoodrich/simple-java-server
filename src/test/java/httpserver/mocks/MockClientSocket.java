package httpserver.mocks;

import httpserver.server.ClientConnection;

import java.io.*;

public class MockClientSocket implements ClientConnection {

    private InputStream inputStream;
    private boolean closed;

    public MockClientSocket(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream(){
        return new ByteArrayOutputStream();
    }

    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
