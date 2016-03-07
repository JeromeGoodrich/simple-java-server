package httpserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ClientServerIO {
    InputStream inFromClient() throws IOException;
    OutputStream outToClient() throws IOException;
    void close() throws IOException;
}
