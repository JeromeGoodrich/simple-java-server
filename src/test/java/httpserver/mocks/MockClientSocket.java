package httpserver.mocks;

import httpserver.server.ClientSocketInterface;

import java.io.*;

public class MockClientSocket implements ClientSocketInterface {


    public InputStream getInputStream() {
        return null;
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
