package httpserver.mocks;

import httpserver.server.ClientConnection;
import httpserver.server.ServerListener;

import java.io.IOException;

public class MockServerListener implements ServerListener {


    private ClientConnection socket;
    private int callCounter = 0;

    public MockServerListener(ClientConnection socket) {
        this.socket = socket;
    }

    public ClientConnection accept() throws IOException {
        return socket;
    }

    public boolean isOpen() {
        if (callCounter == 0) {
            callCounter ++;
            return true;
        } else {
            return false;
        }
    }
}