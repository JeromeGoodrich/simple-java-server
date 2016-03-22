package httpserver.server;

import httpserver.handler.requesthandler.Handler;
import httpserver.handler.responsehandler.ResponseHandler;
import httpserver.parser.Parser;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.*;

public class HttpService implements Runnable {

    private final Parser parser;
    private final Handler requestHandler;
    private final ResponseHandler responseHandler;
    private final ClientConnection socket;

    public HttpService(Handler requestHandler, Parser parser, ResponseHandler responseHandler, ClientConnection socket) {
        this.parser = parser;
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
        this.socket = socket;
    }

    public ClientConnection getSocket() {
        return socket;
    }

    public void run() {
        try {

            Request request = parser.parse(socket.getInputStream());
            Response response = requestHandler.handle(request);
            InputStream in = responseHandler.handle(response);//move logic from send to handle
            responseHandler.sendToClient(in, socket.getOutputStream(), new byte[1024]);

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
