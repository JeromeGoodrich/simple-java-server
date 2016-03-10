package httpserver.mocks;

import httpserver.server.ClientSocketInterface;
import httpserver.server.ServerListener;

import java.io.IOException;

public class MockServerListener implements ServerListener {

    private int callCounter = 0;

    public ClientSocketInterface accept() throws IOException {
        return new MockClientSocket("data");
    }

    public Boolean isOpen() {
        if (callCounter <= 1) {
            callCounter ++;
            return true;
        } else {
            return false;
        }
    }
}
