package httpserver.server;

import httpserver.RequestLogger;
import httpserver.handler.Handler;
import httpserver.parser.Parser;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.*;
import java.util.logging.Level;

public class HttpService implements Runnable {

    private final Parser parser;
    private final Handler handler;
    private final ClientConnection socket;

    public HttpService(Handler handler, Parser parser, ClientConnection socket) {
        this.parser = parser;
        this.handler = handler;
        this.socket = socket;
    }

    public void run() {
        try {

            Request request = parser.parse(socket.getInputStream());
            Response response = handler.handle(request);
            response.sendToClient(socket.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
