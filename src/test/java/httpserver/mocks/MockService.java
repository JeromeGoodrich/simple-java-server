package httpserver.mocks;

import httpserver.server.ClientSocketInterface;
import httpserver.server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MockService extends Service implements Runnable {

    public  boolean running;

    public void run() {
        running = true;
    }
}
