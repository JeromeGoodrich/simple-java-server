package httpserver.server;

import java.io.IOException;

public class MockServerListener implements ServerListener {

    private int callCounter = 0;

    public ClientSocketInterface accept() throws IOException {
        return new MockClientSocket();
    }

    public Boolean isOpen() {
        if (callCounter <= 3) {
            callCounter ++;
            return true;
        } else {
            return false;
        }
    }
}