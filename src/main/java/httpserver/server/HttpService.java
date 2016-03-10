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

public class HttpService extends Service implements Runnable {

    private Parser parser;
    private Handler handler;
    private ClientSocketInterface socket;
    private Request request;

    public HttpService(Handler handler, Parser parser) {
        this.parser = parser;
        this.handler = handler;
    }

    public void setSocket(ClientSocketInterface socket) {
        this.socket = socket;
    }

    public void run() {
        try {

            request = parser.parse(socket.getInputStream());
            Response response = handler.handle(request);
            ResponseFormatter formatter = new ResponseFormatter();
            byte[] formattedResponse = formatter.format(response);

            socket.getOutputStream().write(formattedResponse);
            socket.getOutputStream().flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
