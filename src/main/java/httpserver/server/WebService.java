package httpserver.server;

import httpserver.handler.RequestHandler;
import httpserver.request.Request;
import httpserver.response.Response;
import httpserver.views.ResponseFormatter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class WebService implements Runnable {

    private Socket clientSocket;

    public WebService(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
            InputStreamReader inputReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader inFromClient = new BufferedReader(inputReader);

            String rawRequest = inFromClient.readLine();
            Request request = new Request();
            request.parse(rawRequest);
            RequestHandler handler = new RequestHandler();
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
