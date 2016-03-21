package httpserver.server;

import java.io.IOException;

public interface ServerListener {
    ClientConnection accept() throws IOException;
    boolean isOpen();
}
