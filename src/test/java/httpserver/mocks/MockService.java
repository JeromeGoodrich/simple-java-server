package httpserver.mocks;

import httpserver.server.ClientSocketInterface;
import httpserver.server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MockService implements Runnable {

    public boolean running;
    private ClientSocketInterface socket;

    public void setSocket(ClientSocketInterface socket) {
        this.socket = socket;
    }

    public ClientSocketInterface getSocket() {
        return socket;
    }


    public void run() {
        running = true;
    }


}
