package httpserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientSocketInterface {

    public InputStream getInputStream();
    public OutputStream getOutputStream();
    public void close();
}
