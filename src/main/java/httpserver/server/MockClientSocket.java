package httpserver.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockClientSocket implements ClientSocketInterface {

    public InputStream getInputStream() {
        byte[] data = "data".getBytes();
        return new ByteArrayInputStream(data);
    }

    public OutputStream getOutputStream(){
        return null;
    }

    public void close() {

    }
}
