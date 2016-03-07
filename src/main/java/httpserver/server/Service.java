package httpserver.server;

import httpserver.handler.Handler;
import httpserver.handler.RequestHandler;
import httpserver.request.HTTPRequest;
import httpserver.request.Parser;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.views.ResponseFormatter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Service implements Runnable {

    private Parser parser;
    private Handler handler;
    private ClientServerIO io;
    private Request request;

    public Service(ClientServerIO io, Handler handler, Parser parser) {
        this.io = io;
        this.parser = parser;
        this.handler = handler;

    }

    public void run() {
        try {

            request = parser.parse(io.inFromClient());
            Response response = handler.handle(request);
            ResponseFormatter formatter = new ResponseFormatter();
            byte[] formattedResponse = formatter.format(response);

            io.outToClient().write(formattedResponse);
            io.outToClient().flush();

            io.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
