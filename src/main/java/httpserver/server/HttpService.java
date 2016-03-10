package httpserver.server;

import httpserver.handler.HttpRequestHandler;
import httpserver.handler.RequestHandler;
import httpserver.handler.ResponseHandler;
import httpserver.request.HTTPRequest;
import httpserver.request.Parser;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.*;

public class HttpService extends Service implements Runnable {

    private Parser parser;
    private RequestHandler requestHandler;
    private ResponseHandler responseHandler;
    private ClientSocketInterface socket;
    private Request request;

    public HttpService(RequestHandler requestHandler, Parser parser, ResponseHandler responseHandler) {
        this.parser = parser;
        this.requestHandler = requestHandler;
        this.responseHandler = responseHandler;
    }

    public void setSocket(ClientSocketInterface socket) {
        this.socket = socket;
    }

    public void run() {
        try {

            request = parser.parse(socket.getInputStream());
            Response response = requestHandler.handle(request);
            InputStream in = responseHandler.handle(response, socket);
            responseHandler.sendToClient(in, socket.getOutputStream(), new byte[1024]);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
