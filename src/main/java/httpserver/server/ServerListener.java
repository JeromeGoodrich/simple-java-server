package httpserver.server;

import java.io.IOException;

public interface ServerListener {
    ClientSocketInterface accept() throws IOException;
    boolean isOpen();
}
