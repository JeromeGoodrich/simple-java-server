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

public class WebService implements Runnable {

    private Socket clientSocket;
    private Parser parser;
    private Handler handler;
    private Request request;

    public WebService(Socket clientSocket, Handler handler, Parser parser) {
        this.clientSocket = clientSocket;
        this.parser = parser;
        this.handler = handler;

    }

    public void run() {
        try {
            DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
            InputStream inputFromClient = clientSocket.getInputStream();

            request = parser.parse(inputFromClient);
            Response response = handler.handle(request);
            ResponseFormatter formatter = new ResponseFormatter();
            byte[] formattedResponse = formatter.format(response);

            outToClient.write(formattedResponse);
            outToClient.flush();

            clientSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
