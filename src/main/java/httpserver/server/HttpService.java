package httpserver.server;

import httpserver.handler.requesthandler.RequestHandler;
import httpserver.handler.responsehandler.ResponseHandler;
import httpserver.parser.Parser;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.*;

public class HttpService implements Runnable {

    private final Parser parser;
    private final RequestHandler requestHandler;
    private final ResponseHandler responseHandler;
    private final ClientSocketInterface socket;
    private Request request; //doesn't need to be property

    public HttpService(RequestHandler requestHandler, Parser parser, ResponseHandler responseHandler, ClientSocketInterface socket) {
        this.parser = parser;
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
        this.socket = socket;
    }

    public ClientSocketInterface getSocket() {
        return socket;
    }

    public void run() {
        try {

            request = parser.parse(socket.getInputStream());
            Response response = requestHandler.handle(request);
            InputStream in = responseHandler.handle(response);//move logic from send to handle
            responseHandler.sendToClient(in, socket.getOutputStream(), new byte[1024]);
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//intorduce finally block to ensure socket gets closed
}
