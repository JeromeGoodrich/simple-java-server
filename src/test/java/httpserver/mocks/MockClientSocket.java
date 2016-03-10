package httpserver.mocks;

import httpserver.server.ClientSocketInterface;

import java.io.*;

public class MockClientSocket implements ClientSocketInterface {

    private String input;
    public OutputStream output;

    public MockClientSocket(String input) {
        this.input = input;
    }

    public InputStream getInputStream() {
        byte[] data = input.getBytes();
        return new ByteArrayInputStream(data);
    }

    public OutputStream getOutputStream(){
        this.output = new ByteArrayOutputStream();
        return output;
    }

    public void close() {

    }
}
