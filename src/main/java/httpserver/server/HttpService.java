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
            //log requestLine and headers
            RequestLogger.logger.log(Level.INFO, request.getLogInfo());
            Response response = handler.handle(request);
            response.sendToClient(socket.getOutputStream());

        } catch (Exception e) {
            RequestLogger.logger.log(Level.INFO, "The file can't be foun", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                RequestLogger.logger.log(Level.INFO, "The file can't be foun", e);
            }
        }
    }
}
