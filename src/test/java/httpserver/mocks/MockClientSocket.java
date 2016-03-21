package httpserver.mocks;

import httpserver.server.ClientConnection;

import java.io.*;

public class MockClientSocket implements ClientConnection {

    private InputStream inputStream;

    public MockClientSocket(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream(){
        return null;
    }

    public void close() {

    }

    public boolean isClosed() {
        return false;
    }
}
