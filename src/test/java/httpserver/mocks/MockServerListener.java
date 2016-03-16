package httpserver.mocks;

import httpserver.server.ClientSocketInterface;
import httpserver.server.ServerListener;

import java.io.IOException;

public class MockServerListener implements ServerListener {


    private ClientSocketInterface socket;
    private int callCounter = 0;

    public MockServerListener(ClientSocketInterface socket) {
        this.socket = socket;
    }

    public ClientSocketInterface accept() throws IOException {
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