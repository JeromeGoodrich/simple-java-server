package httpserver.mocks;

import httpserver.server.ClientConnection;

public class MockService implements Runnable {

    public boolean running;
    private ClientConnection socket;

    public void setSocket(ClientConnection socket) {
        this.socket = socket;
    }

    public ClientConnection getSocket() {
        return socket;
    }


    public void run() {
        running = true;
    }


}
